package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Address;

public class AddressBuilder {
    private String city;
    private String streetHome;

    public String getCity() {
        return city;
    }

    public AddressBuilder buildCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreetHome() {
        return streetHome;
    }

    public AddressBuilder buildStreetHome(String streetHome) {
        this.streetHome = streetHome;
        return this;
    }

    public Address buildAddress() {
        return new Address(this);
    }
}
