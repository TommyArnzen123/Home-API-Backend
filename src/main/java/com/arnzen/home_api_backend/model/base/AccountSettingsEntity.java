package com.arnzen.home_api_backend.model.base;

import com.arnzen.home_api_backend.model.accountSettings.TemperatureDisplayOptions;
import com.arnzen.home_api_backend.model.accountSettings.TimeDisplayOptions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountSettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    // Default to 12-hour time display.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeDisplayOptions timeDisplaySetting = TimeDisplayOptions.HOUR_12;

    // Default to Fahrenheit temperature display.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TemperatureDisplayOptions temperatureDisplaySetting = TemperatureDisplayOptions.FAHRENHEIT;

    public AccountSettingsEntity(UserEntity userEntity, TimeDisplayOptions timeDisplaySetting, TemperatureDisplayOptions temperatureDisplaySetting) {
        this.userEntity = userEntity;
        this.timeDisplaySetting = timeDisplaySetting;
        this.temperatureDisplaySetting = temperatureDisplaySetting;
    }
}
