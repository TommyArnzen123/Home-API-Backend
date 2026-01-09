package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.base.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeDao extends JpaRepository<HomeEntity, Integer> {
    List<HomeEntity> findByUserEntityId(int userId);

    List<HomeEntity> findAllByUserEntityId(int userId);

    @Query("SELECT COUNT(h) FROM HomeEntity h WHERE h.id = :homeId")
    int countTotalHomes(int homeId);

    @Query("SELECT COUNT(l) FROM HomeEntity h JOIN h.locations l WHERE h.id = :homeId")
    int countTotalLocations(int homeId);

    @Query("SELECT COUNT(d) FROM HomeEntity h JOIN h.locations l JOIN l.devices d WHERE h.id = :homeId")
    int countTotalDevices(int homeId);

    @Query("SELECT COUNT(t) FROM HomeEntity h JOIN h.locations l JOIN l.devices d JOIN d.temperatures t WHERE h.id = :homeId")
    int countTotalTemperatureReadings(int homeId);
}
