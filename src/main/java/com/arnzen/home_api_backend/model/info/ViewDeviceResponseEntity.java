package com.arnzen.home_api_backend.model.info;

import com.arnzen.home_api_backend.model.entityPath.EntityPathItem;
import com.arnzen.home_api_backend.model.reducedData.GetTemperatureResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ViewDeviceResponseEntity {
    int deviceId;
    int locationId;
    int homeId;
    String deviceName;
    GetTemperatureResponse temperature;
    List<TemperatureHourlyAverage> averageTemperaturesByHourCurrentDay;
    private List<EntityPathItem> entityPath;    // Ordering of entity path matters (User, Home, Location, Device)

    public ViewDeviceResponseEntity() {}

    public ViewDeviceResponseEntity(int deviceId, int locationId, int homeId,
                                    String deviceName, GetTemperatureResponse temperature,
                                    List<TemperatureHourlyAverage> averageTemperaturesByHourCurrentDay,
                                    List<EntityPathItem> entityPath) {
        this.deviceId = deviceId;
        this.locationId = locationId;
        this.homeId = homeId;
        this.deviceName = deviceName;
        this.temperature = temperature;
        this.averageTemperaturesByHourCurrentDay = averageTemperaturesByHourCurrentDay;
        this.entityPath = entityPath;
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

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<TemperatureHourlyAverage> getAverageTemperaturesByHourCurrentDay() {
        return averageTemperaturesByHourCurrentDay;
    }

    public void setAverageTemperaturesByHourCurrentDay(List<TemperatureHourlyAverage> averageTemperaturesByHourCurrentDay) {
        this.averageTemperaturesByHourCurrentDay = averageTemperaturesByHourCurrentDay;
    }

    public List<EntityPathItem> getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(List<EntityPathItem> entityPath) {
        this.entityPath = entityPath;
    }

    public GetTemperatureResponse getTemperature() {
        return temperature;
    }

    public void setTemperature(GetTemperatureResponse temperature) {
        this.temperature = temperature;
    }
}
