package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.*;
import com.arnzen.home_api_backend.model.base.*;
import com.arnzen.home_api_backend.model.info.*;
import com.arnzen.home_api_backend.model.reducedData.GetDeviceResponse;
import com.arnzen.home_api_backend.model.reducedData.GetHomeResponse;
import com.arnzen.home_api_backend.model.reducedData.GetLocationResponse;
import com.arnzen.home_api_backend.model.reducedData.GetTemperatureResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public ResponseEntity<HomeScreenInfoResponseEntity> getHomeScreenInfo(int userId) {
        // Determine if the specified user exists.
        Optional<UserEntity> user = userDao.findById(userId);

        if (user.isPresent()) {

            // Get the registered homes for the specified user.
            List<HomeEntity> homes = homeDao.findAllByUserEntityId(userId);
            List<GetHomeResponse> formattedHomes = new ArrayList<>();

            int totalLocations = 0;
            int totalDevices = 0;

            for (HomeEntity home : homes) {
                totalLocations += homeDao.countTotalLocations(home.getId());
                totalDevices += homeDao.countTotalDevices(home.getId());
                formattedHomes.add(new GetHomeResponse(home.getId(), userId, home.getHomeName()));
            };

            return new ResponseEntity<>(new HomeScreenInfoResponseEntity(formattedHomes,
                    totalLocations, totalDevices), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ViewHomeResponseEntity> getViewHomeInfo(int homeId) {
        // Determine if the specified home exists.
        Optional<HomeEntity> home = homeDao.findById(homeId);

        if (home.isPresent()) {

            int totalDevices = homeDao.countTotalDevices(homeId);

            // Generate the home response object.
            return new ResponseEntity<>(new ViewHomeResponseEntity(homeId, home.get().getHomeName(),
                    formatLocations(homeId), totalDevices), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ViewLocationResponseEntity> getViewLocationInfo(int locationId) {

        // Determine if the specified location exists.
        Optional<LocationEntity> location = locationDao.findById(locationId);

        if (location.isPresent()) {

            return new ResponseEntity<>(new ViewLocationResponseEntity(locationId,
                    location.get().getHomeEntity().getId(), location.get().getLocationName(),
                    formatDevices(locationId)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ViewDeviceResponseEntity> getTemperaturesByDeviceCurrentDay(int deviceId) {

        // Verify the specified device exists.
        Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);

        if (deviceEntity.isPresent()) {

            ViewDeviceResponseEntity viewDeviceResponse = new ViewDeviceResponseEntity();

            String deviceName = deviceEntity.get().getDeviceName();
            int locationId = deviceEntity.get().getLocationEntity().getId();

            // Get the most recent temperature for the specified device.
            TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceId(deviceId);

            // Get average hourly temperatures (for the past 24 hours) for the specified device.
            List<TemperatureHourlyAverage> averageHourlyTemperaturesCurrentDay =
                    temperatureDao.findAverageTemperaturePastTwentyFourHours(deviceId, LocalDateTime.now().minusHours(23));

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

    public List<GetLocationResponse> formatLocations(int homeId) {
        List<GetLocationResponse> formattedLocations = new ArrayList<>();

        List<LocationEntity> locations = locationDao.findAllByHomeEntityId(homeId);

        locations.forEach(location -> {
            GetLocationResponse locationResponse = new GetLocationResponse();
            locationResponse.setLocationId(location.getId());
            locationResponse.setHomeId(location.getHomeEntity().getId());
            locationResponse.setLocationName(location.getLocationName());
            locationResponse.setDevices(formatDevices(location.getId()));
            formattedLocations.add(locationResponse);
        });

        return formattedLocations;
    }

    public List<GetDeviceResponse> formatDevices(int locationId) {

        // Get all devices registered for the specified location.
        List<DeviceEntity> devices = deviceDao.findAllByLocationEntityId(locationId);

        List<GetDeviceResponse> formattedDevices = new ArrayList<>();
        devices.forEach(device -> {
            TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceId(device.getId());
            GetTemperatureResponse temperatureResponse = null;

            if (mostRecentTemperature != null) {
                temperatureResponse = new GetTemperatureResponse(mostRecentTemperature.getId(),
                        mostRecentTemperature.getTemperature(), mostRecentTemperature.getDateRecorded());
            }

            formattedDevices.add(new GetDeviceResponse(device.getId(),
                    device.getLocationEntity().getId(), device.getDeviceName(), temperatureResponse));
        });

        return formattedDevices;
    }
}
