package com.arnzen.home_api_backend.model.registration;

public class RegisterItem {

    private int parentEntityId;
    private String name;

    public RegisterItem() {}

    public RegisterItem(int parentEntityId, String name) {
        this.parentEntityId = parentEntityId;
        this.name = name;
    }

    public int getParentEntityId() {
        return parentEntityId;
    }

    public void setParentEntityId(int parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
