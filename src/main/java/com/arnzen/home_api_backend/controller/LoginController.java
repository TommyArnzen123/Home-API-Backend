package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.login.LoginResponse;
import com.arnzen.home_api_backend.model.login.LoginUserInfo;
import com.arnzen.home_api_backend.model.base.UserEntity;
import com.arnzen.home_api_backend.service.GetInfoService;
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

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserInfo loginUserInfo) {
        return this.loginService.login(loginUserInfo);
    }
}
