package com.arnzen.home_api_backend.model;

import java.util.List;

public class GetLocationResponse {
    private int locationId;
    private int homeId;
    private String locationName;
    private List<GetDeviceResponse> devices;

    public GetLocationResponse() {}

    public GetLocationResponse(int locationId, int homeId, String locationName, List<GetDeviceResponse> devices) {
        this.locationId = locationId;
        this.homeId = homeId;
        this.locationName = locationName;
        this.devices = devices;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<GetDeviceResponse> getDevices() {
        return devices;
    }

    public void setDevices(List<GetDeviceResponse> devices) {
        this.devices = devices;
    }
}
