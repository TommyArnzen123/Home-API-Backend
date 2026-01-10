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

            Optional<HomeEntity> homeEntity = homeDao.findById(homeId);

            if (homeEntity.isPresent()) {

                int totalLocationsBeforeDelete = homeDao.countTotalLocations(homeId);
                int totalDevicesBeforeDelete = homeDao.countTotalDevices(homeId);

                deleteHomeById(homeId);

                int totalHomesAfterDelete = homeDao.countTotalHomes(homeId);
                int totalLocationsAfterDelete = homeDao.countTotalLocations(homeId);
                int totalDevicesAfterDelete = homeDao.countTotalDevices(homeId);
                int totalTemperatureReadingsAfterDelete = homeDao.countTotalTemperatureReadings(homeId);

                if (totalHomesAfterDelete == 0 && totalLocationsAfterDelete == 0 && totalDevicesAfterDelete == 0 && totalTemperatureReadingsAfterDelete == 0) {
                    return new ResponseEntity<>(new DeleteHomeResponseEntity(
                            homeId, totalLocationsBeforeDelete, totalDevicesBeforeDelete, homeEntity.get().getUserEntity().getId()), HttpStatus.OK);
                } else {

                    // There was an error deleting the specified home or during the cascading delete action
                    // of the associated locations, devices, and/or temperature readings.
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                // The specified home was not found. Return an error.
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (EmptyResultDataAccessException exception) {
            // The specified home was not found. Return an error.
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<DeleteLocationResponseEntity> deleteLocation(int locationId) throws EmptyResultDataAccessException {

        try {

            Optional<LocationEntity> locationEntity = locationDao.findById(locationId);

            if (locationEntity.isPresent()) {


                int totalDevicesBeforeDelete = locationDao.countTotalDevices(locationId);

                // Delete the specified location.
                // This action will cascade and delete all associated devices and temperature readings.
                deleteLocationById(locationId);

                int totalLocationsAfterDelete = locationDao.countTotalLocations(locationId);
                int totalDevicesAfterDelete = locationDao.countTotalDevices(locationId);
                int totalTemperatureReadingsAfterDelete = locationDao.countTotalTemperatureReadings(locationId);

                if (totalLocationsAfterDelete == 0 && totalDevicesAfterDelete == 0 && totalTemperatureReadingsAfterDelete == 0) {
                    return new ResponseEntity<>(
                            new DeleteLocationResponseEntity(locationId, totalDevicesBeforeDelete,
                                    locationEntity.get().getHomeEntity().getId()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }

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
    public ResponseEntity<DeleteDeviceResponseEntity> deleteDevice(int deviceId) throws EmptyResultDataAccessException {

        try {
            Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);

            if (deviceEntity.isPresent()) {

                // Create the return object.
                DeleteDeviceResponseEntity deleteDeviceResponse =
                        new DeleteDeviceResponseEntity(deviceId, deviceEntity.get().getLocationEntity().getId());

                // Delete the specified device.
                // This action will cascade and delete all associated temperature readings.
                deleteDeviceById(deviceId);

                // Verify the device and temperature readings were deleted.
                int totalDevices = deviceDao.countTotalDevices(deviceId);
                int totalTemperatureReadings = deviceDao.countTotalTemperatureReadings(deviceId);

                if (totalDevices == 0 && totalTemperatureReadings == 0) {

                    // The specified device and associated temperature readings were deleted.
                    return new ResponseEntity<>(deleteDeviceResponse, HttpStatus.OK);
                } else {
                    // There was an error deleting the device or associated temperature readings.
                    // Return an error.
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                // The specified device was not found. Return an error.
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

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
