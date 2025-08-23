package com.arnzen.home_api_backend.model;

public class GetDeviceResponse {

    private int deviceId;
    private int locationId;
    private String deviceName;

    public GetDeviceResponse() {}

    public GetDeviceResponse(int deviceId, int locationId, String deviceName) {
        this.deviceId = deviceId;
        this.locationId = locationId;
        this.deviceName = deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
