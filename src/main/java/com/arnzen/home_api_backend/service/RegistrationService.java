package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.UserDao;
import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.model.base.HomeEntity;
import com.arnzen.home_api_backend.model.registration.RegisterItem;
import com.arnzen.home_api_backend.model.registration.RegistrationResponse;
import com.arnzen.home_api_backend.model.base.UserEntity;
import com.arnzen.home_api_backend.model.base.LocationEntity;
import com.arnzen.home_api_backend.model.base.DeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<RegistrationResponse> registerUser(UserEntity user) {

        // Get the username entered by the user.
        String normalizedUser = user.getUsername().toLowerCase();

        // Check if the username is already in the database.
        UserEntity checkUser = userDao.findByUsernameIgnoreCase(normalizedUser);

        if (checkUser != null) {

            // If the username is already in the database, return
            // an error response.
            return new ResponseEntity<>(generateRegistrationResponse("Error adding user."), HttpStatus.CONFLICT);
        }

        // Encode the password entered by the user.
        user.setPassword(encoder.encode(user.getPassword()));

        // Save the new user object in the database.
        userDao.save(user);
        return new ResponseEntity<>(generateRegistrationResponse("User added successfully."), HttpStatus.OK);
    }

    public ResponseEntity<RegistrationResponse> registerHome(RegisterItem homeInfo) {
        Optional<UserEntity> user = userDao.findById(homeInfo.getParentEntityId());

        // The user was found in the database. Add the home.
        if (user.isPresent()) {
            HomeEntity home = new HomeEntity();
            home.setUserEntity(user.get());
            home.setHomeName(homeInfo.getName());
            homeDao.save(home);
            return new ResponseEntity<>(generateRegistrationResponse(("Home added successfully.")), HttpStatus.OK);
        } else {
            // The user was not found in the database. Do not add the home.
            return new ResponseEntity<>(generateRegistrationResponse("Error adding home."), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<RegistrationResponse> registerLocation(RegisterItem locationInfo) {
        Optional<HomeEntity> home = homeDao.findById(locationInfo.getParentEntityId());

        // The home was found in the database. Add the location.
        if (home.isPresent()) {
            LocationEntity location = new LocationEntity();
            location.setHomeEntity(home.get());
            location.setLocationName(locationInfo.getName());
            locationDao.save(location);
            return new ResponseEntity<>(generateRegistrationResponse("Location added successfully."), HttpStatus.OK);
        } else {
            // The home was not found in the database. Do not add the location.
            return new ResponseEntity<>(generateRegistrationResponse("Error adding location."), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<RegistrationResponse> registerDevice(RegisterItem deviceInfo) {
        Optional<LocationEntity> location = locationDao.findById(deviceInfo.getParentEntityId());

        // The location was found in the database. Add the device.
        if (location.isPresent()) {
            DeviceEntity device = new DeviceEntity();
            device.setLocationEntity(location.get());
            device.setDeviceName(deviceInfo.getName());
            deviceDao.save(device);
            return new ResponseEntity<>(generateRegistrationResponse("Device added successfully."), HttpStatus.OK);
        } else {

            // The location was not found in the database. Do not add the device.
            return new ResponseEntity<>(generateRegistrationResponse("Error adding device."), HttpStatus.BAD_REQUEST);
        }
    }

    private RegistrationResponse generateRegistrationResponse(String message) {
        return new RegistrationResponse(message);
    }
}
