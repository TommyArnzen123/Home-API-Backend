package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.LocationDao;
import com.arnzen.home_api_backend.dao.TemperatureThresholdDao;
import com.arnzen.home_api_backend.model.base.LocationEntity;
import com.arnzen.home_api_backend.model.base.TemperatureThresholdEntity;
import com.arnzen.home_api_backend.model.messageResponse.MessageResponse;
import com.arnzen.home_api_backend.model.temperature.TemperatureThreshold;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TemperatureThresholdService {

    private final String updateTemperatureThresholdErrorMessage = "There was an error updating the temperature threshold.";
    private final String deleteTemperatureThresholdErrorMessage = "There was an error deleting the temperature threshold.";
    private final String updateTemperatureThresholdSuccessMessage = "The temperature threshold was updated.";
    private final String deleteTemperatureThresholdSuccessMessage = "The temperature threshold was deleted.";

    @Autowired
    LocationDao locationDao;

    @Autowired
    TemperatureThresholdDao temperatureThresholdDao;

    public ResponseEntity<TemperatureThreshold> addTemperatureThreshold(TemperatureThreshold threshold) {

        Integer locationId = threshold.getLocationId();
        Double minimumTemperature = threshold.getMinimumTemperature();
        Double maximumTemperature = threshold.getMaximumTemperature();

        // Verify required fields are set in the request.
        if (locationId != null && (minimumTemperature != null || maximumTemperature != null)) {

            Optional<LocationEntity> location = locationDao.findById(locationId);

            // Verify the specified location exists.
            if (location.isPresent()) {

                TemperatureThresholdEntity thresholdEntity = location.get().getTemperatureThresholdEntity();

                // Verify the specified existing location does not have a current temperature threshold.
                // Only one temperature threshold is allowed per location.
                if (thresholdEntity == null) {

                    TemperatureThresholdEntity newThreshold = new TemperatureThresholdEntity();

                    if (minimumTemperature != null) {
                        newThreshold.setMinimumTemperature(minimumTemperature);
                    }

                    if (maximumTemperature != null) {
                        newThreshold.setMaximumTemperature(maximumTemperature);
                    }

                    newThreshold.setLocationEntity(location.get());
                    TemperatureThresholdEntity savedThreshold = temperatureThresholdDao.save(newThreshold); // Save the new temperature threshold.

                    threshold.setId(savedThreshold.getId());

                    return new ResponseEntity<>(threshold, HttpStatus.OK);
                } else {
                    // There is already a registered threshold for the specified location.
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            } else {

                // The specified location does not exist.
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {

            // The required fields are not set in the request.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<MessageResponse> updateTemperatureThreshold(TemperatureThreshold threshold) {

        Integer locationId = threshold.getLocationId();
        Double minimumTemperature = threshold.getMinimumTemperature();
        Double maximumTemperature = threshold.getMaximumTemperature();

        // Verify required fields are set in the request.
        if (locationId != null && (minimumTemperature != null || maximumTemperature != null)) {

            Optional<LocationEntity> location = locationDao.findById(locationId);

            // Verify the specified location exists.
            if (location.isPresent()) {

                TemperatureThresholdEntity thresholdEntity = location.get().getTemperatureThresholdEntity();

                if (thresholdEntity != null) {

                        thresholdEntity.setMinimumTemperature(minimumTemperature);
                        thresholdEntity.setMaximumTemperature(maximumTemperature);

                        temperatureThresholdDao.save(thresholdEntity); // Update the existing temperature threshold.

                        return new ResponseEntity<MessageResponse>(generateThresholdResponse(updateTemperatureThresholdSuccessMessage), HttpStatus.OK);
                } else {
                    return new ResponseEntity<MessageResponse>(generateThresholdResponse(updateTemperatureThresholdErrorMessage), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<MessageResponse>(generateThresholdResponse(updateTemperatureThresholdErrorMessage), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<MessageResponse>(generateThresholdResponse(updateTemperatureThresholdErrorMessage), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<MessageResponse> deleteTemperatureThreshold(int thresholdId) {

        try {
            Optional<TemperatureThresholdEntity> threshold = temperatureThresholdDao.findById(thresholdId);

            if (threshold.isPresent()) {

                LocationEntity location = threshold.get().getLocationEntity();

                location.setTemperatureThresholdEntity(null);
                locationDao.save(location);
                temperatureThresholdDao.deleteById(thresholdId);

                return new ResponseEntity<MessageResponse>(generateThresholdResponse(deleteTemperatureThresholdSuccessMessage), HttpStatus.OK);
            } else {

                // The specified threshold does not exist.
                return new ResponseEntity<MessageResponse>(generateThresholdResponse(deleteTemperatureThresholdErrorMessage), HttpStatus.BAD_REQUEST);
            }
        } catch (EmptyResultDataAccessException exception) {
            // The specified threshold was not found. Return an error.
            return new ResponseEntity<MessageResponse>(generateThresholdResponse(deleteTemperatureThresholdErrorMessage), HttpStatus.BAD_REQUEST);
        }
    }

    private MessageResponse generateThresholdResponse(String message) {
        return new MessageResponse(message);
    }
}
