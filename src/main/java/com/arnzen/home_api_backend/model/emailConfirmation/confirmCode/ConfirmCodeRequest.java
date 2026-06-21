package com.arnzen.home_api_backend.model.emailConfirmation.confirmCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmCodeRequest {
    private int confirmationId;
    private int userId;
    private int emailConfirmationCode;
}
