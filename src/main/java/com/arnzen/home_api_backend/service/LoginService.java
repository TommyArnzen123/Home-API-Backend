package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.UserDao;
import com.arnzen.home_api_backend.model.LoginUserInfo;
import com.arnzen.home_api_backend.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    UserDao userDao;

    public ResponseEntity<String> login(LoginUserInfo loginUserInfo) {
        List<UserEntity> userEntity = userDao.findByUsername(loginUserInfo.getUsername());

        if (userEntity.isEmpty()) {
            return new ResponseEntity<>("Username not found.", HttpStatus.BAD_REQUEST);
        } else {
            if (userEntity.get(0).getPassword().equals(loginUserInfo.getPassword())) {
                return new ResponseEntity<>("Success - user logged in.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect username/password combination.", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
