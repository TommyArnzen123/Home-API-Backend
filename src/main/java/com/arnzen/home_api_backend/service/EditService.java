package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.model.base.HomeEntity;
import com.arnzen.home_api_backend.model.base.LocationEntity;
import com.arnzen.home_api_backend.model.edit.EditRequest;
import com.arnzen.home_api_backend.model.messageResponse.MessageResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EditService {

    @Autowired
    HomeDao homeDao;

    @Autowired
    LocationDao locationDao;

    @Transactional
    public ResponseEntity<MessageResponse> editHome(EditRequest home) throws EmptyResultDataAccessException {
        try {
            Optional<HomeEntity> homeEntity = homeDao.findById(home.getEntityId());

            if (homeEntity.isPresent()) {

                homeEntity.get().setHomeName(home.getName());
                homeDao.save(homeEntity.get());

                return new ResponseEntity<>(new MessageResponse("Home Edited."), HttpStatus.OK);

            } else {
                // The specified home was not found. Return an error.
                return new ResponseEntity<>(new MessageResponse("Home not found."), HttpStatus.NOT_FOUND);
            }

        } catch (EmptyResultDataAccessException exception) {
            // The specified home was not found. Return an error.
            return new ResponseEntity<>(new MessageResponse("Home not found."), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<MessageResponse> editLocation(EditRequest location) throws EmptyResultDataAccessException {

        try {
            Optional<LocationEntity> locationEntity = locationDao.findById(location.getEntityId());

            if (locationEntity.isPresent()) {

                locationEntity.get().setLocationName(location.getName());
                locationDao.save(locationEntity.get());

                return new ResponseEntity<>(new MessageResponse("Location Edited."), HttpStatus.OK);

            } else {
                // The specified location was not found. Return an error.
                return new ResponseEntity<>(new MessageResponse("Location not found."), HttpStatus.NOT_FOUND);
            }

        } catch (EmptyResultDataAccessException exception) {
            // The specified location was not found. Return an error.
            return new ResponseEntity<>(new MessageResponse("Location not found."), HttpStatus.NOT_FOUND);
        }
    }
}
