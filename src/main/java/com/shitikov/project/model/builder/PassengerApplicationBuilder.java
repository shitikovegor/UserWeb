package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.application.PassengerApplication;

public class PassengerApplicationBuilder extends ApplicationBuilder<PassengerApplication> {
    private int passengersNumber;

    public int getPassengersNumber() {
        return passengersNumber;
    }

    public PassengerApplicationBuilder buildPassengersNumber(int passengersNumber) {
        this.passengersNumber = passengersNumber;
        return this;
    }

    @Override
    public PassengerApplication buildApplication() {
        return new PassengerApplication(this);
    }
}
