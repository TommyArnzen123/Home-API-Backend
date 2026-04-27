package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.messageResponse.MessageResponse;
import com.arnzen.home_api_backend.model.temperature.TemperatureThresholdRequest;
import com.arnzen.home_api_backend.service.TemperatureThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("temperatureThreshold")
public class TemperatureThresholdController {

    @Autowired
    TemperatureThresholdService temperatureThresholdService;

    @PostMapping()
    public ResponseEntity<MessageResponse> addTemperatureThreshold(@RequestBody TemperatureThresholdRequest threshold) {
        return this.temperatureThresholdService.addTemperatureThreshold(threshold);
    }

    @PutMapping()
    public ResponseEntity<MessageResponse> updateTemperatureThreshold(@RequestBody TemperatureThresholdRequest threshold) {
        return this.temperatureThresholdService.updateTemperatureThreshold(threshold);
    }

    @DeleteMapping("/{thresholdId}")
    public ResponseEntity<MessageResponse> deleteTemperatureThreshold(@PathVariable int thresholdId) {
        return this.temperatureThresholdService.deleteTemperatureThreshold(thresholdId);
    }

}
