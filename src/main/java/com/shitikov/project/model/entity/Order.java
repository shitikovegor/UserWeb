package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.OrderBuilder;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;

import java.util.Comparator;

public class Order extends Entity {
    private long orderId;
    private Application application;
    private long carId;
    private long userId;
    private OrderStatus status;

    public Order(long orderId, Application application, long carId, long userId, OrderStatus status) {
        this.orderId = orderId;
        this.application = application;
        this.carId = carId;
        this.userId = userId;
        this.status = status;
    }

    public Order(OrderBuilder builder) {
        this.orderId = builder.getOrderId();
        this.application = builder.getApplication();
        this.carId = builder.getCarId();
        this.userId = builder.getUserId();
        this.status = builder.getStatus();
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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

        if (orderId != other.orderId) {
            return false;
        }

        if (application != null ? !application.equals(other.application) : other.application != null) {
            return false;
        }

        if (carId != other.carId) {
            return false;
        }

        if (userId != other.userId) {
            return false;
        }
        return status == other.status;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (application != null ? application.hashCode() : 0);
        result = 31 * result + (int) (carId ^ (carId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", application=").append(application);
        sb.append(", carId=").append(carId);
        sb.append(", userId=").append(userId);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    public static class IdComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            long id1 = o1.orderId;
            long id2 = o2.orderId;
            if (id1 == id2) {
                return 0;
            }
            return id1 > id2 ? 1 : -1;
        }
    }

    public static class StatusComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            int result = o1.status.compareTo(o2.status);
            return result;
        }
    }
}
