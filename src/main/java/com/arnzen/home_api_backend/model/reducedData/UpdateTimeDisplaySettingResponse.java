package com.arnzen.home_api_backend.model.reducedData;

import com.arnzen.home_api_backend.model.accountSettings.TimeDisplayOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateTimeDisplaySettingResponse extends UpdateSettingResponse {
    private TimeDisplayOptions timeSetting;

    public UpdateTimeDisplaySettingResponse(int userId,
                                            int settingId,
                                            TimeDisplayOptions timeSetting) {
        super(userId, settingId);
        this.timeSetting = timeSetting;
    }
}
