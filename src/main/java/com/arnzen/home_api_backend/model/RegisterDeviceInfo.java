package com.arnzen.home_api_backend.model;

public class RegisterDeviceInfo {

        private int locationId;
        private String deviceName;

        public RegisterDeviceInfo(int locationId, String deviceName) {
            this.locationId = locationId;
            this.deviceName = deviceName;
        }

    public RegisterDeviceInfo() {
    }



    public int getLocationId() {
            return locationId;
        }

        public void setLocationId(int locationId) {
            this.locationId = locationId;
        }

        public String getDeviceName() {

            return deviceName;
        }

        public void setDeviceName(String deviceName) {

            this.deviceName = deviceName;
        }
    }
