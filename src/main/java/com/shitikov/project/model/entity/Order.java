package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.OrderBuilder;
import com.shitikov.project.model.entity.type.OrderStatus;

public class Order extends Entity {
    private long applicationId;
    private long carId;
    private OrderStatus status;
    private long arrivalDate;

    public Order(long applicationId, long carId, OrderStatus status, long arrivalDate) {
        this.applicationId = applicationId;
        this.carId = carId;
        this.status = status;
        this.arrivalDate = arrivalDate;
    }

    public Order(OrderBuilder builder) {
        this.applicationId = builder.getApplicationId();
        this.carId = builder.getCarId();
        this.status = builder.getStatus();
        this.arrivalDate = builder.getArrivalDate();
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
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
        if (applicationId != other.applicationId) {
            return false;
        }
        if (carId != other.carId) {
            return false;
        }
        return status == other.status;
    }

    @Override
    public int hashCode() {
        int result = (int) (applicationId ^ (applicationId >>> 32));
        result = 31 * result + (int) (carId ^ (carId >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (arrivalDate ^ (arrivalDate >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("applicationId=").append(applicationId);
        sb.append(", carId=").append(carId);
        sb.append(", status=").append(status);
        sb.append(", arrivalDate=").append(arrivalDate);
        sb.append('}');
        return sb.toString();
    }
}
