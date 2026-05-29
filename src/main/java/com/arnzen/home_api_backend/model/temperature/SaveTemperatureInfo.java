package com.arnzen.home_api_backend.model.temperature;

public class SaveTemperatureInfo {
    private Integer deviceId;
    private Double temperature;

    public SaveTemperatureInfo() {}

    public SaveTemperatureInfo(Integer deviceId, Double temperature) {
        this.deviceId = deviceId;
        this.temperature = temperature;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
