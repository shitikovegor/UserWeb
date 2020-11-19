package com.shitikov.project.validator;

/**
 * The type Address date validator.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class AddressDateValidator implements Validator {
    private static final String ADDRESS_PATTERN = "[\\p{L}\\p{Digit}\\s-,./]{1,150}";
    private static final String CITY_PATTERN = "[\\p{L}\\s-\\p{Digit}]{1,50}";
    private static final String DATE_PATTERN = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    private static final String DATE_IE_PATTERN = "^(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$";

    private AddressDateValidator() {
    }

    /**
     * Check address boolean.
     *
     * @param address the address
     * @return the boolean
     */
    public static boolean checkAddress(String address) {
        boolean isValid = false;
        if (address != null && !address.isEmpty()) {
            isValid = address.matches(ADDRESS_PATTERN);
        }
        return isValid;
    }

    /**
     * Check city boolean.
     *
     * @param city the city
     * @return the boolean
     */
    public static boolean checkCity(String city) {
        boolean isValid = false;
        if (city != null && !city.isEmpty()) {
            isValid = city.matches(CITY_PATTERN);
        }
        return isValid;
    }

    /**
     * Check date boolean.
     *
     * @param date the date
     * @return the boolean
     */
    public static boolean checkDate(String date) {
        boolean isValid = false;
        if (date != null && !date.isEmpty()) {
            isValid = date.matches(DATE_PATTERN) || date.matches(DATE_IE_PATTERN);
        }
        return isValid;
    }
}
