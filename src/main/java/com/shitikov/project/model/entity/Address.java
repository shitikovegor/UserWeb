package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.AddressBuilder;

/**
 * The type Address.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class Address extends Entity {
    private String city;
    private String streetHome;

    /**
     * Instantiates a new Address.
     *
     * @param city       the city
     * @param streetHome the street home
     */
    public Address(String city, String streetHome) {
        this.city = city;
        this.streetHome = streetHome;
    }

    /**
     * Instantiates a new Address.
     *
     * @param builder the builder
     */
    public Address(AddressBuilder builder) {
        this.city = builder.getCity();
        this.streetHome = builder.getStreetHome();
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
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
     * Sets street home.
     *
     * @param streetHome the street home
     */
    public void setStreetHome(String streetHome) {
        this.streetHome = streetHome;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address other = (Address) obj;
        if (city != null ? !city.equals(other.city) : other.city != null) {
            return false;
        }
        return streetHome != null ? streetHome.equals(other.streetHome) : other.streetHome == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (streetHome != null ? streetHome.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("city='").append(city).append('\'');
        sb.append(", streetHome='").append(streetHome).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
