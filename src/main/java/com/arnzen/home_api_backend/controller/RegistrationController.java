package com.arnzen.home_api_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegistrationController {

    @PostMapping("user")
    public ResponseEntity<String> registerUser() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
