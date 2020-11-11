package com.shitikov.project.model.entity.application;

/**
 * The type Passenger application.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class PassengerApplication extends Application {
    private int passengersNumber;

    private PassengerApplication() {
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

    /**
     * New builder passenger application . builder.
     *
     * @return the passenger application . builder
     */
    public static PassengerApplication.Builder newBuilder() {
        return new PassengerApplication().new Builder();
    }

    /**
     * The type Builder.
     *
     * @author Shitikov Egor
     * @version 1.0
     */
    public class Builder extends Application.Builder<PassengerApplication> {

        private Builder() {
        }

        /**
         * Build passengers number builder.
         *
         * @param passengersNumber the passengers number
         * @return the builder
         */
        public Builder buildPassengersNumber(int passengersNumber) {
            PassengerApplication.this.passengersNumber = passengersNumber;
            return this;
        }

        @Override
        public PassengerApplication buildApplication() {
            return PassengerApplication.this;
        }
    }
}
