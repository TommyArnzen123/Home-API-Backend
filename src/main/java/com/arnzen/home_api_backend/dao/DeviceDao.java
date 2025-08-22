package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.DeviceEntity;
import com.arnzen.home_api_backend.model.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceDao extends JpaRepository<DeviceEntity, Integer> {
    List<DeviceEntity> findByLocationEntityId(int locationId);
}