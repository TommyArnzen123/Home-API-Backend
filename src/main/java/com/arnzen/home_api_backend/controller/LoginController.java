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

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginUserInfo loginUserInfo) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUserInfo.getUsername(), loginUserInfo.getPassword()));

        if(authentication.isAuthenticated())
            return new ResponseEntity<>(jwtService.generateToken(loginUserInfo.getUsername()), HttpStatus.OK);
        else
            return new ResponseEntity<>("Login Failed", HttpStatus.BAD_REQUEST);
    }
}
