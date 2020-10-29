package com.shitikov.project.util.validator;

import java.util.Map;

import static com.shitikov.project.util.ParameterName.*;

public class AddressDateValidator extends Validator {
    private static final String ADDRESS_PATTERN = "[\\p{L}\\p{Digit}\\s-,./]{1,150}";
    private static final String CITY_PATTERN = "[\\p{L}\\s-\\p{Digit}]{1,50}";
    private static final String DATE_PATTERN = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    private static final String DATE_IE_PATTERN = "^(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$";

    private AddressDateValidator() {
    }

    public static boolean checkAddress(String address) {
        return address.matches(ADDRESS_PATTERN);
    }

    public static boolean checkCity(String city) {
        return city.matches(CITY_PATTERN);
    }

    public static boolean checkDate(String date) {
        return date.matches(DATE_PATTERN) || date.matches(DATE_IE_PATTERN);
    }

    public static boolean checkParameters(Map<String, String> parameters) {
        boolean areParametersCorrect = true;

        if (!checkAddress(parameters.get(ADDRESS))) {
            areParametersCorrect = false;
            parameters.replace(ADDRESS, "");
        }
        if (!checkCity(parameters.get(CITY))) {
            areParametersCorrect = false;
            parameters.replace(CITY, "");
        }
        return areParametersCorrect;
    }
}
