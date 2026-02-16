package com.arnzen.home_api_backend.model.info;

import com.arnzen.home_api_backend.model.reducedData.GetHomeResponse;

import java.util.List;

public class HomeScreenInfoResponseEntity {

    private int userId;
    private List<GetHomeResponse> homes;

    public HomeScreenInfoResponseEntity() {
    }

    public HomeScreenInfoResponseEntity(int userId, List<GetHomeResponse> homes) {
        this.userId = userId;
        this.homes = homes;
    }

    public List<GetHomeResponse> getHomes() {
        return homes;
    }

    public void setHomes(List<GetHomeResponse> homes) {
        this.homes = homes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
