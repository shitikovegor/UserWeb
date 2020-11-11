package com.shitikov.project.model.entity;

/**
 * The type Address.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class Address extends Entity {
    private String city;
    private String streetHome;

    private Address() {
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

    /**
     * New builder address . builder.
     *
     * @return the address . builder
     */
    public static Address.Builder newBuilder() {
        return new Address().new Builder();
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
         * Build city builder.
         *
         * @param city the city
         * @return the builder
         */
        public Builder buildCity(String city) {
            Address.this.city = city;
            return this;
        }

        /**
         * Build street home builder.
         *
         * @param streetHome the street home
         * @return the builder
         */
        public Builder buildStreetHome(String streetHome) {
            Address.this.streetHome = streetHome;
            return this;
        }

        /**
         * Build address address.
         *
         * @return the address
         */
        public Address buildAddress() {
            return Address.this;
        }
    }
}
