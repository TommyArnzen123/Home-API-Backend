package com.arnzen.home_api_backend.model.reducedData;

import com.arnzen.home_api_backend.model.accountSettings.TemperatureDisplayOptions;
import com.arnzen.home_api_backend.model.accountSettings.ThemeDisplayOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateThemeDisplaySettingResponse extends UpdateSettingResponse {

    private ThemeDisplayOptions themeSetting;

    public UpdateThemeDisplaySettingResponse(int userId,
                                                   int settingId,
                                                   ThemeDisplayOptions themeSetting) {
        super(userId, settingId);
        this.themeSetting = themeSetting;
    }
}
