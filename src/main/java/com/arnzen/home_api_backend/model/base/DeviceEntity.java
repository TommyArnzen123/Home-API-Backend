package com.arnzen.home_api_backend.model.base;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "location_entity_id")
    private LocationEntity locationEntity;

    @OneToMany(mappedBy = "deviceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemperatureEntity> temperatures = new ArrayList<>();

    private String deviceName;

    public DeviceEntity() {
    }

    public DeviceEntity(int id, LocationEntity locationEntity, List<TemperatureEntity> temperatures, String deviceName) {
        this.id = id;
        this.locationEntity = locationEntity;
        this.temperatures = temperatures;
        this.deviceName = deviceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<TemperatureEntity> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<TemperatureEntity> temperatures) {
        this.temperatures = temperatures;
    }
}
