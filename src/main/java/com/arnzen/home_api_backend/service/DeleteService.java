package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import com.arnzen.home_api_backend.model.base.DeviceEntity;
import com.arnzen.home_api_backend.model.base.HomeEntity;
import com.arnzen.home_api_backend.model.base.LocationEntity;
import com.arnzen.home_api_backend.model.delete.DeleteDeviceResponseEntity;
import com.arnzen.home_api_backend.model.delete.DeleteHomeResponseEntity;
import com.arnzen.home_api_backend.model.delete.DeleteLocationResponseEntity;
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

    @Transactional
    public ResponseEntity<DeleteHomeResponseEntity> deleteHome(int homeId) throws EmptyResultDataAccessException {
        try {

            System.out.println("Total Homes: " + homeDao.countTotalHomes(homeId));
            System.out.println("Total Locations: " + homeDao.countTotalLocations(homeId));
            System.out.println("Total Devices: " + homeDao.countTotalDevices(homeId));
            System.out.println("Total Temperature Readings: " + homeDao.countTotalTemperatureReadings(homeId));

            int totalHomes = homeDao.countTotalHomes(homeId);
            int totalLocations = homeDao.countTotalLocations(homeId);
            int totalDevices = homeDao.countTotalDevices(homeId);
            int totalTemperatureReadings = homeDao.countTotalTemperatureReadings(homeId);

//            Optional<HomeEntity> homeEntity = homeDao.findById(homeId);
//
//            if (homeEntity.isPresent()) {
//
//                deleteHomeById(homeId);
//
//                return new ResponseEntity<>(new DeleteHomeResponseEntity(
//                        homeId, 1, 1, homeEntity.get().getUserEntity().getId()), HttpStatus.OK);
//
//            } else {
//                // The specified home was not found. Return an error.
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch (EmptyResultDataAccessException exception) {
            // The specified home was not found. Return an error.
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<DeleteLocationResponseEntity> deleteLocation(int locationId) throws EmptyResultDataAccessException {

        try {

            System.out.println("Total Locations: " + locationDao.countTotalLocations(locationId));
            System.out.println("Total Devices: " + locationDao.countTotalDevices(locationId));
            System.out.println("Total Temperature Readings: " + locationDao.countTotalTemperatureReadings(locationId));

            int totalLocations = locationDao.countTotalLocations(locationId);
            int totalDevices = locationDao.countTotalDevices(locationId);
            int totalTemperatureReadings = locationDao.countTotalTemperatureReadings(locationId);

//            Optional<LocationEntity> locationEntity = locationDao.findById(locationId);
//
//            if (locationEntity.isPresent()) {
//
//                // Get the list of devices registered with the location.
//                List<DeviceEntity> devices = getInfoService.getAllDevicesByLocationId(locationId);
//
//                deleteLocationById(locationId);
//
//                return new ResponseEntity<>(
//                        new DeleteLocationResponseEntity(locationId, devices.size(),
//                                locationEntity.get().getHomeEntity().getId()), HttpStatus.OK);
//
//            } else {
//                // The specified location was not found. Return an error.
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }



            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch (EmptyResultDataAccessException exception) {
            // The specified location was not found. Return an error.
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<DeleteDeviceResponseEntity> deleteDevice(int deviceId) throws EmptyResultDataAccessException {

        System.out.println("Total Devices: " + deviceDao.countTotalDevices(deviceId));
        System.out.println("Total Temperature Readings: " + deviceDao.countTotalTemperatureReadings(deviceId));

        int totalDevices = deviceDao.countTotalDevices(deviceId);
        int totalTemperatureReadings = deviceDao.countTotalTemperatureReadings(deviceId);

        try {
//            Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);
//
//            if (deviceEntity.isPresent()) {
//                DeleteDeviceResponseEntity deleteDeviceResponse =
//                        new DeleteDeviceResponseEntity(deviceEntity.get().getId(), deviceEntity.get().getLocationEntity().getId());
//                deleteDeviceById(deviceId);
//                return new ResponseEntity<>(deleteDeviceResponse, HttpStatus.OK);
//            } else {
//                // The specified device was not found. Return an error.
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch (EmptyResultDataAccessException exception) {
            // The specified device was not found. Return an error.
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
