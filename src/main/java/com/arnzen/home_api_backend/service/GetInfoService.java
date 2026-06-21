package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.*;
import com.arnzen.home_api_backend.model.base.*;
import com.arnzen.home_api_backend.model.entityPath.EntityPathItem;
import com.arnzen.home_api_backend.model.entityPath.EntityType;
import com.arnzen.home_api_backend.model.info.*;
import com.arnzen.home_api_backend.model.reducedData.*;
import com.arnzen.home_api_backend.model.temperature.TemperatureThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

            for (HomeEntity home : homes) {
                int totalLocations = homeDao.countTotalLocations(home.getId());
                int totalDevices = homeDao.countTotalDevices(home.getId());
                formattedHomes.add(new GetHomeResponse(home.getId(), userId, home.getHomeName(), totalLocations, totalDevices));
            };

            List<EntityPathItem> entityPath = List.of(new EntityPathItem(user.get().getId(), EntityType.USER));

            return new ResponseEntity<>(new HomeScreenInfoResponseEntity(user.get().getId(), user.get().isEmailConfirmed(), formattedHomes, entityPath), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ViewHomeResponseEntity> getViewHomeInfo(int homeId) {
        // Determine if the specified home exists.
        Optional<HomeEntity> home = homeDao.findById(homeId);

        if (home.isPresent()) {

            EntityPathItem userPath = new EntityPathItem(home.get().getUserEntity().getId(), EntityType.USER);
            EntityPathItem homePath = new EntityPathItem(homeId, EntityType.HOME);

            // Generate the home response object.
            return new ResponseEntity<>(new ViewHomeResponseEntity(homeId, home.get().getHomeName(),
                    formatLocations(homeId), List.of(userPath, homePath)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ViewLocationResponseEntity> getViewLocationInfo(int locationId) {

        // Determine if the specified location exists.
        Optional<LocationEntity> location = locationDao.findById(locationId);

        if (location.isPresent()) {

            TemperatureThreshold threshold =
                    new TemperatureThreshold();

            if (location.get().getTemperatureThresholdEntity() != null) {
                threshold.setId(location.get().getTemperatureThresholdEntity().getId());
                threshold.setMinimumTemperature(location.get().getTemperatureThresholdEntity().getMinimumTemperature());
                threshold.setMaximumTemperature(location.get().getTemperatureThresholdEntity().getMaximumTemperature());
                threshold.setLocationId(location.get().getTemperatureThresholdEntity().getLocationEntity().getId());
            } else {
                threshold = null;
            }

            EntityPathItem userPath = new EntityPathItem(location.get().getHomeEntity().getUserEntity().getId(), EntityType.USER);
            EntityPathItem homePath = new EntityPathItem(location.get().getHomeEntity().getId(), EntityType.HOME);
            EntityPathItem locationPath = new EntityPathItem(location.get().getId(), EntityType.LOCATION);

            return new ResponseEntity<>(new ViewLocationResponseEntity(locationId,
                    location.get().getHomeEntity().getId(), location.get().getLocationName(),
                    formatDevices(locationId), threshold, List.of(userPath, homePath, locationPath)), HttpStatus.OK);
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
            int homeId = deviceEntity.get().getLocationEntity().getHomeEntity().getId();

            // Get the most recent temperature for the specified device.
            TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceId(deviceId, LocalDateTime.now().minusMinutes(10));

            // Get average hourly temperatures (for the past 24 hours) for the specified device.
            List<TemperatureHourlyAverage> averageHourlyTemperaturesCurrentDay =
                    temperatureDao.findAverageTemperaturePastTwentyFourHours(deviceId, LocalDateTime.now().minusHours(23));

            viewDeviceResponse.setDeviceId(deviceId);
            viewDeviceResponse.setDeviceName(deviceName);
            viewDeviceResponse.setLocationId(locationId);
            viewDeviceResponse.setHomeId(homeId);
            viewDeviceResponse.setAverageTemperaturesByHourCurrentDay(averageHourlyTemperaturesCurrentDay);

            if (mostRecentTemperature != null) {
                GetTemperatureResponse tempResponse = new GetTemperatureResponse(mostRecentTemperature.getId(),
                        mostRecentTemperature.getTemperature(),
                        mostRecentTemperature.getDateRecorded());
                viewDeviceResponse.setTemperature(tempResponse);
            }

            EntityPathItem userPath = new EntityPathItem(deviceEntity.get().getLocationEntity().getHomeEntity().getUserEntity().getId(), EntityType.USER);
            EntityPathItem homePath = new EntityPathItem(deviceEntity.get().getLocationEntity().getHomeEntity().getId(), EntityType.HOME);
            EntityPathItem locationPath = new EntityPathItem(deviceEntity.get().getLocationEntity().getId(), EntityType.LOCATION);
            EntityPathItem devicePath = new EntityPathItem(deviceEntity.get().getId(), EntityType.DEVICE);

            viewDeviceResponse.setEntityPath(List.of(userPath, homePath, locationPath, devicePath));

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
            locationResponse.setNumDevices(location.getDevices().size());
            locationResponse.setAverageTemperature(getAverageLocationTemperature(location.getDevices()));


            if (location.getTemperatureThresholdEntity() != null) {
                TemperatureThreshold threshold =
                        new TemperatureThreshold(
                                location.getTemperatureThresholdEntity().getId(),
                                location.getTemperatureThresholdEntity().getMinimumTemperature(),
                                location.getTemperatureThresholdEntity().getMaximumTemperature(),
                                location.getTemperatureThresholdEntity().getLocationEntity().getId());
                locationResponse.setThreshold(threshold);
            } else {
                locationResponse.setThreshold(null);
            }
            formattedLocations.add(locationResponse);
        });

        return formattedLocations;
    }

    public List<GetDeviceResponse> formatDevices(int locationId) {

        // Get all devices registered for the specified location.
        List<DeviceEntity> devices = deviceDao.findAllByLocationEntityId(locationId);

        List<GetDeviceResponse> formattedDevices = new ArrayList<>();
        devices.forEach(device -> {
            TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceId(device.getId(), LocalDateTime.now().minusMinutes(10));
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

    public Double getAverageLocationTemperature(List<DeviceEntity> devices) {
        Double averageTemperature = null;
        int totalTemperatures = 0;

        for (DeviceEntity device : devices) {
            TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceId(device.getId(), LocalDateTime.now().minusMinutes(10));

            if (mostRecentTemperature != null) {
                if (averageTemperature != null) {
                    averageTemperature += mostRecentTemperature.getTemperature();   // Average temperature is set. Add to the set average temperature.
                } else {
                    averageTemperature = mostRecentTemperature.getTemperature();    // Average temperature is not set. Set the average temperature.
                }
                totalTemperatures++;
            }
        }

        if (averageTemperature != null) {
            return averageTemperature / totalTemperatures;
        } else {
            return averageTemperature;
        }
    }
}
