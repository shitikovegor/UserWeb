package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.PassengerApplicationBuilder;

public class PassengerApplication extends Application {
    private int passengersNumber;

    public PassengerApplication(long applicationId, String title, ApplicationType applicationType
            , long date, AddressTimeData addressTimeData
            , String description, int passengersNumber) {
        super(applicationId, title, applicationType, date, addressTimeData, description);
        this.passengersNumber = passengersNumber;
    }

    public PassengerApplication(PassengerApplicationBuilder builder) {
        super(builder);
        this.passengersNumber = builder.getPassengersNumber();
    }

    public int getPassengersNumber() {
        return passengersNumber;
    }

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
