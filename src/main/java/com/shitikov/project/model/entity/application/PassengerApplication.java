package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.PassengerApplicationBuilder;
import com.shitikov.project.model.entity.type.ApplicationType;

/**
 * The type Passenger application.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class PassengerApplication extends Application {
    private int passengersNumber;

    /**
     * Instantiates a new Passenger application.
     *
     * @param applicationId    the application id
     * @param title            the title
     * @param applicationType  the application type
     * @param date             the date
     * @param addressTimeData  the address time data
     * @param description      the description
     * @param passengersNumber the passengers number
     */
    public PassengerApplication(long applicationId, String title, ApplicationType applicationType
            , long date, AddressTimeData addressTimeData
            , String description, int passengersNumber) {
        super(applicationId, title, applicationType, date, addressTimeData, description);
        this.passengersNumber = passengersNumber;
    }

    /**
     * Instantiates a new Passenger application.
     *
     * @param builder the builder
     */
    public PassengerApplication(PassengerApplicationBuilder builder) {
        super(builder);
        this.passengersNumber = builder.getPassengersNumber();
    }

    /**
     * Gets passengers number.
     *
     * @return the passengers number
     */
    public int getPassengersNumber() {
        return passengersNumber;
    }

    /**
     * Sets passengers number.
     *
     * @param passengersNumber the passengers number
     */
    public void setPassengersNumber(int passengersNumber) {
        this.passengersNumber = passengersNumber;
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
        PassengerApplication other = (PassengerApplication) obj;
        return passengersNumber == other.passengersNumber;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + passengersNumber;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PassengerApplication{");
        sb.append(super.toString());
        sb.append("passengersNumber=").append(passengersNumber);
        sb.append('}');
        return sb.toString();
    }
}
