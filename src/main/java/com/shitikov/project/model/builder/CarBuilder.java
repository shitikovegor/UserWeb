package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Car;

/**
 * The type Car builder.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class CarBuilder {
    private long carId;
    private String carNumber;
    private double carryingWeight;
    private double carryingVolume;
    private int passengers;
    private boolean removed;

    /**
     * Gets car id.
     *
     * @return the car id
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Build car id car builder.
     *
     * @param carId the car id
     * @return the car builder
     */
    public CarBuilder buildCarId(long carId) {
        this.carId = carId;
        return this;
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
     * Build car number car builder.
     *
     * @param carNumber the car number
     * @return the car builder
     */
    public CarBuilder buildCarNumber(String carNumber) {
        this.carNumber = carNumber;
        return this;
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
     * Build carrying weight car builder.
     *
     * @param carryingWeight the carrying weight
     * @return the car builder
     */
    public CarBuilder buildCarryingWeight(double carryingWeight) {
        this.carryingWeight = carryingWeight;
        return this;
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
     * Build carrying volume car builder.
     *
     * @param carryingVolume the carrying volume
     * @return the car builder
     */
    public CarBuilder buildCarryingVolume(double carryingVolume) {
        this.carryingVolume = carryingVolume;
        return this;
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
     * Build passengers car builder.
     *
     * @param passengers the passengers
     * @return the car builder
     */
    public CarBuilder buildPassengers(int passengers) {
        this.passengers = passengers;
        return this;
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
     * Build removed car builder.
     *
     * @param removed the removed
     * @return the car builder
     */
    public CarBuilder buildRemoved(boolean removed) {
        this.removed = removed;
        return this;
    }

    /**
     * Build car car.
     *
     * @return the car
     */
    public Car buildCar() {
        return new Car(this);
    }
}
