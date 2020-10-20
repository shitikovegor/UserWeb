package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.AddressBuilder;

public class Address extends Entity {
    private String city;
    private String streetHome;

    public Address(String city, String streetHome) {
        this.city = city;
        this.streetHome = streetHome;
    }

    public Address(AddressBuilder builder) {
        this.city = builder.getCity();
        this.streetHome = builder.getStreetHome();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetHome() {
        return streetHome;
    }

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
