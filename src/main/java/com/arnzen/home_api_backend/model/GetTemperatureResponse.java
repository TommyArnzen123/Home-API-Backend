package com.arnzen.home_api_backend.model;

import java.time.LocalDateTime;

public class GetTemperatureResponse {

    private int temperatureId;

    // Temperature in fahrenheit.
    private double temperature;

    private LocalDateTime dateRecorded;

    public GetTemperatureResponse() {}

    public GetTemperatureResponse(int temperatureId, double temperature, LocalDateTime dateRecorded) {
        this.temperatureId = temperatureId;
        this.temperature = temperature;
        this.dateRecorded = dateRecorded;
    }

    public int getTemperatureId() {
        return temperatureId;
    }

    public void setTemperatureId(int temperatureId) {
        this.temperatureId = temperatureId;
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
