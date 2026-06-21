package com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.generateCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Do not return fields that are set to null.
public class GenerateCodeError {
    private GenerateCodeErrorTypes errorType;
    private Long retryAfterSeconds;
}
