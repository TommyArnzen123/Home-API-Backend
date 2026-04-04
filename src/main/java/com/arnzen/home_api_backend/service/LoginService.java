package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.model.base.UserEntity;
import com.arnzen.home_api_backend.model.login.LoginResponse;
import com.arnzen.home_api_backend.model.login.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoginService {

    @Autowired
    private GetInfoService getInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<LoginResponse> login(LoginUserInfo loginUserInfo) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUserInfo.getUsername(), loginUserInfo.getPassword()));

        if(authentication.isAuthenticated()) {
            UserEntity userEntity = getInfoService.getUserEntityByUsername(loginUserInfo.getUsername());

            LoginResponse loginResponse =
                    new LoginResponse(userEntity.getId(),
                            userEntity.getUsername(),
                            userEntity.getFirstName(),
                            jwtService.generateToken(loginUserInfo.getUsername()));
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
