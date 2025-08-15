package com.arnzen.home_api_backend.model;

import jakarta.persistence.*;

@Entity
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "home_entity_id")
    private HomeEntity homeEntity;

    private String locationName;

    public LocationEntity() {
    }

    public LocationEntity(int id, HomeEntity homeEntity, String locationName) {
        this.id = id;
        this.homeEntity = homeEntity;
        this.locationName = locationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HomeEntity getHomeEntity() {
        return homeEntity;
    }

    public void setHomeEntity(HomeEntity homeEntity) {
        this.homeEntity = homeEntity;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
