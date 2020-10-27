package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.type.OrderStatus;

public class OrderBuilder {
    private long applicationId;
    private long carId;
    private OrderStatus status;
    private long arrivalDate;

    public long getApplicationId() {
        return applicationId;
    }

    public OrderBuilder buildApplicationId(long application) {
        this.applicationId = application;
        return this;
    }

    public long getCarId() {
        return carId;
    }

    public OrderBuilder buildCarId(long carId) {
        this.carId = carId;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderBuilder buildStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public long getArrivalDate() {
        return arrivalDate;
    }

    public OrderBuilder buildArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public Order buildOrder() {
        return new Order(this);
    }
}
