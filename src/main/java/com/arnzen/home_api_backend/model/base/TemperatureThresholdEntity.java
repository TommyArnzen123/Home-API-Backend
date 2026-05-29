package com.arnzen.home_api_backend.model.base;

import jakarta.persistence.*;

@Entity
public class TemperatureThresholdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true)
    private Double minimumTemperature;

    @Column(nullable = true)
    private Double maximumTemperature;

    @OneToOne()
    @JoinColumn(name = "location_entity_id")
    private LocationEntity locationEntity;

    public TemperatureThresholdEntity() {
    }

    public TemperatureThresholdEntity(int id, Double minimumTemperature, Double maximumTemperature, LocationEntity locationEntity) {
        this.id = id;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
        this.locationEntity = locationEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(Double minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public Double getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(Double maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }
}
