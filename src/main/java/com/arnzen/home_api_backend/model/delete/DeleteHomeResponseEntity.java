package com.arnzen.home_api_backend.model.delete;

public class DeleteHomeResponseEntity {
    private int homeId;
    private int numLocations;
    private int numDevices;
    private int userId;

    public DeleteHomeResponseEntity() {}

    public DeleteHomeResponseEntity(int homeId, int numLocations, int numDevices, int userId) {
        this.homeId = homeId;
        this.numLocations = numLocations;
        this.numDevices = numDevices;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
