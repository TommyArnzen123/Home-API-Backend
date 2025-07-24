package com.arnzen.home_api_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegistrationController {

    // Allow a new user to be registered with the application.
    @PostMapping("user")
    public ResponseEntity<String> registerUser() {
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }

    // Allow a new home to be registered and associated with a registered user.
    @PostMapping("home")
    public ResponseEntity<String> registerHome() {
        return new ResponseEntity<>("Home registered", HttpStatus.OK);
    }

    // Allow a new location to be registered and associated with a registered home.


    // Allow a new device to be registered and associated with a registered location.

}
