package com.shitikov.project.model.entity.application;

/**
 * The type Cargo application.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class CargoApplication extends Application {
    private double cargoWeight;
    private double cargoVolume;

    private CargoApplication() {
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

    /**
     * New builder cargo application . builder.
     *
     * @return the cargo application . builder
     */
    public static CargoApplication.Builder newBuilder() {
        return new CargoApplication().new Builder();
    }

    /**
     * The type Builder.
     *
     * @author Shitikov Egor
     * @version 1.0
     */
    public class Builder extends Application.Builder<CargoApplication> {

        private Builder() {
        }

        /**
         * Build cargo weight builder.
         *
         * @param cargoWeight the cargo weight
         * @return the builder
         */
        public Builder buildCargoWeight(double cargoWeight) {
            CargoApplication.this.cargoWeight = cargoWeight;
            return this;
        }

        /**
         * Build cargo volume builder.
         *
         * @param cargoVolume the cargo volume
         * @return the builder
         */
        public Builder buildCargoVolume(double cargoVolume) {
            CargoApplication.this.cargoVolume = cargoVolume;
            return this;
        }

        @Override
        public CargoApplication buildApplication() {
            return CargoApplication.this;
        }
    }
}
