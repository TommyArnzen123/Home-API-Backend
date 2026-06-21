package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.base.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsernameIgnoreCase(String username);
    public boolean existsByUsernameIgnoreCase(String username);
    public boolean existsByEmailIgnoreCase(String email);
}
