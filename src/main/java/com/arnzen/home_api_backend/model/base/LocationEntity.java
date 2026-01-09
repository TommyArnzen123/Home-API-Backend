package com.arnzen.home_api_backend.model.base;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "home_entity_id")
    private HomeEntity homeEntity;

    @OneToMany(mappedBy = "locationEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeviceEntity> devices = new ArrayList<>();

    private String locationName;

    public LocationEntity() {
    }

    public LocationEntity(int id, HomeEntity homeEntity, List<DeviceEntity> devices, String locationName) {
        this.id = id;
        this.homeEntity = homeEntity;
        this.devices = devices;
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

    public List<DeviceEntity> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceEntity> devices) {
        this.devices = devices;
    }
}
