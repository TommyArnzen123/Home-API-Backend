package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDao extends JpaRepository<LocationEntity, Integer> {
    List<LocationEntity> findByHomeEntityId(int homeId);

    List<LocationEntity> findAllByHomeEntityId(int homeId);
}
