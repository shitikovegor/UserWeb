package com.shitikov.project.util.validator;

import com.shitikov.project.util.ParameterName;

import java.util.Map;

public class CarValidator extends Validator {
    private static final String CAR_NUMBER_PATTERN ="\\d{4}\\p{Alpha}{2}[1-7]";

    private CarValidator() {}

    public static boolean checkCarNumber(String carNumber) {
        boolean isValid = false;
        if (carNumber != null && !carNumber.isEmpty()) {
            isValid = carNumber.matches(CAR_NUMBER_PATTERN);
        }
        return isValid;
    }

    public static boolean checkParameters(Map<String, String> parameters) {
        boolean areParametersValid = true;
        String carNumber = parameters.get(ParameterName.CAR_NUMBER);
        if (parameters.containsKey(ParameterName.CAR_NUMBER) && !checkCarNumber(carNumber)) {
            parameters.replace(ParameterName.CAR_NUMBER, "");
            areParametersValid = false;
        }
        String weight = parameters.get(ParameterName.CARRYING_WEIGHT);
        if (parameters.containsKey(ParameterName.CARRYING_WEIGHT) && !ApplicationValidator.checkCargo(weight)) {
            parameters.replace(ParameterName.CARRYING_WEIGHT, "");
            areParametersValid = false;
        }
        String volume = parameters.get(ParameterName.CARRYING_VOLUME);
        if (parameters.containsKey(ParameterName.CARRYING_VOLUME) && !ApplicationValidator.checkCargo(volume)) {
            parameters.replace(ParameterName.CARRYING_VOLUME, "");
            areParametersValid = false;
        }
        String passengerNumber = parameters.get(ParameterName.PASSENGERS_NUMBER);
        if (parameters.containsKey(ParameterName.PASSENGERS_NUMBER)
                && !ApplicationValidator.checkPassenger(passengerNumber)) {
            parameters.replace(ParameterName.PASSENGERS_NUMBER, "");
            areParametersValid = false;
        }
        return areParametersValid;
    }
}
