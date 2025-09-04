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
        List<GetHomeResponse> homes = getInfoService.getHomesByUser(userId);

        if (homes.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(homes, HttpStatus.OK);
        }
    }

    @GetMapping("locationsByHome/{homeId}")
    public ResponseEntity<List<GetLocationResponse>> getLocationsByHome(@PathVariable int homeId){
        List<GetLocationResponse> locations = getInfoService.getLocationsByHome(homeId);

        if (locations.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(locations, HttpStatus.OK);
        }
    }

    @GetMapping("devicesByLocation/{locationId}")
    public ResponseEntity<List<GetDeviceResponse>> getDevicesByLocation(@PathVariable int locationId) {
        return getInfoService.getDevicesByLocation(locationId);
    }

    @GetMapping("temperaturesByDeviceCurrentDay/{deviceId}")
    public ResponseEntity<List<GetTemperatureResponse>> getTemperaturesByDeviceCurrentDay(@PathVariable int deviceId) {
        return getInfoService.getTemperaturesByDevice(deviceId);
    }

    @GetMapping("homeScreenInfo/{userId}")
    public ResponseEntity<HomeScreenInfoResponseEntity> getHomeScreenInfo(@PathVariable int userId) {

        HomeScreenInfoResponseEntity homeScreenInfoResponseEntity = new HomeScreenInfoResponseEntity();

        int totalLocations = 0;
        int totalDevices = 0;

        List<GetHomeResponse> homes = getInfoService.getHomesByUser(userId);


        // Get the home information.
        homeScreenInfoResponseEntity.setHomes(homes);

        // Get the location information.
        homeScreenInfoResponseEntity.setNumLocations(totalLocations);

        // Get the device information.
        homeScreenInfoResponseEntity.setNumDevices(totalDevices);



        return new ResponseEntity<>(homeScreenInfoResponseEntity, HttpStatus.OK);
    }
}
