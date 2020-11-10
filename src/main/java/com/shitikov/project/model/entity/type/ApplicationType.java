package com.shitikov.project.model.entity.type;

/**
 * The enum Application type.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public enum ApplicationType {
    /**
     * Cargo application type.
     */
    CARGO("cargo"),
    /**
     * Passenger application type.
     */
    PASSENGER("passenger");

    private final String typeName;

    ApplicationType(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return typeName;
    }
}
