package com.arnzen.home_api_backend.model.reducedData;

import com.arnzen.home_api_backend.model.base.TemperatureThresholdEntity;

import java.util.List;

public class GetLocationResponse {
    private int locationId;
    private int homeId;
    private String locationName;
    private List<GetDeviceResponse> devices;
    private GetTemperatureThresholdResponse threshold;

    public GetLocationResponse() {}

    public GetLocationResponse(int locationId, int homeId, String locationName, List<GetDeviceResponse> devices, GetTemperatureThresholdResponse threshold) {
        this.locationId = locationId;
        this.homeId = homeId;
        this.locationName = locationName;
        this.devices = devices;
        this.threshold = threshold;
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

    public GetTemperatureThresholdResponse getThreshold() {
        return threshold;
    }

    public void setThreshold(GetTemperatureThresholdResponse threshold) {
        this.threshold = threshold;
    }
}
