package com.arnzen.home_api_backend.model.delete;

public class DeleteDeviceResponseEntity {

    private int deviceId;
    private int locationId;

    public DeleteDeviceResponseEntity(int deviceId, int locationId) {
        this.deviceId = deviceId;
        this.locationId = locationId;
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
}
