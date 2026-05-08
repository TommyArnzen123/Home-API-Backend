package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.TemperatureDao;
import com.arnzen.home_api_backend.model.base.DeviceEntity;
import com.arnzen.home_api_backend.model.base.TemperatureEntity;
import com.arnzen.home_api_backend.model.base.TemperatureThresholdEntity;
import com.arnzen.home_api_backend.model.reducedData.GetTemperatureResponse;
import com.arnzen.home_api_backend.model.temperature.SaveTemperatureInfo;
import com.arnzen.home_api_backend.service.notification.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TemperatureService {

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    TemperatureDao temperatureDao;

    @Autowired
    EmailService emailService;

    public ResponseEntity<GetTemperatureResponse> saveTemperatureByDevice(SaveTemperatureInfo saveTemperatureInfo) {

        Integer deviceId = saveTemperatureInfo.getDeviceId();
        Double temperatureValue = saveTemperatureInfo.getTemperature();

        if (deviceId != null && temperatureValue != null) {
            Optional<DeviceEntity> device = deviceDao.findById(deviceId);

            if (device.isPresent()) {
                TemperatureEntity temperature = new TemperatureEntity();

                temperature.setDeviceEntity(device.get());
                temperature.setTemperature(temperatureValue);
                temperature.setDateRecorded(LocalDateTime.now());

                TemperatureEntity newTemperature = temperatureDao.save(temperature);

                checkTemperatureThresholdNotification(deviceId, temperatureValue);

                GetTemperatureResponse temperatureResponse =
                        new GetTemperatureResponse(newTemperature.getId(),
                                newTemperature.getTemperature(), newTemperature.getDateRecorded());
                return new ResponseEntity<>(temperatureResponse, HttpStatus.OK);

            } else {
                // The specified device was not found in the database.
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {
            // The deviceId and/or the temperatureValue were not sent in the request.
            // Both fields are required to store a new temperature reading in the application.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public void checkTemperatureThresholdNotification(Integer deviceId, Double temperature) {

        Optional<DeviceEntity> device = deviceDao.findById(deviceId);

        // Check to see if the device specified exists.
        if (device.isPresent()) {

            TemperatureThresholdEntity threshold =
                    device.get().getLocationEntity().getTemperatureThresholdEntity();

            // Check to see if the location associated with the specified device
            // has a temperature threshold set.
            if (threshold != null) {
                Double minimumTemperature = threshold.getMinimumTemperature();
                Double maximumTemperature = threshold.getMaximumTemperature();

                if ((minimumTemperature != null && temperature < minimumTemperature)
                        || (maximumTemperature != null && temperature > maximumTemperature)) {
//                    generateTemperatureThresholdNotificationEmail(
//                            device.get(),
//                            temperature,
//                            minimumTemperature,
//                            maximumTemperature);
                }
            }
        }
    }

    public void generateTemperatureThresholdNotificationEmail(DeviceEntity device,
                                                              Double temperature,
                                                              Double minimumTemperature,
                                                              Double maximumTemperature) {

        String locationName = device.getLocationEntity().getLocationName();
        String homeName = device.getLocationEntity().getHomeEntity().getHomeName();
        String ownerFirstName = device.getLocationEntity().getHomeEntity().getUserEntity().getFirstName();
        String ownerEmailAddress = device.getLocationEntity().getHomeEntity().getUserEntity().getEmail();

        emailService.sendTemperatureThresholdViolationEmail(
                temperature, minimumTemperature, maximumTemperature,
                homeName, locationName, ownerFirstName, ownerEmailAddress);
    }
}
