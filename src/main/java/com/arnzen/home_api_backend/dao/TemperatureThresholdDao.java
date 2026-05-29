package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.base.TemperatureThresholdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TemperatureThresholdDao extends JpaRepository<TemperatureThresholdEntity, Integer>  {}
