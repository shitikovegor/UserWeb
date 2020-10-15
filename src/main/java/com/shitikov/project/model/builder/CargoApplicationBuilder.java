package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.application.CargoApplication;

public class CargoApplicationBuilder extends ApplicationBuilder<CargoApplication> {
    private double cargoWeight;
    private double cargoVolume;

    public double getCargoWeight() {
        return cargoWeight;
    }

    public CargoApplicationBuilder buildCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
        return this;
    }

    public double getCargoVolume() {
        return cargoVolume;
    }

    public CargoApplicationBuilder buildCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
        return this;
    }

    @Override
    public CargoApplication buildApplication() {
        return new CargoApplication(this);
    }
    // TODO: 15.10.2020 is it OK?
}
