package com.arnzen.home_api_backend.model.temperature;

public class TemperatureThresholdRequest {

    private Integer id;
    private Double minimumTemperature;
    private Double maximumTemperature;
    private Integer locationId;

    public TemperatureThresholdRequest() {}

    public TemperatureThresholdRequest(Integer id, Double minimumTemperature, Double maximumTemperature, Integer locationId) {
        this.id = id;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
        this.locationId = locationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
}
