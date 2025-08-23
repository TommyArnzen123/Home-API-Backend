package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.*;
import com.arnzen.home_api_backend.service.GetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("getInfo")
public class GetInfoController {

    @Autowired
    GetInfoService getInfoService;

    @GetMapping("homesByUser/{userId}")
    public ResponseEntity<List<GetHomeResponse>> getHomesByUser(@PathVariable int userId) {
        return getInfoService.getHomesByUser(userId);
    }

    // Locations by homeId.

    @GetMapping("devicesByLocation/{locationId}")
    public ResponseEntity<List<GetDeviceResponse>> getDevicesByLocation(@PathVariable int locationId) {
        return getInfoService.getDevicesByLocation(locationId);
    }

    @GetMapping("temperaturesByDeviceCurrentDay/{deviceId}")
    public ResponseEntity<List<GetTemperatureResponse>> getTemperaturesByDeviceCurrentDay(@PathVariable int deviceId) {
        return getInfoService.getTemperaturesByDevice(deviceId);
    }
}
