package com.arnzen.home_api_backend.model.delete;

public class DeleteHomeResponse {
    private int homeId;
    private int numLocations;
    private int numDevices;

    public DeleteHomeResponse() {}

    public DeleteHomeResponse(int homeId, int numLocations, int numDevices) {
        this.homeId = homeId;
        this.numLocations = numLocations;
        this.numDevices = numDevices;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getNumLocations() {
        return numLocations;
    }

    public void setNumLocations(int numLocations) {
        this.numLocations = numLocations;
    }

    public int getNumDevices() {
        return numDevices;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }
}
