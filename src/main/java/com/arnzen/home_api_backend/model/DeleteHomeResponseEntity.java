package com.arnzen.home_api_backend.model;

public class DeleteHomeResponseEntity {

    private int homeId;
    private int totalLocations;
    private int totalDevices;

    public DeleteHomeResponseEntity() {
    }

    public DeleteHomeResponseEntity(int homeId, int totalLocations, int totalDevices) {
        this.homeId = homeId;
        this.totalLocations = totalLocations;
        this.totalDevices = totalDevices;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getTotalLocations() {
        return totalLocations;
    }

    public void setTotalLocations(int totalLocations) {
        this.totalLocations = totalLocations;
    }

    public int getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(int totalDevices) {
        this.totalDevices = totalDevices;
    }
}
