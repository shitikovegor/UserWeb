package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.CarBuilder;

/**
 * The type Car.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class Car extends Entity {
    private long carId;
    private String carNumber;
    private double carryingWeight;
    private double carryingVolume;
    private int passengers;
    private boolean removed;

    /**
     * Instantiates a new Car.
     *
     * @param carId          the car id
     * @param carNumber      the car number
     * @param carryingWeight the carrying weight
     * @param carryingVolume the carrying volume
     * @param passengers     the passengers
     * @param removed        the removed
     */
    public Car(long carId, String carNumber, double carryingWeight, double carryingVolume, int passengers) {
        this.carId = carId;
        this.carNumber = carNumber;
        this.carryingWeight = carryingWeight;
        this.carryingVolume = carryingVolume;
        this.passengers = passengers;
        this.removed = false;
    }

    /**
     * Instantiates a new Car.
     *
     * @param builder the builder
     */
    public Car(CarBuilder builder) {
        this.carId = builder.getCarId();
        this.carNumber = builder.getCarNumber();
        this.carryingWeight = builder.getCarryingWeight();
        this.carryingVolume = builder.getCarryingVolume();
        this.passengers = builder.getPassengers();
        this.removed = builder.getRemoved();
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
     * Gets car number.
     *
     * @return the car number
     */
    public String getCarNumber() {
        return carNumber;
    }

    /**
     * Sets car number.
     *
     * @param carNumber the car number
     */
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    /**
     * Gets carrying weight.
     *
     * @return the carrying weight
     */
    public double getCarryingWeight() {
        return carryingWeight;
    }

    /**
     * Sets carrying weight.
     *
     * @param carryingWeight the carrying weight
     */
    public void setCarryingWeight(double carryingWeight) {
        this.carryingWeight = carryingWeight;
    }

    /**
     * Gets carrying volume.
     *
     * @return the carrying volume
     */
    public double getCarryingVolume() {
        return carryingVolume;
    }

    /**
     * Sets carrying volume.
     *
     * @param carryingVolume the carrying volume
     */
    public void setCarryingVolume(double carryingVolume) {
        this.carryingVolume = carryingVolume;
    }

    /**
     * Gets passengers.
     *
     * @return the passengers
     */
    public int getPassengers() {
        return passengers;
    }

    /**
     * Sets passengers.
     *
     * @param passengers the passengers
     */
    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    /**
     * Gets removed.
     *
     * @return the removed
     */
    public boolean getRemoved() {
        return removed;
    }

    /**
     * Sets removed.
     *
     * @param removed the removed
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Car other = (Car) obj;
        if (carId != other.carId) {
            return false;
        }
        if (Double.compare(other.carryingWeight, carryingWeight) != 0) {
            return false;
        }
        if (Double.compare(other.carryingVolume, carryingVolume) != 0) {
            return false;
        }
        if (passengers != other.passengers) {
            return false;
        }
        if (carNumber != null ? !carNumber.equals(other.carNumber) : other.carNumber != null) {
            return false;
        }
        return removed == other.removed;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (carId ^ (carId >>> 32));
        result = 31 * result + (carNumber != null ? carNumber.hashCode() : 0);
        temp = Double.doubleToLongBits(carryingWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carryingVolume);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + passengers;
        result = 31 * result + (removed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("carId=").append(carId);
        sb.append(", carNumber='").append(carNumber).append('\'');
        sb.append(", carryingWeight=").append(carryingWeight);
        sb.append(", carryingVolume=").append(carryingVolume);
        sb.append(", passengers=").append(passengers);
        sb.append(", removed=").append(removed);
        sb.append('}');
        return sb.toString();
    }
}
