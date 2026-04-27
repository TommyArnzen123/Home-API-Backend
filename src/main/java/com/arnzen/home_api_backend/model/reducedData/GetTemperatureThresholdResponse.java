package com.arnzen.home_api_backend.model.reducedData;

public class GetTemperatureThresholdResponse {

    private Double minimumTemperature;
    private Double maximumTemperature;

    public GetTemperatureThresholdResponse() {
    }

    public GetTemperatureThresholdResponse(Double minimumTemperature, Double maximumTemperature) {
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
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
}
