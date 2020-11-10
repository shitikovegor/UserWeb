package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.application.PassengerApplication;

/**
 * The type Passenger application builder.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class PassengerApplicationBuilder extends ApplicationBuilder<PassengerApplication> {
    private int passengersNumber;

    /**
     * Gets passengers number.
     *
     * @return the passengers number
     */
    public int getPassengersNumber() {
        return passengersNumber;
    }

    /**
     * Build passengers number passenger application builder.
     *
     * @param passengersNumber the passengers number
     * @return the passenger application builder
     */
    public PassengerApplicationBuilder buildPassengersNumber(int passengersNumber) {
        this.passengersNumber = passengersNumber;
        return this;
    }

    @Override
    public PassengerApplication buildApplication() {
        return new PassengerApplication(this);
    }
}
