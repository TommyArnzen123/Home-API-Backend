package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.LoginUserInfo;
import com.arnzen.home_api_backend.service.JwtService;
import com.arnzen.home_api_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginUserInfo loginUserInfo) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUserInfo.getUsername(), loginUserInfo.getPassword()));

        if(authentication.isAuthenticated()) {
            Map<String, String> loginResponse = new HashMap<>();
            loginResponse.put("jwtToken", jwtService.generateToken(loginUserInfo.getUsername()));
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
