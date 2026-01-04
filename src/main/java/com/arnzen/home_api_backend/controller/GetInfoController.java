package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.info.HomeScreenInfoResponseEntity;
import com.arnzen.home_api_backend.model.info.ViewDeviceResponseEntity;
import com.arnzen.home_api_backend.model.info.ViewHomeResponseEntity;
import com.arnzen.home_api_backend.model.info.ViewLocationResponseEntity;
import com.arnzen.home_api_backend.service.GetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("getInfo")
public class GetInfoController {

    @Autowired
    GetInfoService getInfoService;

    // Get the required information for the main screen (home screen).
    @GetMapping("homeScreenInfo/{userId}")
    public ResponseEntity<HomeScreenInfoResponseEntity> getHomeScreenInfo(@PathVariable int userId) {
        return getInfoService.getHomeScreenInfo(userId);
    }

    // Get the required information to view a selected home.
    @GetMapping("viewHomeInfo/{homeId}")
    public ResponseEntity<ViewHomeResponseEntity> getViewHomeInfo(@PathVariable int homeId) {
        return getInfoService.getViewHomeInfo(homeId);
    }

    // TO DO: Get the required information to view a selected location.
    @GetMapping("viewLocationInfo/{locationId}")
    public ResponseEntity<ViewLocationResponseEntity> getViewLocationInfo(@PathVariable int locationId) {
        return getInfoService.getViewLocationInfo(locationId);
    }

    // Get the required information to view a selected device.
    @GetMapping("informationByDeviceCurrentDay/{deviceId}")
    public ResponseEntity<ViewDeviceResponseEntity> getInformationByDeviceCurrentDay(@PathVariable int deviceId) {
       return getInfoService.getTemperaturesByDeviceCurrentDay(deviceId);
    }
}
