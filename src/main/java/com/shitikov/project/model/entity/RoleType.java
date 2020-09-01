package com.shitikov.project.model.entity;

public enum RoleType {
    ADMINISTRATOR("administrator"),
    USER("user");

    private String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
