package com.arnzen.home_api_backend.model;

public class GetTemperatureResponse {

    int temperatureId;
    int deviceId;
    double temperature;

    public GetTemperatureResponse() {}

    public GetTemperatureResponse(int temperatureId, int deviceId, double temperature) {
        this.temperatureId = temperatureId;
        this.deviceId = deviceId;
        this.temperature = temperature;
    }

    public int getTemperatureId() {
        return temperatureId;
    }

    public void setTemperatureId(int temperatureId) {
        this.temperatureId = temperatureId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
