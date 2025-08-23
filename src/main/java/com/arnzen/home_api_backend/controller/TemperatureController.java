package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.RegisterTemperatureInfo;
import com.arnzen.home_api_backend.model.TemperatureEntity;
import com.arnzen.home_api_backend.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("temperature")
public class TemperatureController {

    @Autowired
    TemperatureService temperatureService;

    @PostMapping("save")
    public ResponseEntity<TemperatureEntity> saveTemperatureByDevice(@RequestBody RegisterTemperatureInfo registerTemperatureInfo) {
        return temperatureService.saveTemperatureByDevice(registerTemperatureInfo);
    }
}
