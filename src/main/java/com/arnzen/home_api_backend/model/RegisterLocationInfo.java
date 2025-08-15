package com.arnzen.home_api_backend.model;

public class RegisterLocationInfo {

    private int homeId;
    private String locationName;

    public RegisterLocationInfo(int homeId, String locationName) {
        this.homeId = homeId;
        this.locationName = locationName;
    }

    public RegisterLocationInfo() {
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
