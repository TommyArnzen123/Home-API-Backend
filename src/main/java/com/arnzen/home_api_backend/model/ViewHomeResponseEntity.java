package com.arnzen.home_api_backend.model;

import java.util.List;

public class ViewHomeResponseEntity {

    private int homeId;
    private String homeName;
    private List<GetLocationResponse> locations;
    private int numDevices;

    public ViewHomeResponseEntity() {
    }

    public ViewHomeResponseEntity(int homeId, String homeName, List<GetLocationResponse> locations, int numDevices) {
        this.homeId = homeId;
        this.homeName = homeName;
        this.locations = locations;
        this.numDevices = numDevices;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public List<GetLocationResponse> getLocations() {
        return locations;
    }

    public void setLocations(List<GetLocationResponse> locations) {
        this.locations = locations;
    }

    public int getNumDevices() {
        return numDevices;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }
}
