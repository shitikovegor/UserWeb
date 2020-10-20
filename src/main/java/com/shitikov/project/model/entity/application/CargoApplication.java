package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.CargoApplicationBuilder;

public class CargoApplication extends Application {
    private double cargoWeight;
    private double cargoVolume;

    public CargoApplication(String title, ApplicationType applicationType, long date
            , AddressTimeData addressTimeData, String description
            , double cargoWeight, double cargoVolume) {
        super(title, applicationType, date, addressTimeData, description);
        this.cargoWeight = cargoWeight;
        this.cargoVolume = cargoVolume;
    }

    public CargoApplication(CargoApplicationBuilder builder) {
        super(builder);
        this.cargoWeight = builder.getCargoWeight();
        this.cargoVolume = builder.getCargoVolume();
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public double getCargoVolume() {
        return cargoVolume;
    }

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
