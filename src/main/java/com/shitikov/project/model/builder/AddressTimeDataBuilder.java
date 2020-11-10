package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.application.AddressTimeData;

/**
 * The type Address time data builder.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class AddressTimeDataBuilder {
    private long departureDate;
    private Address departureAddress;
    private long arrivalDate;
    private Address arrivalAddress;

    /**
     * Gets departure date.
     *
     * @return the departure date
     */
    public long getDepartureDate() {
        return departureDate;
    }

    /**
     * Build departure date address time data builder.
     *
     * @param departureDate the departure date
     * @return the address time data builder
     */
    public AddressTimeDataBuilder buildDepartureDate(long departureDate) {
        this.departureDate = departureDate;
        return this;
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
     * Build departure address address time data builder.
     *
     * @param departureAddress the departure address
     * @return the address time data builder
     */
    public AddressTimeDataBuilder buildDepartureAddress(Address departureAddress) {
        this.departureAddress = departureAddress;
        return this;
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
     * Build arrival date address time data builder.
     *
     * @param arrivalDate the arrival date
     * @return the address time data builder
     */
    public AddressTimeDataBuilder buildArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
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
     * Build arrival address address time data builder.
     *
     * @param arrivalAddress the arrival address
     * @return the address time data builder
     */
    public AddressTimeDataBuilder buildArrivalAddress(Address arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
        return this;
    }

    /**
     * Build address time data address time data.
     *
     * @return the address time data
     */
    public AddressTimeData buildAddressTimeData() {
        return new AddressTimeData(this);
    }
}
