package com.arnzen.home_api_backend.model.base;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class HomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "homeEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocationEntity> locations = new ArrayList<>();

    private String homeName;

    public HomeEntity() {
    }

    public HomeEntity(int id, UserEntity userEntity, List<LocationEntity> locations, String homeName) {
        this.id = id;
        this.userEntity = userEntity;
        this.locations = locations;
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

    public List<LocationEntity> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationEntity> locations) {
        this.locations = locations;
    }
}
