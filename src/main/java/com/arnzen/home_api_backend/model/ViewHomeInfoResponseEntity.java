package com.arnzen.home_api_backend.model;

import java.util.List;

public class ViewHomeInfoResponseEntity {

    private String homeName;
    private List<LocationEntity> locations;
    private int numDevices;

    public ViewHomeInfoResponseEntity() {
    }

    public ViewHomeInfoResponseEntity(String homeName, List<LocationEntity> locations, int numDevices) {
        this.homeName = homeName;
        this.locations = locations;
        this.numDevices = numDevices;
    }

    public List<LocationEntity> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationEntity> locations) {
        this.locations = locations;
    }

    public int getNumDevices() {
        return numDevices;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
}
