package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<UserEntity, Integer> {

    public List<UserEntity> findByUsername(String username);
}
