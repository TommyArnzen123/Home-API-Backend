package com.arnzen.home_api_backend.model.reducedData;

import com.arnzen.home_api_backend.model.base.TemperatureThresholdEntity;
import com.arnzen.home_api_backend.model.temperature.TemperatureThreshold;

import java.util.List;

public class GetLocationResponse {
    private int locationId;
    private int homeId;
    private String locationName;
    private int numDevices;
    private Double averageTemperature;
    private TemperatureThreshold threshold;

    public GetLocationResponse() {}

    public GetLocationResponse(int locationId, int homeId, String locationName, int numDevices, Double averageTemperature, TemperatureThreshold threshold) {
        this.locationId = locationId;
        this.homeId = homeId;
        this.locationName = locationName;
        this.numDevices = numDevices;
        this.averageTemperature = averageTemperature;
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

    public int getNumDevices() {
        return numDevices;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(Double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public TemperatureThreshold getThreshold() {
        return threshold;
    }

    public void setThreshold(TemperatureThreshold threshold) {
        this.threshold = threshold;
    }
}
