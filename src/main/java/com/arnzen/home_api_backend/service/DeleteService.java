package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import com.arnzen.home_api_backend.model.DeviceEntity;
import com.arnzen.home_api_backend.model.GetDeviceResponse;
import com.arnzen.home_api_backend.model.LocationEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeleteService {

    @Autowired
    LocationDao locationDao;

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    TemperatureDao temperatureDao;

    @Autowired
    GetInfoService getInfoService;

    @Transactional
    public DeviceEntity deleteDevice(int deviceId) throws EmptyResultDataAccessException {
        try {

            Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);

            if (deviceEntity.isPresent()) {
                deleteTemperaturesByDeviceId(deviceId);
                deleteDeviceById(deviceId);
                return deviceEntity.get();    // Device found in the database and removed.
            } else {
                return null; // Device not found in the database. No error thrown.
            }
        } catch (EmptyResultDataAccessException exception) {
            return null;   // Device not found in the database and error thrown.
        }
    }

    @Transactional
    public LocationEntity deleteLocation(int locationId) throws EmptyResultDataAccessException {
        try {

            Optional<LocationEntity> locationEntity = locationDao.findById(locationId);

            if (locationEntity.isPresent()) {

                // Get a list of devices for the location.
                List<GetDeviceResponse> devices = getInfoService.getDevicesByLocation(locationId);

                if (devices != null) {
                    // Delete temperature information for each device and then delete the device.
                    devices.forEach(device -> {
                        temperatureDao.deleteAllByDeviceEntityId(device.getDeviceId());
                        deviceDao.deleteById(device.getDeviceId());
                    });
                }

                // Delete the location.
                locationDao.deleteById(locationId);

                return locationEntity.get();    // Location found in the database and removed.

            } else {
                return null; // Location not found in the database. No error thrown.
            }
        } catch (EmptyResultDataAccessException exception) {
            return null;   // Location not found in the database and error thrown.
        }
    }

    private void deleteDeviceById(int deviceId) {
        deviceDao.deleteById(deviceId);
    }

    private void deleteTemperaturesByDeviceId(int deviceId) {
        temperatureDao.deleteAllByDeviceEntityId(deviceId);
    }
}
