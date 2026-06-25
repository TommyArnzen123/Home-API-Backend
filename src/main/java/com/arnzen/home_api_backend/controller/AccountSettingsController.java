package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.accountSettings.UpdateTemperatureDisplaySettingRequest;
import com.arnzen.home_api_backend.model.accountSettings.UpdateTimeDisplaySettingRequest;
import com.arnzen.home_api_backend.model.messageResponse.MessageResponse;
import com.arnzen.home_api_backend.model.reducedData.GetAccountSettingsResponse;
import com.arnzen.home_api_backend.model.reducedData.UpdateTemperatureDisplaySettingResponse;
import com.arnzen.home_api_backend.model.reducedData.UpdateTimeDisplaySettingResponse;
import com.arnzen.home_api_backend.service.AccountSettingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("accountSettings")
public class AccountSettingsController {

    @Autowired
    AccountSettingsService accountSettingsService;

    @GetMapping("getSettings/{userId}")
    public ResponseEntity<GetAccountSettingsResponse> getAccountSettings(@PathVariable int userId) {
        return accountSettingsService.getAccountSettings(userId);
    }

    @PutMapping("timeDisplay")
    public ResponseEntity<UpdateTimeDisplaySettingResponse> updateTimeDisplaySetting(@Valid  @RequestBody UpdateTimeDisplaySettingRequest timeDisplayRequest) {
        return accountSettingsService.updateTimeDisplaySetting(timeDisplayRequest);
    }

    @PutMapping("temperatureDisplay")
    public ResponseEntity<UpdateTemperatureDisplaySettingResponse> updateTemperatureDisplaySetting(@Valid @RequestBody UpdateTemperatureDisplaySettingRequest temperatureDisplayRequest) {
        return accountSettingsService.updateTemperatureDisplaySetting(temperatureDisplayRequest);
    }

    // Exception handling.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleLocalInvalidArgumentsException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // If left generic, could be moved to the global error handler.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResponse> handleLocalInvalidArgumentsException(
            HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new MessageResponse("The format of the request was invalid."), HttpStatus.BAD_REQUEST);
    }
}
