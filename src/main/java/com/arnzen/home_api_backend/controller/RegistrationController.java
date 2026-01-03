package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.registration.RegistrationResponse;
import com.arnzen.home_api_backend.model.registration.UserEntity;
import com.arnzen.home_api_backend.model.HomeEntity;
import com.arnzen.home_api_backend.model.LocationEntity;
import com.arnzen.home_api_backend.model.DeviceEntity;

import com.arnzen.home_api_backend.model.RegisterHomeInfo;
import com.arnzen.home_api_backend.service.RegistrationService;
import com.arnzen.home_api_backend.model.RegisterLocationInfo;
import com.arnzen.home_api_backend.model.RegisterDeviceInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("register")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    // Allow a new user to be registered with the application.
    @PostMapping("user")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody UserEntity user) {
        return registrationService.registerUser(user);
    }

    // Allow a new home to be registered and associated with a registered user.
    @PostMapping("home")
    public ResponseEntity<HomeEntity> registerHome(@RequestBody RegisterHomeInfo home) {
        return registrationService.registerHome(home);
    }

    // Allow a new location to be registered and associated with a registered home.
    @PostMapping("location")
    public ResponseEntity<LocationEntity> registerLocation(@RequestBody RegisterLocationInfo location) {
        return registrationService.registerLocation(location);
    }


    // Allow a new device to be registered and associated with a registered location.
    @PostMapping("device")
    public ResponseEntity<DeviceEntity> registerDevice(@RequestBody RegisterDeviceInfo device) {
        return registrationService.registerDevice(device);
    }
}