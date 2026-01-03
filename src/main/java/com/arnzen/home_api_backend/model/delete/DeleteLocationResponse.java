package com.arnzen.home_api_backend.model.delete;

public class DeleteLocationResponse {
    private int locationId;
    private int numDevices;

    public DeleteLocationResponse() {}

    public DeleteLocationResponse(int locationId, int numDevices) {
        this.locationId = locationId;
        this.numDevices = numDevices;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getNumDevices() {
        return numDevices;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }
}
