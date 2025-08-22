package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.DeviceEntity;
import com.arnzen.home_api_backend.model.HomeEntity;
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
    private ResponseEntity<List<HomeEntity>> getHomesByUser(@PathVariable int userId) {
        return getInfoService.getHomesByUser(userId);
    }

    @GetMapping("devicesByLocation/{locationId}")
    private ResponseEntity<List<DeviceEntity>> getDevicesByLocation(@PathVariable int locationId) {
        return getInfoService.getDevicesByLocation(locationId);
    }
}
