package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import com.arnzen.home_api_backend.dao.UserDao;
import com.arnzen.home_api_backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GetInfoService {

    @Autowired
    UserDao userDao;

    @Autowired
    HomeDao homeDao;

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    TemperatureDao temperatureDao;

    public UserEntity getUserEntityByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public ResponseEntity<List<GetHomeResponse>> getHomesByUser(int userId) {
        List<HomeEntity> homes = homeDao.findByUserEntityId(userId);

        if (homes.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {

            List<GetHomeResponse> homesResponse = new ArrayList<>();

            homes.forEach(home -> {
                GetHomeResponse getHomeResponse = new GetHomeResponse(home.getId(), home.getUserEntity().getId(), home.getHomeName());
                homesResponse.add(getHomeResponse);
            });

            return new ResponseEntity<>(homesResponse, HttpStatus.OK);
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

    public ResponseEntity<List<GetTemperatureResponse>> getTemperaturesByDevice(int deviceId) {

        System.out.println(new Date());
        List<TemperatureEntity> temperatures = temperatureDao.findByDeviceEntityIdAndDateRecorded(deviceId, LocalDate.now());

        if (temperatures.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {

            List<GetTemperatureResponse> temperaturesResponse = new ArrayList<>();

            temperatures.forEach(temperature -> {
                GetTemperatureResponse getTemperatureResponse = new GetTemperatureResponse(temperature.getId(), temperature.getDeviceEntity().getId(), temperature.getTemperature());
                temperaturesResponse.add(getTemperatureResponse);
            });


            return new ResponseEntity<>(temperaturesResponse, HttpStatus.OK);
        }
    }
}
