package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.delete.DeleteDeviceResponseEntity;
import com.arnzen.home_api_backend.model.delete.DeleteHomeResponseEntity;
import com.arnzen.home_api_backend.model.delete.DeleteLocationResponseEntity;
import com.arnzen.home_api_backend.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("delete")
public class DeleteController {

    @Autowired
    DeleteService deleteService;

    // Deletes the specified home (if it exists), and removes all locations, devices, and temperature information
    // associated with the specified home.
    @DeleteMapping("home/{homeId}")
    public ResponseEntity<DeleteHomeResponseEntity> deleteHome(@PathVariable int homeId) {
        return deleteService.deleteHome(homeId);
    }

    // Delete the specified location (if it exists), and removes all devices and temperature information associated
    // with the specified location.
    @DeleteMapping("location/{locationId}")
    public ResponseEntity<DeleteLocationResponseEntity> deleteLocation(@PathVariable int locationId) {
        return deleteService.deleteLocation(locationId);
    }

    // Delete the specified device (if it exists) and remove all temperature information associated with
    // the specified device.
    @DeleteMapping("device/{deviceId}")
    public ResponseEntity<DeleteDeviceResponseEntity> deleteDevice(@PathVariable int deviceId) {
        return deleteService.deleteDevice(deviceId);
    }
}
