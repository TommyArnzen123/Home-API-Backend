package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.base.AccountSettingsEntity;
import com.arnzen.home_api_backend.model.base.TemperatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountSettingsDao extends JpaRepository<AccountSettingsEntity, Integer> {
    Optional<AccountSettingsEntity> findByUserEntityId(int userId);
}
