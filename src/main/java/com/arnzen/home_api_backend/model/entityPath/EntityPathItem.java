package com.arnzen.home_api_backend.model.entityPath;

public class EntityPathItem {
    private int id;
    private EntityType type;

    public EntityPathItem() {}

    public EntityPathItem(int id, EntityType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }
}
