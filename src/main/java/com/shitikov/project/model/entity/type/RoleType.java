package com.shitikov.project.model.entity.type;

public enum RoleType {
    ADMINISTRATOR("administrator"),
    DRIVER("driver"),
    CLIENT("client"),
    GUEST("guest");

    private String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
