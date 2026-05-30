package com.arnzen.home_api_backend.model.info;

import com.arnzen.home_api_backend.model.entityPath.EntityPathItem;
import com.arnzen.home_api_backend.model.reducedData.GetLocationResponse;

import java.util.List;

public class ViewHomeResponseEntity {

    private int homeId;
    private String homeName;
    private List<GetLocationResponse> locations;
    private List<EntityPathItem> entityPath; // Ordering of entity path matters (User, Home)

    public ViewHomeResponseEntity() {
    }

    public ViewHomeResponseEntity(int homeId, String homeName, List<GetLocationResponse> locations, List<EntityPathItem> entityPath) {
        this.homeId = homeId;
        this.homeName = homeName;
        this.locations = locations;
        this.entityPath = entityPath;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public List<GetLocationResponse> getLocations() {
        return locations;
    }

    public void setLocations(List<GetLocationResponse> locations) {
        this.locations = locations;
    }

    public List<EntityPathItem> getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(List<EntityPathItem> entityPath) {
        this.entityPath = entityPath;
    }
}
