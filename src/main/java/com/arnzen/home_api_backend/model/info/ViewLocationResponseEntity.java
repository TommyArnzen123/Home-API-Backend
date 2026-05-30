package com.arnzen.home_api_backend.model.info;

import com.arnzen.home_api_backend.model.entityPath.EntityPathItem;
import com.arnzen.home_api_backend.model.reducedData.GetDeviceResponse;
import com.arnzen.home_api_backend.model.temperature.TemperatureThreshold;

import java.util.List;

public class ViewLocationResponseEntity {

    private int locationId;
    private int homeId;
    private String locationName;
    private List<GetDeviceResponse> devices;
    private TemperatureThreshold threshold;
    private List<EntityPathItem> entityPath; // Ordering of entity path matters (User, Home, Location)

    public ViewLocationResponseEntity() {
    }

    public ViewLocationResponseEntity(int locationId, int homeId, String locationName, List<GetDeviceResponse> devices, TemperatureThreshold threshold, List<EntityPathItem> entityPath) {
        this.locationId = locationId;
        this.homeId = homeId;
        this.locationName = locationName;
        this.devices = devices;
        this.threshold = threshold;
        this.entityPath = entityPath;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public List<GetDeviceResponse> getDevices() {
        return devices;
    }

    public void setDevices(List<GetDeviceResponse> devices) {
        this.devices = devices;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public TemperatureThreshold getThreshold() {
        return threshold;
    }

    public void setThreshold(TemperatureThreshold threshold) {
        this.threshold = threshold;
    }

    public List<EntityPathItem> getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(List<EntityPathItem> entityPath) {
        this.entityPath = entityPath;
    }
}
