package com.arnzen.home_api_backend.model.base;

import jakarta.persistence.*;

@Entity
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "location_entity_id")
    private LocationEntity locationEntity;

    private String deviceName;

    public DeviceEntity() {
    }

    public DeviceEntity(int id, LocationEntity locationEntity, String deviceName) {
        this.id = id;
        this.locationEntity = locationEntity;
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
}
