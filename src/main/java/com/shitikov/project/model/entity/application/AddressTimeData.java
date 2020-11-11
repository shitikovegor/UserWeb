package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.Entity;

/**
 * The type Address time data.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class AddressTimeData extends Entity {
    private long departureDate;
    private Address departureAddress;
    private long arrivalDate;
    private Address arrivalAddress;

    private AddressTimeData() {
    }

    /**
     * Gets departure date.
     *
     * @return the departure date
     */
    public long getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets departure date.
     *
     * @param departureDate the departure date
     */
    public void setDepartureDate(long departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Gets departure address.
     *
     * @return the departure address
     */
    public Address getDepartureAddress() {
        return departureAddress;
    }

    /**
     * Sets departure address.
     *
     * @param departureAddress the departure address
     */
    public void setDepartureAddress(Address departureAddress) {
        this.departureAddress = departureAddress;
    }

    /**
     * Gets arrival date.
     *
     * @return the arrival date
     */
    public long getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets arrival date.
     *
     * @param arrivalDate the arrival date
     */
    public void setArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    /**
     * Gets arrival address.
     *
     * @return the arrival address
     */
    public Address getArrivalAddress() {
        return arrivalAddress;
    }

    /**
     * Sets arrival address.
     *
     * @param arrivalAddress the arrival address
     */
    public void setArrivalAddress(Address arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AddressTimeData other = (AddressTimeData) obj;
        if (departureDate != other.departureDate) {
            return false;
        }
        if (arrivalDate != other.arrivalDate) {
            return false;
        }
        if (departureAddress != null ? !departureAddress.equals(other.departureAddress) : other.departureAddress != null) {
            return false;
        }
        return arrivalAddress != null ? arrivalAddress.equals(other.arrivalAddress) : other.arrivalAddress == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (departureDate ^ (departureDate >>> 32));
        result = 31 * result + (departureAddress != null ? departureAddress.hashCode() : 0);
        result = 31 * result + (int) (arrivalDate ^ (arrivalDate >>> 32));
        result = 31 * result + (arrivalAddress != null ? arrivalAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApplicationAddress{");
        sb.append("departureDate=").append(departureDate);
        sb.append(", departureAddress='").append(departureAddress).append('\'');
        sb.append(", arrivalDate=").append(arrivalDate);
        sb.append(", arrivalAddress='").append(arrivalAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * New builder address time data . builder.
     *
     * @return the address time data . builder
     */
    public static AddressTimeData.Builder newBuilder() {
        return new AddressTimeData().new Builder();
    }

    /**
     * The type Builder.
     *
     * @author Shitikov Egor
     * @version 1.0
     */
    public class Builder {

        private Builder() {
        }

        /**
         * Build departure date builder.
         *
         * @param departureDate the departure date
         * @return the builder
         */
        public Builder buildDepartureDate(long departureDate) {
            AddressTimeData.this.departureDate = departureDate;
            return this;
        }

        /**
         * Build departure address builder.
         *
         * @param departureAddress the departure address
         * @return the builder
         */
        public Builder buildDepartureAddress(Address departureAddress) {
            AddressTimeData.this.departureAddress = departureAddress;
            return this;
        }

        /**
         * Build arrival date builder.
         *
         * @param arrivalDate the arrival date
         * @return the builder
         */
        public Builder buildArrivalDate(long arrivalDate) {
            AddressTimeData.this.arrivalDate = arrivalDate;
            return this;
        }

        /**
         * Build arrival address builder.
         *
         * @param arrivalAddress the arrival address
         * @return the builder
         */
        public Builder buildArrivalAddress(Address arrivalAddress) {
            AddressTimeData.this.arrivalAddress = arrivalAddress;
            return this;
        }

        /**
         * Build address time data address time data.
         *
         * @return the address time data
         */
        public AddressTimeData buildAddressTimeData() {
            return AddressTimeData.this;
        }
    }
}