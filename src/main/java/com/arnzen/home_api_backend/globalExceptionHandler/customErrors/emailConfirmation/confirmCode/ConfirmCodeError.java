package com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.confirmCode;

import com.arnzen.home_api_backend.model.emailConfirmation.ConfirmCodeResponse;
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
public class ConfirmCodeError extends ConfirmCodeResponse {
    private ConfirmCodeErrorTypes errorType;
}
