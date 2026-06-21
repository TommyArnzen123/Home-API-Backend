package com.arnzen.home_api_backend.globalExceptionHandler;

import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.confirmCode.ConfirmCodeError;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.confirmCode.ConfirmCodeErrorTypes;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.confirmCode.ConfirmCodeException;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.generateCode.GenerateCodeError;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.generateCode.GenerateCodeErrorTypes;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.generateCode.GenerateCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenerateCodeException.class)
    public ResponseEntity<GenerateCodeError> handleGenerateEmailConfirmationCodeError(
            GenerateCodeException ex, WebRequest request
    ) {
        GenerateCodeError error = new GenerateCodeError();
        error.setErrorType(ex.getErrorType());
        error.setRetryAfterSeconds(ex.getRetryAfterSeconds());

        // Set the status type of the error response.
        HttpStatus status = ex.getErrorType() ==
                GenerateCodeErrorTypes.TOO_MANY_REQUESTS
                ? HttpStatus.TOO_MANY_REQUESTS
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ConfirmCodeException.class)
    public ResponseEntity<ConfirmCodeError> handleConfirmEmailError(
            ConfirmCodeException ex, WebRequest request
    ) {
        ConfirmCodeError error = new ConfirmCodeError();
        ConfirmCodeErrorTypes errorType = ex.getErrorType();
        error.setErrorType(errorType);
        error.setConfirmationId(ex.getConfirmationId());
        error.setExpiresAt(ex.getExpiresAt());
        error.setAttemptsRemaining(ex.getAttemptsRemaining());

        // Set the status type of the error message.
        HttpStatus status =
                errorType == ConfirmCodeErrorTypes.TOO_MANY_ATTEMPTS
                        ? HttpStatus.TOO_MANY_REQUESTS
                        : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(error, status);
    }
}
