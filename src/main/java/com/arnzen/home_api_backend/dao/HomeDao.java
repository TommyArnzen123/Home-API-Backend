package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.HomeEntity;
import com.arnzen.home_api_backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeDao extends JpaRepository<HomeEntity, Integer> {
    List<HomeEntity> findByUserEntityId(int userId);

    List<HomeEntity> findAllByUserEntityId(int userId);
}
