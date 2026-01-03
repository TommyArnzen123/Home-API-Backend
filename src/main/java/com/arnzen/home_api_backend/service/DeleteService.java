package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import com.arnzen.home_api_backend.model.base.DeviceEntity;
import com.arnzen.home_api_backend.model.base.HomeEntity;
import com.arnzen.home_api_backend.model.base.LocationEntity;
import com.arnzen.home_api_backend.model.delete.DeleteHomeResponse;
import com.arnzen.home_api_backend.model.delete.DeleteLocationResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeleteService {

    @Autowired
    HomeDao homeDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    TemperatureDao temperatureDao;

    @Autowired
    GetInfoService getInfoService;

//    @Transactional
//    public ResponseEntity<DeleteHomeResponse> deleteHome(int homeId) throws EmptyResultDataAccessException {
//
//        try {
//
//            Optional<HomeEntity> homeEntity = homeDao.findById(homeId);
//
//            if (homeEntity.isPresent()) {
//
//                // Get the list of locations registered with the home.
//
//
//
//                List<DeviceEntity> devices = getInfoService.getAllDevicesByLocationId(locationId);
//                deleteListOfDevices(devices);
//
//                deleteLocationById(locationId);
//
//                return new ResponseEntity<>(new DeleteLocationResponse(locationId, devices.size()), HttpStatus.OK);
//
//            } else {
//                // The specified home was not found. Return an error.
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }
//
//        } catch (EmptyResultDataAccessException exception) {
//            // The specified location was not found. Return an error.
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

    @Transactional
    public ResponseEntity<DeleteLocationResponse> deleteLocation(int locationId) throws EmptyResultDataAccessException {

        try {

            Optional<LocationEntity> locationEntity = locationDao.findById(locationId);

            if (locationEntity.isPresent()) {

                // Get the list of devices registered with the location.
                List<DeviceEntity> devices = getInfoService.getAllDevicesByLocationId(locationId);
                deleteListOfDevices(devices);

                deleteLocationById(locationId);

                return new ResponseEntity<>(new DeleteLocationResponse(locationId, devices.size()), HttpStatus.OK);

            } else {
                // The specified location was not found. Return an error.
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (EmptyResultDataAccessException exception) {
            // The specified location was not found. Return an error.
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<Integer> deleteDevice(int deviceId) throws EmptyResultDataAccessException {

        try {

            Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);

            if (deviceEntity.isPresent()) {
                deleteTemperaturesByDeviceId(deviceId);
                deleteDeviceById(deviceId);
                return new ResponseEntity<>(deviceId, HttpStatus.OK);
            } else {
                // The specified device was not found. Return an error.
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (EmptyResultDataAccessException exception) {
            // The specified device was not found. Return an error.
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a list of devices.
    private void deleteListOfDevices(List<DeviceEntity> devices) {
        for (DeviceEntity device : devices) {
            deleteTemperaturesByDeviceId(device.getId());
            deleteDeviceById(device.getId());
        }
    }

    // Delete the specified home.
    private void deleteHomeById(int homeId) {
        homeDao.deleteById(homeId);
    }

    // Delete the specified location.
    private void deleteLocationById(int locationId) {
        locationDao.deleteById(locationId);
    }

    // Delete the specified device.
    private void deleteDeviceById(int deviceId) {
        deviceDao.deleteById(deviceId);
    }

    // Delete all temperature readings for the specified device.
    private void deleteTemperaturesByDeviceId(int deviceId) {
        temperatureDao.deleteAllByDeviceEntityId(deviceId);
    }
}
