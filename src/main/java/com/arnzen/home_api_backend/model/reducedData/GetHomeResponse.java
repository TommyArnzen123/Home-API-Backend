package com.arnzen.home_api_backend.model.reducedData;

public class GetHomeResponse {

    private int homeId;
    private int userId;
    private String homeName;
    private int totalLocations;
    private int totalDevices;

    public GetHomeResponse() {}

    public GetHomeResponse(int homeId, int userId, String homeName, int totalLocations, int totalDevices) {
        this.homeId = homeId;
        this.userId = userId;
        this.homeName = homeName;
        this.totalLocations = totalLocations;
        this.totalDevices = totalDevices;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
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
