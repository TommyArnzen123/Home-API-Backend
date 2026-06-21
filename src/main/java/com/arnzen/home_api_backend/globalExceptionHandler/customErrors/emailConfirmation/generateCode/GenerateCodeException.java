package com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.generateCode;

import lombok.Getter;

@Getter
public class GenerateCodeException extends RuntimeException{

    private final GenerateCodeErrorTypes errorType;
    private final Long retryAfterSeconds;

    public GenerateCodeException(GenerateCodeErrorTypes errorType) {
        super("There was an error generating the email confirmation code.");
        this.errorType = errorType;
        this.retryAfterSeconds = null;
    }

    public GenerateCodeException(GenerateCodeErrorTypes errorType, Long retryAfterSeconds) {
        super("There was an error generating the email confirmation code.");
        this.errorType = errorType;
        this.retryAfterSeconds = retryAfterSeconds;
    }
}
