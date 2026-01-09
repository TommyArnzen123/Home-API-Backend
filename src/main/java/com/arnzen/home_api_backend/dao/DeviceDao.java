package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.base.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceDao extends JpaRepository<DeviceEntity, Integer> {
    List<DeviceEntity> findByLocationEntityId(int locationId);

    List<DeviceEntity> findAllByLocationEntityId(int locationId);

    @Query("SELECT COUNT(d) FROM DeviceEntity d WHERE d.id = :deviceId")
    int countTotalDevices(int deviceId);

    @Query("SELECT COUNT(t) FROM DeviceEntity d JOIN d.temperatures t WHERE d.id = :deviceId")
    int countTotalTemperatureReadings(int deviceId);
}