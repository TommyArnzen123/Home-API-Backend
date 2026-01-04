package com.arnzen.home_api_backend.model.temperature;

public class SaveTemperatureInfo {
    private int deviceId;
    private double temperature;

    public SaveTemperatureInfo() {}

    public SaveTemperatureInfo(int deviceId, double temperature) {
        this.deviceId = deviceId;
        this.temperature = temperature;
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
