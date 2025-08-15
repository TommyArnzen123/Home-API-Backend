package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.HomeEntity;
import com.arnzen.home_api_backend.model.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDao extends JpaRepository<LocationEntity, Integer> {
}
