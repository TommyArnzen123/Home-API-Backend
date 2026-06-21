package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.UserDao;
import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.model.base.HomeEntity;
import com.arnzen.home_api_backend.model.registration.RegisterItem;
import com.arnzen.home_api_backend.model.messageResponse.MessageResponse;
import com.arnzen.home_api_backend.model.base.UserEntity;
import com.arnzen.home_api_backend.model.base.LocationEntity;
import com.arnzen.home_api_backend.model.base.DeviceEntity;
import com.arnzen.home_api_backend.service.notification.email.EmailService;
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

    @Autowired
    EmailService emailService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<MessageResponse> registerUser(UserEntity user) {

        // Get the username entered by the user.
        String normalizedUsername = user.getUsername().toLowerCase();

        // Check if the username is already in the database.
        boolean usernameExists = userDao.existsByUsernameIgnoreCase(normalizedUsername);

        if (usernameExists) {

            // If the username is already in the database, return an error response.
            return new ResponseEntity<>(generateRegistrationResponse("Error registering user."), HttpStatus.CONFLICT);
        }

        // Check if the email address is already in the database.
        boolean emailExists = userDao.existsByEmailIgnoreCase(user.getEmail().toLowerCase());

        if (emailExists) {

            // If the email address is already associated with an account, return an error.
            return new ResponseEntity<>(generateRegistrationResponse("Error registering user."), HttpStatus.CONFLICT);
        }

        // Encode the password entered by the user.
        user.setPassword(encoder.encode(user.getPassword()));

        // Save the new user object in the database.
        UserEntity newlyRegisteredUser = userDao.save(user);

        // Send the user registration email.
//        emailService.sendUserRegistrationEmail(newlyRegisteredUser);

        return new ResponseEntity<>(generateRegistrationResponse("User added successfully."), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> registerHome(RegisterItem homeInfo) {
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

    public ResponseEntity<MessageResponse> registerLocation(RegisterItem locationInfo) {
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

    public ResponseEntity<MessageResponse> registerDevice(RegisterItem deviceInfo) {
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

    private MessageResponse generateRegistrationResponse(String message) {
        return new MessageResponse(message);
    }
}
