package com.arnzen.home_api_backend.model;

import jakarta.persistence.*;

@Entity
public class HomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    private String homeName;

    public HomeEntity() {
    }

    public HomeEntity(int id, UserEntity userEntity, String homeName) {
        this.id = id;
        this.userEntity = userEntity;
        this.homeName = homeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
}
