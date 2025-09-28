package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.*;
import com.arnzen.home_api_backend.service.DeleteService;
import com.arnzen.home_api_backend.service.GetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("delete")
public class DeleteController {

    @Autowired
    DeleteService deleteService;

    @Autowired
    GetInfoService getInfoService;

    // Deletes the specified device (if it exists), and removes all temperature information
    // associated with the specified device.
    @DeleteMapping("device/{locationId}/{deviceId}")
    public ResponseEntity<List<GetDeviceResponse>> deleteDevice(@PathVariable int locationId, @PathVariable int deviceId) {
        DeviceEntity removedDevice = deleteService.deleteDevice(deviceId);

        if (removedDevice != null) {

            // Get the remaining devices for the specified location.
            List<GetDeviceResponse> remainingDevices = getInfoService.getDevicesByLocation(locationId);


            return new ResponseEntity<>(remainingDevices, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }

    // Deletes the specified location (if it exists), and removes all devices and temperature information associated with the specified location.
    @DeleteMapping("location/{homeId}/{locationId}")
    public ResponseEntity<List<GetLocationResponse>> deleteLocation(@PathVariable int homeId, @PathVariable int locationId) {
        LocationEntity removedLocation = deleteService.deleteLocation(locationId);

        if (removedLocation != null) {

            // Get the remaining locations for the specified home.
            List<GetLocationResponse> remainingLocations = getInfoService.getLocationsByHome(homeId);

            return new ResponseEntity<>(remainingLocations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
    }
}
