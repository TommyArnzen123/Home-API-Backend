package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import com.arnzen.home_api_backend.model.*;
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

    @Transactional
    public ResponseEntity<DeleteHomeResponseEntity> deleteHome(int homeId) throws EmptyResultDataAccessException {
        try {

            Optional<HomeEntity> homeEntity = homeDao.findById(homeId);

            if (homeEntity.isPresent()) {

                ViewHomeInfoResponseEntity homeInfo = getInfoService.getViewHomeByIdInfo(homeId);

                // Get a list of locations for the home.
//                List<LocationEntity> locations = getInfoService.getLocationsByHome(homeId);

                if (!homeInfo.getLocations().isEmpty()) {
                    for (LocationEntity location : homeInfo.getLocations()) {
                        deleteLocation(location.getId());
                    }
                }

                homeDao.deleteById(homeId);

                DeleteHomeResponseEntity response = new DeleteHomeResponseEntity();
                response.setHomeId(homeId);
                response.setTotalLocations(homeInfo.getLocations().size());
                response.setTotalDevices(homeInfo.getNumDevices());

                return new ResponseEntity<>(response, HttpStatus.OK);

            }
        } catch (EmptyResultDataAccessException exception) {
            System.out.println("Delete Home EmptyResultDataAccessException");
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Transactional
    public void deleteLocation(int locationId) throws EmptyResultDataAccessException {
        try {

            Optional<LocationEntity> locationEntity = locationDao.findById(locationId);

            if (locationEntity.isPresent()) {

                // Get a list of devices for the location.
                List<DeviceEntity> devices = getInfoService.getAllDevicesByLocationId(locationId);

                if (!devices.isEmpty()) {
                    for(DeviceEntity device : devices) {
                        deleteDevice(device.getId());
                    }
                }

                // Delete the location.
                locationDao.deleteById(locationId);

            }
        } catch (EmptyResultDataAccessException exception) {
            System.out.println("Delete Location EmptyResultDataAccessException");
        }
    }

    @Transactional
    public void deleteDevice(int deviceId) throws EmptyResultDataAccessException {
        try {

            Optional<DeviceEntity> deviceEntity = deviceDao.findById(deviceId);

            if (deviceEntity.isPresent()) {
                deleteTemperaturesByDeviceId(deviceId); // Delete temperatures associated with device.
                deleteDeviceById(deviceId); // Delete the device.
            }
        } catch (EmptyResultDataAccessException exception) {
            System.out.println("Delete Device EmptyResultDataAccessException");
        }
    }

    private void deleteDeviceById(int deviceId) {
        deviceDao.deleteById(deviceId);
    }

    private void deleteTemperaturesByDeviceId(int deviceId) {
        temperatureDao.deleteAllByDeviceEntityId(deviceId);
    }
}
