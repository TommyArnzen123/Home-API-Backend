package com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.confirmCode;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConfirmCodeException extends RuntimeException {

    private final ConfirmCodeErrorTypes errorType;
    private final Integer confirmationId;
    private final LocalDateTime expiresAt;
    private final Integer attemptsRemaining;

    public ConfirmCodeException(ConfirmCodeErrorTypes errorType) {
        super("There was an error confirming the email address.");
        this.errorType = errorType;
        this.confirmationId = null;
        this.expiresAt = null;
        this.attemptsRemaining = null;
    }

    public ConfirmCodeException(ConfirmCodeErrorTypes errorType,
                                Integer confirmationId,
                                LocalDateTime expiresAt,
                                Integer attemptsRemaining) {
        super("There was an error confirming the email address.");
        this.errorType = errorType;
        this.confirmationId = confirmationId;
        this.expiresAt = expiresAt;
        this.attemptsRemaining = attemptsRemaining;
    }
}
