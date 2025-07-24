package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.LoginUserInfo;
import com.arnzen.home_api_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginUserInfo loginUserInfo) {
        return loginService.login(loginUserInfo);
    }
}
