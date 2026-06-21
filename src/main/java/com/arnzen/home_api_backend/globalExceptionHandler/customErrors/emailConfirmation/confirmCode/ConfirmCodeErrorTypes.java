package com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.confirmCode;

public enum ConfirmCodeErrorTypes {
    NOT_ACTIVE,
    ALREADY_CONFIRMED,
    CODE_EXPIRED,
    TOO_MANY_ATTEMPTS,
    CODE_MISMATCH,
    BAD_REQUEST,
}
