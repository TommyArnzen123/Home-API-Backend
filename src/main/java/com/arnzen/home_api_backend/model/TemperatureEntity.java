package com.arnzen.home_api_backend.model;

import com.arnzen.home_api_backend.model.base.DeviceEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TemperatureEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "device_entity_id")
    private DeviceEntity deviceEntity;

    // Temperature in fahrenheit.
    private double temperature;

    private LocalDateTime dateRecorded;

    public TemperatureEntity() {}

    public TemperatureEntity(int id, DeviceEntity deviceEntity, double temperature, LocalDateTime dateRecorded) {
        this.id = id;
        this.deviceEntity = deviceEntity;
        this.temperature = temperature;
        this.dateRecorded = dateRecorded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeviceEntity getDeviceEntity() {
        return deviceEntity;
    }

    public void setDeviceEntity(DeviceEntity deviceEntity) {
        this.deviceEntity = deviceEntity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(LocalDateTime dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
}
