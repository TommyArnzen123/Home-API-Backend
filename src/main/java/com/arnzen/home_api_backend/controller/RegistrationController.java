package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.registration.RegisterItem;
import com.arnzen.home_api_backend.model.registration.RegistrationResponse;
import com.arnzen.home_api_backend.model.base.UserEntity;

import com.arnzen.home_api_backend.service.RegistrationService;

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
    public ResponseEntity<RegistrationResponse> registerHome(@RequestBody RegisterItem home) {
        return registrationService.registerHome(home);
    }

    // Allow a new location to be registered and associated with a registered home.
    @PostMapping("location")
    public ResponseEntity<RegistrationResponse> registerLocation(@RequestBody RegisterItem location) {
        return registrationService.registerLocation(location);
    }

    // Allow a new device to be registered and associated with a registered location.
    @PostMapping("device")
    public ResponseEntity<RegistrationResponse> registerDevice(@RequestBody RegisterItem device) {
        return registrationService.registerDevice(device);
    }
}