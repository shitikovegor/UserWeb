package com.shitikov.project.model.entity.type;

/**
 * The enum Role type.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public enum RoleType {
    /**
     * Administrator role type.
     */
    ADMINISTRATOR("administrator"),
    /**
     * Driver role type.
     */
    DRIVER("driver"),
    /**
     * Client role type.
     */
    CLIENT("client"),
    /**
     * Guest role type.
     */
    GUEST("guest");

    private final String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return roleName;
    }
}
