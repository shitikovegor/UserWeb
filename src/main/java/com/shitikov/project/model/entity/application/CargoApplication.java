package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.CargoApplicationBuilder;
import com.shitikov.project.model.entity.type.ApplicationType;

/**
 * The type Cargo application.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class CargoApplication extends Application {
    private double cargoWeight;
    private double cargoVolume;

    /**
     * Instantiates a new Cargo application.
     *
     * @param applicationId   the application id
     * @param title           the title
     * @param applicationType the application type
     * @param date            the date
     * @param addressTimeData the address time data
     * @param description     the description
     * @param cargoWeight     the cargo weight
     * @param cargoVolume     the cargo volume
     */
    public CargoApplication(long applicationId, String title, ApplicationType applicationType, long date
            , AddressTimeData addressTimeData, String description
            , double cargoWeight, double cargoVolume) {
        super(applicationId, title, applicationType, date, addressTimeData, description);
        this.cargoWeight = cargoWeight;
        this.cargoVolume = cargoVolume;
    }

    /**
     * Instantiates a new Cargo application.
     *
     * @param builder the builder
     */
    public CargoApplication(CargoApplicationBuilder builder) {
        super(builder);
        this.cargoWeight = builder.getCargoWeight();
        this.cargoVolume = builder.getCargoVolume();
    }

    /**
     * Gets cargo weight.
     *
     * @return the cargo weight
     */
    public double getCargoWeight() {
        return cargoWeight;
    }

    /**
     * Sets cargo weight.
     *
     * @param cargoWeight the cargo weight
     */
    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    /**
     * Gets cargo volume.
     *
     * @return the cargo volume
     */
    public double getCargoVolume() {
        return cargoVolume;
    }

    /**
     * Sets cargo volume.
     *
     * @param cargoVolume the cargo volume
     */
    public void setCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        CargoApplication other = (CargoApplication) obj;
        if (Double.compare(other.cargoWeight, cargoWeight) != 0) {
            return false;
        }
        return Double.compare(other.cargoVolume, cargoVolume) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(cargoWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cargoVolume);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CargoApplication{");
        sb.append(super.toString());
        sb.append("cargoWeight=").append(cargoWeight);
        sb.append(", cargoVolume=").append(cargoVolume);
        sb.append('}');
        return sb.toString();
    }
}
