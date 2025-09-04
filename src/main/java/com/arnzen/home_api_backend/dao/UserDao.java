package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsernameIgnoreCase(String username);
}
