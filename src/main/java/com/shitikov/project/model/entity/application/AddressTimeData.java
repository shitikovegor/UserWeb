package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.AddressTimeDataBuilder;
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

    /**
     * Instantiates a new Address time data.
     *
     * @param departureDate    the departure date
     * @param departureAddress the departure address
     * @param arrivalDate      the arrival date
     * @param arrivalAddress   the arrival address
     */
    public AddressTimeData(long departureDate, Address departureAddress, long arrivalDate,
                           Address arrivalAddress) {
        this.departureDate = departureDate;
        this.departureAddress = departureAddress;
        this.arrivalDate = arrivalDate;
        this.arrivalAddress = arrivalAddress;
    }

    /**
     * Instantiates a new Address time data.
     *
     * @param builder the builder
     */
    public AddressTimeData(AddressTimeDataBuilder builder) {
        this.departureDate = builder.getDepartureDate();
        this.departureAddress = builder.getDepartureAddress();
        this.arrivalDate = builder.getArrivalDate();
        this.arrivalAddress = builder.getArrivalAddress();
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
}
