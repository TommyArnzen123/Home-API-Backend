package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.LocationEntity;
import com.arnzen.home_api_backend.model.TemperatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TemperatureDao extends JpaRepository<TemperatureEntity, Integer> {
    List<TemperatureEntity> findByDeviceEntityIdAndDateRecorded(int deviceId, LocalDate date);
}
