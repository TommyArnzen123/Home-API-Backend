package com.arnzen.home_api_backend.model.info;

import com.arnzen.home_api_backend.model.entityPath.EntityPathItem;
import com.arnzen.home_api_backend.model.reducedData.GetHomeResponse;

import java.util.List;

public class HomeScreenInfoResponseEntity {

    private int userId;
    private boolean emailConfirmed;
    private List<GetHomeResponse> homes;
    private List<EntityPathItem> entityPath;    // Ordering of entity path matters (User)

    public HomeScreenInfoResponseEntity() {
    }

    public HomeScreenInfoResponseEntity(int userId, boolean emailConfirmed, List<GetHomeResponse> homes, List<EntityPathItem> entityPath) {
        this.userId = userId;
        this.emailConfirmed = emailConfirmed;
        this.homes = homes;
        this.entityPath = entityPath;
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

    public List<EntityPathItem> getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(List<EntityPathItem> entityPath) {
        this.entityPath = entityPath;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }
}
