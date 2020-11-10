package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.application.CargoApplication;

/**
 * The type Cargo application builder.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class CargoApplicationBuilder extends ApplicationBuilder<CargoApplication> {
    private double cargoWeight;
    private double cargoVolume;

    /**
     * Gets cargo weight.
     *
     * @return the cargo weight
     */
    public double getCargoWeight() {
        return cargoWeight;
    }

    /**
     * Build cargo weight cargo application builder.
     *
     * @param cargoWeight the cargo weight
     * @return the cargo application builder
     */
    public CargoApplicationBuilder buildCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
        return this;
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
     * Build cargo volume cargo application builder.
     *
     * @param cargoVolume the cargo volume
     * @return the cargo application builder
     */
    public CargoApplicationBuilder buildCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
        return this;
    }

    @Override
    public CargoApplication buildApplication() {
        return new CargoApplication(this);
    }
}
