package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.OrderBuilder;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;

public class Order {
    private Application application;
    private Car car;
    private OrderStatus status;
    private long arrivalDate;

    public Order(Application application, Car car, OrderStatus status, long arrivalDate) {
        this.application = application;
        this.car = car;
        this.status = status;
        this.arrivalDate = arrivalDate;
    }

    public Order(OrderBuilder builder) {
        this.application = builder.getApplication();
        this.car = builder.getCar();
        this.status = builder.getStatus();
        this.arrivalDate = builder.getArrivalDate();
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public long getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Order other = (Order) obj;

        if (arrivalDate != other.arrivalDate) {
            return false;
        }
        if (application != null ? !application.equals(other.application) : other.application != null) {
            return false;
        }
        if (car != null ? !car.equals(other.car) : other.car != null) {
            return false;
        }
        return status == other.status;
    }

    @Override
    public int hashCode() {
        int result = application != null ? application.hashCode() : 0;
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (arrivalDate ^ (arrivalDate >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("application=").append(application);
        sb.append(", car=").append(car);
        sb.append(", status=").append(status);
        sb.append(", arrivalDate=").append(arrivalDate);
        sb.append('}');
        return sb.toString();
    }
}
