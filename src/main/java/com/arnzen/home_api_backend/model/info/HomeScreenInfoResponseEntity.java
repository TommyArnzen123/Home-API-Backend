package com.arnzen.home_api_backend.model.info;

import com.arnzen.home_api_backend.model.reducedData.GetHomeResponse;

import java.util.List;

public class HomeScreenInfoResponseEntity {

    private List<GetHomeResponse> homes;
    private int numLocations;
    private int numDevices;

    public HomeScreenInfoResponseEntity() {
    }

    public HomeScreenInfoResponseEntity(List<GetHomeResponse> homes, int numLocations, int numDevices) {
        this.homes = homes;
        this.numLocations = numLocations;
        this.numDevices = numDevices;
    }

    public List<GetHomeResponse> getHomes() {
        return homes;
    }

    public void setHomes(List<GetHomeResponse> homes) {
        this.homes = homes;
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
