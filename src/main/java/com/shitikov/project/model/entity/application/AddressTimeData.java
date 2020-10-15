package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.AddressTimeDataBuilder;
import com.shitikov.project.model.entity.Address;

public class AddressTimeData {
    private long departureDate;
    private Address departureAddress;
    private int daysToComplete;
    private Address arrivalAddress;

    public AddressTimeData(AddressTimeDataBuilder builder) {
        this.departureDate = builder.getDepartureDate();
        this.departureAddress = builder.getDepartureAddress();
        this.daysToComplete = builder.getDaysToComplete();
        this.arrivalAddress = builder.getArrivalAddress();
    }

    public long getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(long departureDate) {
        this.departureDate = departureDate;
    }

    public Address getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(Address departureAddress) {
        this.departureAddress = departureAddress;
    }

    public int getDaysToComplete() {
        return daysToComplete;
    }

    public void setDaysToComplete(int daysToComplete) {
        this.daysToComplete = daysToComplete;
    }

    public Address getArrivalAddress() {
        return arrivalAddress;
    }

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
        if (daysToComplete != other.daysToComplete) {
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
        result = 31 * result + daysToComplete;
        result = 31 * result + (arrivalAddress != null ? arrivalAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApplicationAddress{");
        sb.append("departureDate=").append(departureDate);
        sb.append(", departureAddress='").append(departureAddress).append('\'');
        sb.append(", daysToComplete=").append(daysToComplete);
        sb.append(", arrivalAddress='").append(arrivalAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }
}