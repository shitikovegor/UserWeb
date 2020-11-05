package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;

public class OrderBuilder {
    private long orderId;
    private Application application;
    private long carId;
    private long userId;
    private OrderStatus status;

    public long getOrderId() {
        return orderId;
    }

    public OrderBuilder buildOrderId(long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Application getApplication() {
        return application;
    }

    public OrderBuilder buildApplication(Application application) {
        this.application = application;
        return this;
    }

    public long getCarId() {
        return carId;
    }

    public OrderBuilder buildCarId(long carId) {
        this.carId = carId;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public OrderBuilder buildUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderBuilder buildStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Order buildOrder() {
        return new Order(this);
    }
}
