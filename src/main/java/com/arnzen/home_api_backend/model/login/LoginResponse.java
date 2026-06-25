package com.arnzen.home_api_backend.model.login;

import com.arnzen.home_api_backend.model.reducedData.GetAccountSettingsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private int userId;
    private String username;
    private String firstName;
    private String jwtToken;
    private GetAccountSettingsResponse accountSettings;
}
