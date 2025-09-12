package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.DeviceEntity;
import com.arnzen.home_api_backend.model.GetHomeResponse;
import com.arnzen.home_api_backend.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("delete")
public class DeleteController {

    @Autowired
    DeleteService deleteService;

    // Deletes the specified device (if it exists), and removes all temperature information
    // associated with the specified device.
    @DeleteMapping("device/{deviceId}")
    public ResponseEntity<DeviceEntity> deleteDevice(@PathVariable int deviceId) {
        System.out.println("Delete endpoint hit.");
        DeviceEntity removedDevice = deleteService.deleteDevice(deviceId);

        if (removedDevice != null) {
            return new ResponseEntity<>(removedDevice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
