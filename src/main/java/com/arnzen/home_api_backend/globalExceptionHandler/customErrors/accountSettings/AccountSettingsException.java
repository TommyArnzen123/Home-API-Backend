package com.arnzen.home_api_backend.globalExceptionHandler.customErrors.accountSettings;

import lombok.Getter;

@Getter
public class AccountSettingsException extends RuntimeException {

    public AccountSettingsException(String message) {
        super(message);
    }
}
