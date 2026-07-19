package com.arnzen.home_api_backend.model.reducedData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetUserInfoResponse {
    private int userId;
    private String firstName;
}
