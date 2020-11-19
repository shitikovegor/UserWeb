package com.shitikov.project.validator;

import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.util.ParameterName;

import java.util.Map;

/**
 * The type Car validator.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class CarValidator implements Validator {
    private static final String CAR_NUMBER_PATTERN ="\\d{4}\\p{Alpha}{2}[1-7]";

    private CarValidator() {}

    /**
     * Check car number boolean.
     *
     * @param carNumber the car number
     * @return the boolean
     */
    public static boolean checkCarNumber(String carNumber) {
        boolean isValid = false;
        if (carNumber != null && !carNumber.isEmpty()) {
            isValid = carNumber.matches(CAR_NUMBER_PATTERN);
        }
        return isValid;
    }

    /**
     * Check parameters boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     */
    public static boolean checkParameters(Map<String, String> parameters) {
        boolean areParametersValid = true;
        String carNumber = parameters.get(ParameterName.CAR_NUMBER);
        if (parameters.containsKey(ParameterName.CAR_NUMBER) && !checkCarNumber(carNumber)) {
            parameters.replace(ParameterName.CAR_NUMBER, AttributeName.EMPTY_LINE);
            areParametersValid = false;
        }
        String weight = parameters.get(ParameterName.CARRYING_WEIGHT);
        if (parameters.containsKey(ParameterName.CARRYING_WEIGHT) && !ApplicationValidator.checkCargo(weight)) {
            parameters.replace(ParameterName.CARRYING_WEIGHT, AttributeName.EMPTY_LINE);
            areParametersValid = false;
        }
        String volume = parameters.get(ParameterName.CARRYING_VOLUME);
        if (parameters.containsKey(ParameterName.CARRYING_VOLUME) && !ApplicationValidator.checkCargo(volume)) {
            parameters.replace(ParameterName.CARRYING_VOLUME, AttributeName.EMPTY_LINE);
            areParametersValid = false;
        }
        String passengerNumber = parameters.get(ParameterName.PASSENGERS_NUMBER);
        if (parameters.containsKey(ParameterName.PASSENGERS_NUMBER)
                && !ApplicationValidator.checkPassenger(passengerNumber)) {
            parameters.replace(ParameterName.PASSENGERS_NUMBER, AttributeName.EMPTY_LINE);
            areParametersValid = false;
        }
        return areParametersValid;
    }
}
