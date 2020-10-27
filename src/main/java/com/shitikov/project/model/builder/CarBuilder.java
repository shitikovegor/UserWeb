package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;

public class CarBuilder {
    private long carId;
    private String carNumber;
    private double carryingWeight;
    private double carryingVolume;
    private int passengers;
    private User owner;

    public long getCarId() {
        return carId;
    }

    public CarBuilder buildCarId(long carId) {
        this.carId = carId;
        return this;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public CarBuilder buildCarNumber(String carNumber) {
        this.carNumber = carNumber;
        return this;
    }

    public double getCarryingWeight() {
        return carryingWeight;
    }

    public CarBuilder buildCarryingWeight(double carryingWeight) {
        this.carryingWeight = carryingWeight;
        return this;
    }

    public double getCarryingVolume() {
        return carryingVolume;
    }

    public CarBuilder buildCarryingVolume(double carryingVolume) {
        this.carryingVolume = carryingVolume;
        return this;
    }

    public int getPassengers() {
        return passengers;
    }

    public CarBuilder buildPassengers(int passengers) {
        this.passengers = passengers;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public CarBuilder buildOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public Car buildCar() {
        return new Car(this);
    }
}
