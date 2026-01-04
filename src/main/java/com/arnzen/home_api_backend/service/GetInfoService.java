package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.*;
import com.arnzen.home_api_backend.model.*;
import com.arnzen.home_api_backend.model.base.DeviceEntity;
import com.arnzen.home_api_backend.model.base.HomeEntity;
import com.arnzen.home_api_backend.model.base.LocationEntity;
import com.arnzen.home_api_backend.model.base.UserEntity;
import com.arnzen.home_api_backend.model.info.HomeScreenInfoResponseEntity;
import com.arnzen.home_api_backend.model.info.ViewDeviceResponseEntity;
import com.arnzen.home_api_backend.model.info.ViewHomeResponseEntity;
import com.arnzen.home_api_backend.model.info.ViewLocationResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GetInfoService {

    @Autowired
    UserDao userDao;

    @Autowired
    HomeDao homeDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    TemperatureDao temperatureDao;

    public UserEntity getUserEntityByUsername(String username) {
        return userDao.findByUsernameIgnoreCase(username);
    }

    public List<HomeEntity> getHomesByUser(int userId) {
        return homeDao.findByUserEntityId(userId);
    }

    public HomeEntity getHomeById(int homeId) {
        Optional<HomeEntity> home = homeDao.findById(homeId);

        return home.orElse(null);
    }

    public List<LocationEntity> getLocationsByHome(int homeId) {
        return locationDao.findByHomeEntityId(homeId);
    }

    public List<GetDeviceResponse> getDevicesByLocation(int locationId) {

        List<DeviceEntity> devices = deviceDao.findByLocationEntityId(locationId);

        if (devices.isEmpty()) {
            return Collections.emptyList();
        } else {

            List<GetDeviceResponse> devicesResponse = new ArrayList<>();

            devices.forEach(device -> {

                // Get the most recent temperature value recorded for the device.
                TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceId(device.getId());

                GetTemperatureResponse temperatureResponse = new GetTemperatureResponse(mostRecentTemperature.getId(), mostRecentTemperature.getTemperature(), mostRecentTemperature.getDateRecorded());

                GetDeviceResponse getDeviceResponse = new GetDeviceResponse(device.getId(), device.getLocationEntity().getId(), device.getDeviceName(), temperatureResponse);
                devicesResponse.add(getDeviceResponse);
            });


            if (!devicesResponse.isEmpty()) {
                return devicesResponse;
            } else {
                return Collections.emptyList();
            }
        }
    }

    public ResponseEntity<ViewDeviceResponseEntity> getTemperaturesByDeviceCurrentDay(int deviceId) {

        ViewDeviceResponseEntity viewDeviceResponse = new ViewDeviceResponseEntity();

        Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);

        if (deviceEntity.isPresent()) {

            String deviceName = deviceEntity.get().getDeviceName();
            int locationId = deviceEntity.get().getLocationEntity().getId();

            TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceId(deviceId);

            List<TemperatureHourlyAverage> averageHourlyTemperaturesCurrentDay = temperatureDao.findAverageTemperaturePastTwentyFourHours(deviceId, LocalDateTime.now().minusHours(23));

            viewDeviceResponse.setDeviceId(deviceId);
            viewDeviceResponse.setDeviceName(deviceName);
            viewDeviceResponse.setLocationId(locationId);
            viewDeviceResponse.setAverageTemperaturesByHourCurrentDay(averageHourlyTemperaturesCurrentDay);

            if (mostRecentTemperature == null) {
                viewDeviceResponse.setMostRecentTemperature(0.0);
                viewDeviceResponse.setMostRecentTemperatureAvailable(false);
            } else {
                viewDeviceResponse.setMostRecentTemperature(mostRecentTemperature.getTemperature());
                viewDeviceResponse.setMostRecentTemperatureAvailableDateTime(mostRecentTemperature.getDateRecorded());
                viewDeviceResponse.setMostRecentTemperatureAvailable(true);
            }

            return new ResponseEntity<>(viewDeviceResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public GetLocationResponse getInformationByLocation(int locationId) {

        Optional<LocationEntity> locationEntity = locationDao.findById(locationId);

        if (locationEntity.isPresent()) {

            List<GetDeviceResponse> devicesByLocation = new ArrayList<>();

            // Get the devices for the specified location.
            List<DeviceEntity> devices = deviceDao.findByLocationEntityId(locationId);

            if (!devices.isEmpty()) {
                devices.forEach(device -> {

                    // Get the most recent temperature reading for the specified device.
                    TemperatureEntity temperature = temperatureDao.getMostRecentTemperatureByDeviceId(device.getId());

                    if (temperature != null) {
                        GetTemperatureResponse getTemperatureResponse = new GetTemperatureResponse(temperature.getId(), temperature.getTemperature(), temperature.getDateRecorded());
                        devicesByLocation.add(new GetDeviceResponse(device.getId(), locationId, device.getDeviceName(), getTemperatureResponse));
                    } else {
                        devicesByLocation.add(new GetDeviceResponse(device.getId(), locationId, device.getDeviceName(), null));
                    }
                });
            }


            GetLocationResponse locationResponse = new GetLocationResponse();
            locationResponse.setLocationId(locationId);
            locationResponse.setLocationName(locationEntity.get().getLocationName());
            locationResponse.setHomeId(locationEntity.get().getHomeEntity().getId());
            locationResponse.setDevices(devicesByLocation);

            return locationResponse;

        } else {
            return null;    // The specified location ID was not found in the database.
        }

    }

    public ResponseEntity<ViewHomeResponseEntity> getViewHomeInfo(int homeId) {

        ViewHomeResponseEntity responseEntity = new ViewHomeResponseEntity();

        Optional<HomeEntity> homeEntity = homeDao.findById(homeId);

        if (homeEntity.isPresent()) {

            int totalDevices = 0;

            responseEntity.setHomeId(homeId);
            responseEntity.setHomeName(homeEntity.get().getHomeName());
            List<LocationEntity> locations = locationDao.findAllByHomeEntityId(homeId);

            for (LocationEntity location : locations) {
                totalDevices += getTotalDevicesByLocation(location.getId());
            }

            List<GetLocationResponse> newLocations = convertLocationEntityToGetLocationResponse(locations);
            responseEntity.setLocations(newLocations);
            responseEntity.setNumDevices(totalDevices);

            return new ResponseEntity<>(responseEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseEntity, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HomeScreenInfoResponseEntity> getHomeScreenInfo(int userId) {

        HomeScreenInfoResponseEntity response = new HomeScreenInfoResponseEntity();

        int totalLocations = 0;
        int totalDevices = 0;

        // Get homes for the specified user.
        List<HomeEntity> homes = getAllHomesByUserId(userId);

        for (HomeEntity home : homes) {

            // Get locations for the specified home.
            List<LocationEntity> locations = getAllLocationsByHomeId(home.getId());
            totalLocations += locations.size();

            for (LocationEntity location : locations) {

                // Get devices for the specified location.
                List<DeviceEntity> devices = getAllDevicesByLocationId(location.getId());
                totalDevices += devices.size();
            }
        }

        response.setHomes(convertHomeEntityToGetHomeResponse(homes));
        response.setNumLocations(totalLocations);
        response.setNumDevices(totalDevices);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ViewHomeInfoResponseEntity getViewHomeByIdInfo(int homeId) {

        Optional<HomeEntity> home = homeDao.findById(homeId);

        ViewHomeInfoResponseEntity response = new ViewHomeInfoResponseEntity();

        if (home.isPresent()) {

            int totalDevices = 0;

            // Get locations for the specified home.
            List<LocationEntity> locations = getAllLocationsByHomeId(homeId);

            for (LocationEntity location : locations) {

                // Get devices for the specified location.
                List<DeviceEntity> devices = getAllDevicesByLocationId(location.getId());
                totalDevices += devices.size();
            }


            response.setLocations(locations);
            response.setHomeName(home.get().getHomeName());
            response.setNumDevices(totalDevices);
        }

        return response;
    }

    // Helper methods.
    // Return all homes for a specified user.
    public List<HomeEntity> getAllHomesByUserId(int userId) {
        return homeDao.findAllByUserEntityId(userId);
    }

    // Return all locations for a specified home.
    public List<LocationEntity> getAllLocationsByHomeId(int homeId) {
        return locationDao.findAllByHomeEntityId(homeId);
    }

    // Return all devices for a specified location.
    public List<DeviceEntity> getAllDevicesByLocationId(int locationId) {
        return deviceDao.findAllByLocationEntityId(locationId);
    }

    // Return all temperature for a specified device.
    public List<TemperatureEntity> getAllTemperaturesByDeviceId(int deviceId) {
        return temperatureDao.findAllByDeviceEntityId(deviceId);
    }

    // Get the total number of homes for the specified user.
    public int getTotalHomesByUser(int userId) {
        List<HomeEntity> homes = getAllHomesByUserId(userId);

        return homes.size();
    }

    // Get the total number of locations for the specified home.
    public int getTotalLocationsByHome(int homeId) {
        List<LocationEntity> locations = getAllLocationsByHomeId(homeId);

        return locations.size();
    }

    // Get the total number of devices for the specified location.
    public int getTotalDevicesByLocation(int locationId) {
        List<DeviceEntity> devices = getAllDevicesByLocationId(locationId);

        return devices.size();
    }

    // Get the total number of temperature readings for the specified device.
    public int getTotalTemperatureReadingsByDevice(int deviceId) {
        List<TemperatureEntity> temperatureReadings = getAllTemperaturesByDeviceId(deviceId);

        return temperatureReadings.size();
    }

    public ResponseEntity<ViewLocationResponseEntity> getViewLocationInfo(int locationId) {

        // Verify the specified location exists in the database.
        Optional<LocationEntity> locationEntity = locationDao.findById(locationId);

        if (locationEntity.isPresent()) {
            ViewLocationResponseEntity responseEntity = new ViewLocationResponseEntity();
            responseEntity.setLocationId(locationId);
            responseEntity.setLocationName(locationEntity.get().getLocationName());

            // Get a list of devices associated with the location.
            List<DeviceEntity> devices = deviceDao.findAllByLocationEntityId(locationId);

            responseEntity.setDevices(convertDeviceEntityToGetDeviceResponse(devices));

            return new ResponseEntity<>(responseEntity, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public List<GetHomeResponse> convertHomeEntityToGetHomeResponse(List<HomeEntity> homes) {
        List<GetHomeResponse> newHomes = new ArrayList<>();

        for (HomeEntity home : homes) {
            GetHomeResponse homeResponse = new GetHomeResponse(home.getId(), home.getUserEntity().getId(), home.getHomeName());
            newHomes.add(homeResponse);
        }

        return newHomes;
    }

    public List<GetLocationResponse> convertLocationEntityToGetLocationResponse(List<LocationEntity> locations) {
        List<GetLocationResponse> newLocations = new ArrayList<>();

        for (LocationEntity location : locations) {
            GetLocationResponse locationResponse = new GetLocationResponse();
            locationResponse.setLocationId(location.getId());
            locationResponse.setLocationName(location.getLocationName());
            locationResponse.setHomeId(location.getHomeEntity().getId());
            locationResponse.setDevices(getDevicesByLocation(location.getId()));
            newLocations.add(locationResponse);
        }

        return newLocations;
    }

    public List<GetDeviceResponse> convertDeviceEntityToGetDeviceResponse(List<DeviceEntity> devices) {
        List<GetDeviceResponse> newDevices = new ArrayList<>();

        for (DeviceEntity device : devices) {
            GetDeviceResponse deviceResponse = new GetDeviceResponse();
            deviceResponse.setDeviceId(device.getId());
            deviceResponse.setDeviceName(device.getDeviceName());
            deviceResponse.setLocationId(device.getLocationEntity().getId());

            newDevices.add(deviceResponse);
        }

        return newDevices;
    }
}
