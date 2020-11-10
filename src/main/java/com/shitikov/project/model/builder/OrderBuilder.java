package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;

/**
 * The type Order builder.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class OrderBuilder {
    private long orderId;
    private Application application;
    private long carId;
    private long userId;
    private OrderStatus status;

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Build order id order builder.
     *
     * @param orderId the order id
     * @return the order builder
     */
    public OrderBuilder buildOrderId(long orderId) {
        this.orderId = orderId;
        return this;
    }

    /**
     * Gets application.
     *
     * @return the application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Build application order builder.
     *
     * @param application the application
     * @return the order builder
     */
    public OrderBuilder buildApplication(Application application) {
        this.application = application;
        return this;
    }

    /**
     * Gets car id.
     *
     * @return the car id
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Build car id order builder.
     *
     * @param carId the car id
     * @return the order builder
     */
    public OrderBuilder buildCarId(long carId) {
        this.carId = carId;
        return this;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Build user id order builder.
     *
     * @param userId the user id
     * @return the order builder
     */
    public OrderBuilder buildUserId(long userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Build status order builder.
     *
     * @param status the status
     * @return the order builder
     */
    public OrderBuilder buildStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Build order order.
     *
     * @return the order
     */
    public Order buildOrder() {
        return new Order(this);
    }
}
