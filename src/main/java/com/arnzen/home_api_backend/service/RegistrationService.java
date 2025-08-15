package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.UserDao;
import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.model.HomeEntity;
import com.arnzen.home_api_backend.model.UserEntity;
import com.arnzen.home_api_backend.model.LocationEntity;
import com.arnzen.home_api_backend.model.DeviceEntity;
import com.arnzen.home_api_backend.model.RegisterHomeInfo;
import com.arnzen.home_api_backend.model.RegisterLocationInfo;
import com.arnzen.home_api_backend.model.RegisterDeviceInfo;
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

    @Autowired
    LocationDao locationDao;

    @Autowired
    DeviceDao deviceDao;

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

    public ResponseEntity<LocationEntity> registerLocation(RegisterLocationInfo locationInfo) {
        Optional<HomeEntity> home = homeDao.findById(locationInfo.getHomeId());
        if (home.isPresent()) {
            LocationEntity location = new LocationEntity();
            location.setHomeEntity(home.get());
            location.setLocationName(locationInfo.getLocationName());
            LocationEntity newLocation = locationDao.save(location);
            return new ResponseEntity<>(newLocation, HttpStatus.OK);
        } else {

            // The home was not found in the database.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<DeviceEntity> registerDevice(RegisterDeviceInfo deviceInfo) {
        Optional<LocationEntity> location = locationDao.findById(deviceInfo.getLocationId());
        if (location.isPresent()) {
            DeviceEntity device = new DeviceEntity();
            device.setLocationEntity(location.get());
            device.setDeviceName(deviceInfo.getDeviceName());
            DeviceEntity newDevice = deviceDao.save(device);
            return new ResponseEntity<>(newDevice, HttpStatus.OK);
        } else {

            // The location was not found in the database.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
