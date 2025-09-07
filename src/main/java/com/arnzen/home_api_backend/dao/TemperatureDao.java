package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.TemperatureEntity;
import com.arnzen.home_api_backend.model.TemperatureHourlyAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TemperatureDao extends JpaRepository<TemperatureEntity, Integer> {

    @Query("SELECT new com.arnzen.home_api_backend.model.TemperatureHourlyAverage(HOUR(m.dateRecorded), AVG(m.temperature)) " +
            "FROM TemperatureEntity m WHERE DATE(m.dateRecorded) = DATE(:dateTime) AND m.deviceEntity.id = :deviceId GROUP BY HOUR(m.dateRecorded) ORDER BY HOUR(m.dateRecorded)")
    List<TemperatureHourlyAverage> findAverageTemperatureByDateAndHour(@Param("deviceId") int deviceId, @Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT temp from TemperatureEntity temp WHERE temp.deviceEntity.id = :deviceId ORDER BY temp.id DESC LIMIT 1")
    TemperatureEntity getMostRecentTemperatureByDeviceIdAndDate(@Param("dateTime") LocalDateTime dateTime, @Param("deviceId") int deviceId);

    void deleteAllByDeviceEntityId(int deviceEntityId);
}
