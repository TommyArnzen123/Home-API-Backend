package com.arnzen.home_api_backend.model.login;

import com.arnzen.home_api_backend.model.reducedData.GetAccountSettingsResponse;
import com.arnzen.home_api_backend.model.reducedData.GetUserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private GetUserInfoResponse userInfo;
    private String jwtToken;
    private GetAccountSettingsResponse accountSettings;
}
