package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.model.base.HomeEntity;
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
}
