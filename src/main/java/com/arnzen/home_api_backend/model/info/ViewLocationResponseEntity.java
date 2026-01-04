package com.arnzen.home_api_backend.model.info;

import com.arnzen.home_api_backend.model.GetDeviceResponse;
import com.arnzen.home_api_backend.model.base.DeviceEntity;

import java.util.List;

public class ViewLocationResponseEntity {

    private int locationId;
    private String locationName;
    private List<GetDeviceResponse> devices;

    public ViewLocationResponseEntity() {}

    public ViewLocationResponseEntity(int locationId, String locationName, List<GetDeviceResponse> devices) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.devices = devices;
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
}
