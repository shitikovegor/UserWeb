package com.shitikov.project.model.entity.type;

public enum OrderStatus {
    ACTIVE("active"),
    IN_PROCESS("in process"),
    COMPLETED("completed"),
    CANCELED("canceled");


    private String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
