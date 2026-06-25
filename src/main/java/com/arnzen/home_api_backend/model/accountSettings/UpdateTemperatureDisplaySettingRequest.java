package com.arnzen.home_api_backend.model.accountSettings;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateTemperatureDisplaySettingRequest {

    @NotNull(message = "UserId is required.")
    private Integer userId;

    @NotNull(message = "Temperature Display Setting is required.")
    private TemperatureDisplayOptions setting;
}
