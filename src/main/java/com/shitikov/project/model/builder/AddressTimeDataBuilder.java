package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.application.AddressTimeData;

public class AddressTimeDataBuilder {
    private long departureDate;
    private Address departureAddress;
    private long arrivalDate;
    private Address arrivalAddress;

    public long getDepartureDate() {
        return departureDate;
    }

    public AddressTimeDataBuilder buildDepartureDate(long departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public Address getDepartureAddress() {
        return departureAddress;
    }

    public AddressTimeDataBuilder buildDepartureAddress(Address departureAddress) {
        this.departureAddress = departureAddress;
        return this;
    }

    public long getArrivalDate() {
        return arrivalDate;
    }

    public AddressTimeDataBuilder buildArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public Address getArrivalAddress() {
        return arrivalAddress;
    }

    public AddressTimeDataBuilder buildArrivalAddress(Address arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
        return this;
    }

    public AddressTimeData buildAddressTimeData() {
        return new AddressTimeData(this);
    }
}
