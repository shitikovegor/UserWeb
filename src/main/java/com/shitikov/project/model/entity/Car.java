package com.shitikov.project.model.entity;

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

    private Car(){
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

    /**
     * New builder builder.
     *
     * @return the builder
     */
    public static Builder newBuilder() {
        return new Car().new Builder();
    }

    /**
     * The type Builder.
     *
     * @author Shitikov Egor
     * @version 1.0
     */
    public class Builder {

        private Builder() {
        }

        /**
         * Build car id builder.
         *
         * @param carId the car id
         * @return the builder
         */
        public Builder buildCarId(long carId) {
            Car.this.carId = carId;
            return this;
        }

        /**
         * Build car number builder.
         *
         * @param carNumber the car number
         * @return the builder
         */
        public Builder buildCarNumber(String carNumber) {
            Car.this.carNumber = carNumber;
            return this;
        }

        /**
         * Build carrying weight builder.
         *
         * @param carryingWeight the carrying weight
         * @return the builder
         */
        public Builder buildCarryingWeight(double carryingWeight) {
            Car.this.carryingWeight = carryingWeight;
            return this;
        }

        /**
         * Build carrying volume builder.
         *
         * @param carryingVolume the carrying volume
         * @return the builder
         */
        public Builder buildCarryingVolume(double carryingVolume) {
            Car.this.carryingVolume = carryingVolume;
            return this;
        }

        /**
         * Build passengers builder.
         *
         * @param passengers the passengers
         * @return the builder
         */
        public Builder buildPassengers(int passengers) {
            Car.this.passengers = passengers;
            return this;
        }

        /**
         * Build removed builder.
         *
         * @param removed the removed
         * @return the builder
         */
        public Builder buildRemoved(boolean removed) {
            Car.this.removed = removed;
            return this;
        }

        /**
         * Build car car.
         *
         * @return the car
         */
        public Car buildCar() {
            return Car.this;
        }
    }

}
