package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.*;
import com.arnzen.home_api_backend.model.*;
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
            return null;
        } else {
            List<GetLocationResponse> locationsResponse = new ArrayList<>();

            locations.forEach(location -> {
                GetLocationResponse getLocationResponse = new GetLocationResponse(location.getId(), location.getHomeEntity().getId(), location.getLocationName());
                locationsResponse.add(getLocationResponse);
            });

            return locationsResponse;
        }
    }

    public ResponseEntity<List<GetDeviceResponse>> getDevicesByLocation(int locationId) {

        List<DeviceEntity> devices = deviceDao.findByLocationEntityId(locationId);

        if (devices.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {

            List<GetDeviceResponse> devicesResponse = new ArrayList<>();

            devices.forEach(device -> {
                GetDeviceResponse getDeviceResponse = new GetDeviceResponse(device.getId(), device.getLocationEntity().getId(), device.getDeviceName());
                devicesResponse.add(getDeviceResponse);
            });


            return new ResponseEntity<>(devicesResponse, HttpStatus.OK);
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



        TemperatureEntity mostRecentTemperature = temperatureDao.getMostRecentTemperatureByDeviceIdAndDate(LocalDateTime.now(), deviceId);

        List<TemperatureHourlyAverage> averageHourlyTemperaturesCurrentDay = temperatureDao.findAverageTemperatureByDateAndHour(deviceId, LocalDateTime.now());

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
}
