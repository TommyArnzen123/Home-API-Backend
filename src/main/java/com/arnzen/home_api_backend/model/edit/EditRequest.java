package com.arnzen.home_api_backend.model.edit;

public class EditRequest {

    private int entityId;
    private String name;

    public EditRequest() {}

    public EditRequest(int entityId, String name) {
        this.entityId = entityId;
        this.name = name;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
