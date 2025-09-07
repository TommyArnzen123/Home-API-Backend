package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    TemperatureDao temperatureDao;

    @Transactional
    public boolean deleteDevice(int deviceId) throws EmptyResultDataAccessException {
        try {
            temperatureDao.deleteAllByDeviceEntityId(deviceId);
            deviceDao.deleteById(deviceId);
            return true;    // Device found in the database and removed.
        } catch (EmptyResultDataAccessException exception) {
            return false;   // Device not found in the database and error thrown.
        }
    }
}
