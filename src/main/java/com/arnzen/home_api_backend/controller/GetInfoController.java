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

//    @GetMapping("homesByUser/{userId}")
//    public ResponseEntity<List<GetHomeResponse>> getHomesByUser(@PathVariable int userId) {
//        List<GetHomeResponse> homes = getInfoService.getHomesByUser(userId);
//
//        if (homes.isEmpty()) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(homes, HttpStatus.OK);
//        }
//    }

//    @GetMapping("locationsByHome/{homeId}")
//    public ResponseEntity<List<GetLocationResponse>> getLocationsByHome(@PathVariable int homeId){
//        List<GetLocationResponse> locations = getInfoService.getLocationsByHome(homeId);
//
//        if (locations.isEmpty()) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(locations, HttpStatus.OK);
//        }
//    }

    @GetMapping("devicesByLocation/{locationId}")
    public ResponseEntity<List<GetDeviceResponse>> getDevicesByLocation(@PathVariable int locationId) {
        List<GetDeviceResponse> devices = getInfoService.getDevicesByLocation(locationId);

        if (devices.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(devices, HttpStatus.OK);
        }
    }

    @GetMapping("informationByLocation/{locationId}")
    public ResponseEntity<GetLocationResponse> getInformationByLocation(@PathVariable int locationId) {
        GetLocationResponse locationResponse = getInfoService.getInformationByLocation(locationId);

        if (locationResponse != null) {
            return new ResponseEntity<>(locationResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("informationByDeviceCurrentDay/{deviceId}")
    public ResponseEntity<ViewDeviceResponse> getInformationByDeviceCurrentDay(@PathVariable int deviceId) {

        ViewDeviceResponse viewDeviceResponse = getInfoService.getTemperaturesByDeviceCurrentDay(deviceId);

        if (viewDeviceResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(viewDeviceResponse, HttpStatus.OK);
        }
    }

    @GetMapping("homeScreenInfo/{userId}")
    public ResponseEntity<HomeScreenInfoResponseEntity> getHomeScreenInfo(@PathVariable int userId) {
        return getInfoService.getHomeScreenInfo(userId);
    }

    @GetMapping("viewHomeInfo/{homeId}")
    public ResponseEntity<ViewHomeResponseEntity> getViewHomeInfo(@PathVariable int homeId) {
        return getInfoService.getViewHomeInfo(homeId);
    }
}
