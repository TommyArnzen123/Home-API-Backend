package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.UserDao;
import com.arnzen.home_api_backend.model.HomeEntity;
import com.arnzen.home_api_backend.model.RegisterHomeInfo;
import com.arnzen.home_api_backend.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    UserDao userDao;

    @Autowired
    HomeDao homeDao;

    public ResponseEntity<UserEntity> registerUser(UserEntity user) {
        UserEntity newUser = userDao.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    public ResponseEntity<HomeEntity> registerHome(RegisterHomeInfo homeInfo) {
        Optional<UserEntity> user = userDao.findById(homeInfo.getUserId());
        if (user.isPresent()) {
            HomeEntity home = new HomeEntity();
            home.setUserEntity(user.get());
            home.setHomeName(homeInfo.getHomeName());
            HomeEntity newHome = homeDao.save(home);
            return new ResponseEntity<>(newHome, HttpStatus.OK);
        } else {

            // The user was not found in the database.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
