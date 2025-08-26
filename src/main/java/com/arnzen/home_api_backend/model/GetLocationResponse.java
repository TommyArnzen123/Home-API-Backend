package com.arnzen.home_api_backend.model;

public class GetLocationResponse {
    private int locationId;
    private int homeId;
    private String locationName;

    public GetLocationResponse() {}

    public GetLocationResponse(int locationId, int homeId, String locationName) {
        this.locationId = locationId;
        this.homeId = homeId;
        this.locationName = locationName;
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
}
