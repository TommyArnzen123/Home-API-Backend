package com.arnzen.home_api_backend.model.reducedData;

import com.arnzen.home_api_backend.model.accountSettings.TemperatureDisplayOptions;
import com.arnzen.home_api_backend.model.accountSettings.TimeDisplayOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetAccountSettingsResponse {
    private Integer accountSettingId;
    private Integer userId;
    private TimeDisplayOptions timeDisplaySetting;
    private TemperatureDisplayOptions temperatureDisplaySetting;
}
