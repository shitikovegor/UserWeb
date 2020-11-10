package com.shitikov.project.model.entity.type;

/**
 * The enum Order status.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public enum OrderStatus {
    /**
     * Active order status.
     */
    ACTIVE("active"),
    /**
     * Confirmed order status.
     */
    CONFIRMED("confirmed"),
    /**
     * Completed order status.
     */
    COMPLETED("completed"),
    /**
     * Canceled order status.
     */
    CANCELED("canceled");


    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
