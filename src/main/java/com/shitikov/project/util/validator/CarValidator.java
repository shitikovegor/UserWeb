package com.shitikov.project.util.validator;

import com.shitikov.project.util.ParameterName;

import java.util.Map;

public class CarValidator extends Validator {
    private static final String CAR_NUMBER_PATTERN ="\\d{4}\\p{Alpha}{2}[1-7]";
    private static final String CARRYING_PATTERN = "\\d+\\.*\\d*";
    private static final String PASSENGER_PATTERN = "\\d+";

    private CarValidator() {}

    public static boolean checkCarNumber(String carNumber) {
        return carNumber.matches(CAR_NUMBER_PATTERN);
    }

    public static boolean checkCarrying(String cargo) {
        return cargo.matches(CARRYING_PATTERN);
    }

    public static boolean checkPassenger(String passengerNumber) {
        return passengerNumber.matches(PASSENGER_PATTERN);
    }

    public static boolean checkParameters(Map<String, String> parameters) {
        boolean areParametersValid = true;
        String carNumber = parameters.get(ParameterName.CAR_NUMBER);
        if (carNumber != null && !checkCarNumber(carNumber)) {
            parameters.replace(ParameterName.CAR_NUMBER, "");
            areParametersValid = false;
        }
        String weight = parameters.get(ParameterName.CARRYING_WEIGHT);
        if (weight != null && !checkCarrying(weight)) {
            parameters.replace(ParameterName.CARRYING_WEIGHT, "");
            areParametersValid = false;
        }
        String volume = parameters.get(ParameterName.CARRYING_VOLUME);
        if (volume != null && !checkCarrying(volume)) {
            parameters.replace(ParameterName.CARRYING_VOLUME, "");
            areParametersValid = false;
        }
        String passengerNumber = parameters.get(ParameterName.PASSENGERS_NUMBER);
        if (passengerNumber != null && !checkPassenger(passengerNumber)) {
            parameters.replace(ParameterName.PASSENGERS_NUMBER, "");
            areParametersValid = false;
        }
        return areParametersValid;
    }
}
