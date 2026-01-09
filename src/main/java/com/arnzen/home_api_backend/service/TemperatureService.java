package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import com.arnzen.home_api_backend.model.base.DeviceEntity;
import com.arnzen.home_api_backend.model.base.TemperatureEntity;
import com.arnzen.home_api_backend.model.temperature.SaveTemperatureInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TemperatureService {

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    TemperatureDao temperatureDao;

    public ResponseEntity<TemperatureEntity> saveTemperatureByDevice(SaveTemperatureInfo registerTemperatureInfo) {
        Optional<DeviceEntity> device = deviceDao.findById(registerTemperatureInfo.getDeviceId());
        if (device.isPresent()) {
            TemperatureEntity temperature = new TemperatureEntity();
            temperature.setDeviceEntity(device.get());
            temperature.setTemperature(registerTemperatureInfo.getTemperature());
            temperature.setDateRecorded(LocalDateTime.now());
            TemperatureEntity newTemperature = temperatureDao.save(temperature);
            return new ResponseEntity<>(newTemperature, HttpStatus.OK);
        } else {

            // The device was not found in the database.
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
