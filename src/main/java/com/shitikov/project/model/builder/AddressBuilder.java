package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.Address;

/**
 * The type Address builder.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class AddressBuilder {
    private String city;
    private String streetHome;

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Build city address builder.
     *
     * @param city the city
     * @return the address builder
     */
    public AddressBuilder buildCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * Gets street home.
     *
     * @return the street home
     */
    public String getStreetHome() {
        return streetHome;
    }

    /**
     * Build street home address builder.
     *
     * @param streetHome the street home
     * @return the address builder
     */
    public AddressBuilder buildStreetHome(String streetHome) {
        this.streetHome = streetHome;
        return this;
    }

    /**
     * Build address address.
     *
     * @return the address
     */
    public Address buildAddress() {
        return new Address(this);
    }
}
