package com.arnzen.home_api_backend.model;

public class RegisterHomeInfo {

    private int userId;
    private String homeName;

    public RegisterHomeInfo(int userId, String homeName) {
        this.userId = userId;
        this.homeName = homeName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
}
