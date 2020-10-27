package com.shitikov.project.validator;

import java.util.Map;

import static com.shitikov.project.util.ParameterName.ADDRESS;
import static com.shitikov.project.util.ParameterName.CITY;

public class AddressValidator {
    private static final String ADDRESS_PATTERN = "[\\p{L}\\p{Digit}\\s-,.]{1,150}";
    private static final String CITY_PATTERN = "[\\p{L}\\s-\\p{Digit}]{1,50}";

    private AddressValidator() {
    }

    public static boolean checkAddress(String address) {
        return address.matches(ADDRESS_PATTERN);
    }

    public static boolean checkCity(String city) {
        return city.matches(CITY_PATTERN);
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
