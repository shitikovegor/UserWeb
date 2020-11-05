package com.shitikov.project.model.entity.type;

public enum OrderStatus {
    ACTIVE("active"),
    CONFIRMED("confirmed"),
    COMPLETED("completed"),
    CANCELED("canceled");


    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
