package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.OrderBuilder;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;

import java.util.Comparator;

/**
 * The type Order.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class Order extends Entity {
    private long orderId;
    private Application application;
    private long carId;
    private long userId;
    private OrderStatus status;

    /**
     * Instantiates a new Order.
     *
     * @param orderId     the order id
     * @param application the application
     * @param carId       the car id
     * @param userId      the user id
     * @param status      the status
     */
    public Order(long orderId, Application application, long carId, long userId, OrderStatus status) {
        this.orderId = orderId;
        this.application = application;
        this.carId = carId;
        this.userId = userId;
        this.status = status;
    }

    /**
     * Instantiates a new Order.
     *
     * @param builder the builder
     */
    public Order(OrderBuilder builder) {
        this.orderId = builder.getOrderId();
        this.application = builder.getApplication();
        this.carId = builder.getCarId();
        this.userId = builder.getUserId();
        this.status = builder.getStatus();
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
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
     * Sets application.
     *
     * @param application the application
     */
    public void setApplication(Application application) {
        this.application = application;
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
     * Sets car id.
     *
     * @param carId the car id
     */
    public void setCarId(long carId) {
        this.carId = carId;
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
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
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
     * Sets status.
     *
     * @param status the status
     */
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

    /**
     * The type Status comparator.
     *
     * @author Shitikov Egor
     * @version 1.0
     */
    public static class StatusComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            int result = o1.status.compareTo(o2.status);
            return result;
        }
    }
}
