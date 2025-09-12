package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import com.arnzen.home_api_backend.model.DeviceEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteService {

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    TemperatureDao temperatureDao;

    @Transactional
    public DeviceEntity deleteDevice(int deviceId) throws EmptyResultDataAccessException {
        try {

            Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);

            if (deviceEntity.isPresent()) {
                temperatureDao.deleteAllByDeviceEntityId(deviceId);
                deviceDao.deleteById(deviceId);
                return deviceEntity.get();    // Device found in the database and removed.
            } else {
                return null; // Device not found in the database. No error thrown.
            }
        } catch (EmptyResultDataAccessException exception) {
            return null;   // Device not found in the database and error thrown.
        }
    }
}
