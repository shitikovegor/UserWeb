package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;

public class OrderBuilder {
    private Application application;
    private Car car;
    private OrderStatus status;
    private long arrivalDate;

    public Application getApplication() {
        return application;
    }

    public OrderBuilder buildApplication(Application application) {
        this.application = application;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public OrderBuilder buildCar(Car car) {
        this.car = car;
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
