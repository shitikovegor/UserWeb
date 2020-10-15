package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.CarBuilder;

public class Car extends Entity {
    private String carNumber;
    private double carryingWeight;
    private double carryingVolume;
    private int passengers;
    private User owner;

    public Car(String carNumber, double carryingWeight, double carryingVolume, int passengers, User owner) {
        this.carNumber = carNumber;
        this.carryingWeight = carryingWeight;
        this.carryingVolume = carryingVolume;
        this.passengers = passengers;
        this.owner = owner;
    }

    public Car(CarBuilder builder) {
        this.carNumber = builder.getCarNumber();
        this.carryingWeight = builder.getCarryingWeight();
        this.carryingVolume = builder.getCarryingVolume();
        this.passengers = builder.getPassengers();
        this.owner = builder.getOwner();
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public double getCarryingWeight() {
        return carryingWeight;
    }

    public void setCarryingWeight(double carryingWeight) {
        this.carryingWeight = carryingWeight;
    }

    public double getCarryingVolume() {
        return carryingVolume;
    }

    public void setCarryingVolume(double carryingVolume) {
        this.carryingVolume = carryingVolume;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
        return owner != null ? owner.equals(other.owner) : other.owner == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = carNumber != null ? carNumber.hashCode() : 0;
        temp = Double.doubleToLongBits(carryingWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carryingVolume);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + passengers;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("carNumber='").append(carNumber).append('\'');
        sb.append(", carryingWeight=").append(carryingWeight);
        sb.append(", carryingVolume=").append(carryingVolume);
        sb.append(", passengers=").append(passengers);
        sb.append(", owner=").append(owner);
        sb.append('}');
        return sb.toString();
    }
}
