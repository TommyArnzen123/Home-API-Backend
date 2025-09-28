package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.*;
import com.arnzen.home_api_backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public List<GetHomeResponse> getHomesByUser(int userId) {
        List<HomeEntity> homes = homeDao.findByUserEntityId(userId);

        if (homes.isEmpty()) {
            return null;
        } else {

            List<GetHomeResponse> homesResponse = new ArrayList<>();

            homes.forEach(home -> {
                GetHomeResponse getHomeResponse = new GetHomeResponse(home.getId(), home.getUserEntity().getId(), home.getHomeName());
                homesResponse.add(getHomeResponse);
            });

            return homesResponse;
        }
    }

    public HomeEntity getHomeById(int homeId) {
        Optional<HomeEntity> home = homeDao.findById(homeId);

        return home.orElse(null);
    }

    public List<GetLocationResponse> getLocationsByHome(int homeId) {
        List<LocationEntity> locations = locationDao.findByHomeEntityId(homeId);

        if (locations.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<GetLocationResponse> locationsResponse = new ArrayList<>();

            locations.forEach(location -> {
                GetLocationResponse getLocationResponse = new GetLocationResponse(location.getId(), location.getHomeEntity().getId(), location.getLocationName(), null);
                locationsResponse.add(getLocationResponse);
            });

            if (!locationsResponse.isEmpty()) {
                return locationsResponse;
            } else {
                return Collections.emptyList();
            }
        }
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

    public ViewDeviceResponse getTemperaturesByDeviceCurrentDay(int deviceId) {

        ViewDeviceResponse viewDeviceResponse = new ViewDeviceResponse();

        Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);

        if (deviceEntity.isEmpty()) {
            return null;
        }

        String deviceName = deviceEntity.get().getDeviceName();
        int locationId = deviceEntity.get().getLocationEntity().getId();



        TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceId(deviceId);

//        List<TemperatureHourlyAverage> averageHourlyTemperaturesCurrentDay = temperatureDao.findAverageTemperatureByDateAndHour(deviceId, LocalDateTime.now());

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

        return viewDeviceResponse;
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
}
