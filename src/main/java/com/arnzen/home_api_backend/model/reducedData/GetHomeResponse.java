package com.arnzen.home_api_backend.model.reducedData;

public class GetHomeResponse {

    private int homeId;
    private int userId;
    private String homeName;

    public GetHomeResponse() {}

    public GetHomeResponse(int homeId, int userId, String homeName) {
        this.homeId = homeId;
        this.userId = userId;
        this.homeName = homeName;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
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
