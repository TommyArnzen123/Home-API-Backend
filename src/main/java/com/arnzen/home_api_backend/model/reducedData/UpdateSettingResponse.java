package com.arnzen.home_api_backend.model.reducedData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateSettingResponse {
    private int userId;
    private int settingId;
}
