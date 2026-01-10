package com.arnzen.home_api_backend.model.info;

import com.arnzen.home_api_backend.model.TemperatureHourlyAverage;

import java.time.LocalDateTime;
import java.util.List;

public class ViewDeviceResponseEntity {
    int deviceId;
    int locationId;
    String deviceName;
    double mostRecentTemperature;
    LocalDateTime mostRecentTemperatureAvailableDateTime;
    boolean mostRecentTemperatureAvailable;
    List<TemperatureHourlyAverage> averageTemperaturesByHourCurrentDay;

    public ViewDeviceResponseEntity() {}

    public ViewDeviceResponseEntity(int deviceId, int locationId, String deviceName,
                                    double mostRecentTemperature, LocalDateTime mostRecentTemperatureAvailableDateTime,
                                    boolean mostRecentTemperatureAvailable, List<TemperatureHourlyAverage> averageTemperaturesByHourCurrentDay) {
        this.deviceId = deviceId;
        this.locationId = locationId;
        this.deviceName = deviceName;
        this.mostRecentTemperature = mostRecentTemperature;
        this.mostRecentTemperatureAvailableDateTime = mostRecentTemperatureAvailableDateTime;
        this.mostRecentTemperatureAvailable = mostRecentTemperatureAvailable;
        this.averageTemperaturesByHourCurrentDay = averageTemperaturesByHourCurrentDay;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
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

    public double getMostRecentTemperature() {
        return mostRecentTemperature;
    }

    public void setMostRecentTemperature(double mostRecentTemperature) {
        this.mostRecentTemperature = mostRecentTemperature;
    }

    public List<TemperatureHourlyAverage> getAverageTemperaturesByHourCurrentDay() {
        return averageTemperaturesByHourCurrentDay;
    }

    public void setAverageTemperaturesByHourCurrentDay(List<TemperatureHourlyAverage> averageTemperaturesByHourCurrentDay) {
        this.averageTemperaturesByHourCurrentDay = averageTemperaturesByHourCurrentDay;
    }

    public boolean isMostRecentTemperatureAvailable() {
        return mostRecentTemperatureAvailable;
    }

    public void setMostRecentTemperatureAvailable(boolean mostRecentTemperatureAvailable) {
        this.mostRecentTemperatureAvailable = mostRecentTemperatureAvailable;
    }

    public LocalDateTime getMostRecentTemperatureAvailableDateTime() {
        return mostRecentTemperatureAvailableDateTime;
    }

    public void setMostRecentTemperatureAvailableDateTime(LocalDateTime mostRecentTemperatureAvailableDateTime) {
        this.mostRecentTemperatureAvailableDateTime = mostRecentTemperatureAvailableDateTime;
    }
}
