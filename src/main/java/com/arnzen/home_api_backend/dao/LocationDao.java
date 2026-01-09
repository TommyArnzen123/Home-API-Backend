package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.base.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationDao extends JpaRepository<LocationEntity, Integer> {
    List<LocationEntity> findByHomeEntityId(int homeId);

    List<LocationEntity> findAllByHomeEntityId(int homeId);

    @Query("SELECT COUNT(l) FROM LocationEntity l WHERE l.id = :locationId")
    int countTotalLocations(int locationId);

    @Query("SELECT COUNT(d) FROM LocationEntity l JOIN l.devices d WHERE l.id = :locationId")
    int countTotalDevices(int locationId);

    @Query("SELECT COUNT(t) FROM LocationEntity l JOIN l.devices d JOIN d.temperatures t WHERE l.id = :locationId")
    int countTotalTemperatureReadings(int locationId);
}
