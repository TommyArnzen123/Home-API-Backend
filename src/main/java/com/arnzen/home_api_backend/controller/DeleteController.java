package com.arnzen.home_api_backend.controller;

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
    public ResponseEntity<String> deleteDevice(@PathVariable int deviceId) {
        boolean deviceDeleted = deleteService.deleteDevice(deviceId);

        if (deviceDeleted) {
            return new ResponseEntity<>("Removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Device not removed", HttpStatus.NOT_FOUND);
        }
    }
}
