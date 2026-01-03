package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.delete.DeleteHomeResponse;
import com.arnzen.home_api_backend.model.delete.DeleteLocationResponse;
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
//    @DeleteMapping("home/{homeId}")
//    public ResponseEntity<DeleteHomeResponse> deleteHome(@PathVariable int homeId) {
//        return deleteService.deleteHome(homeId);
//    }

    @DeleteMapping("location/{locationId}")
    public ResponseEntity<DeleteLocationResponse> deleteLocation(@PathVariable int locationId) {
        return deleteService.deleteLocation(locationId);
    }

    @DeleteMapping("device/{deviceId}")
    public ResponseEntity<Integer> deleteDevice(@PathVariable int deviceId) {
        return deleteService.deleteDevice(deviceId);
    }
}
