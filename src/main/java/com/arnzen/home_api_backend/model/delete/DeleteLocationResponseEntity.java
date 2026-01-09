package com.arnzen.home_api_backend.model.delete;

public class DeleteLocationResponseEntity {
    private int locationId;
    private int numDevices;
    private int homeId;

    public DeleteLocationResponseEntity() {}

    public DeleteLocationResponseEntity(int locationId, int numDevices, int homeId) {
        this.locationId = locationId;
        this.numDevices = numDevices;
        this.homeId = homeId;
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

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }
}
