package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.RegistrationDao;
import com.arnzen.home_api_backend.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    RegistrationDao registrationDao;

    public ResponseEntity<UserEntity> registerUser(UserEntity user) {
        UserEntity newUser = registrationDao.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
