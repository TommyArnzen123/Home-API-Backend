package com.arnzen.home_api_backend.model.info;

public class TemperatureHourlyAverage {
    private int hour;
    private double averageTemperature;

    public TemperatureHourlyAverage() {}

    public TemperatureHourlyAverage(int hour, double averageTemperature) {
        this.hour = hour;
        this.averageTemperature = averageTemperature;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }
}
