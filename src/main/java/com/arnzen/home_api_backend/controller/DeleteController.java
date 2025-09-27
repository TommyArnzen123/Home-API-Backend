package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.DeviceEntity;
import com.arnzen.home_api_backend.model.GetDeviceResponse;
import com.arnzen.home_api_backend.model.GetHomeResponse;
import com.arnzen.home_api_backend.service.DeleteService;
import com.arnzen.home_api_backend.service.GetInfoService;
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
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
