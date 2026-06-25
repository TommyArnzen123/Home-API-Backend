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
public class UpdateTemperatureDisplaySettingResponse extends UpdateSettingResponse {
    private TemperatureDisplayOptions temperatureSetting;

    public UpdateTemperatureDisplaySettingResponse(int userId,
                                            int settingId,
                                            TemperatureDisplayOptions temperatureSetting) {
        super(userId, settingId);
        this.temperatureSetting = temperatureSetting;
    }
}
