package com.shitikov.project.util.validator;

import com.shitikov.project.model.entity.application.ApplicationType;
import com.shitikov.project.util.ParameterName;

import java.util.Map;

import static com.shitikov.project.util.ParameterName.*;

public class ApplicationValidator extends Validator {
    private static final String CARGO_PATTERN = "\\d+\\.*\\d*";
    private static final String PASSENGER_PATTERN = "\\d+";
    private static final String TITLE_PATTERN = "[\\p{L}0-9\\s-,._!?%#&*+]{1,100}";
    private static final String DESCRIPTION_PATTERN = "[\\p{L}0-9\\s-,._!?%#&*+\n]+";

    private ApplicationValidator() {
    }

    public static boolean checkCargo(String cargo) {
        boolean isValid = false;
        if (cargo != null && !cargo.isEmpty()) {
            isValid = cargo.matches(CARGO_PATTERN);
        }
        return isValid;
    }

    public static boolean checkPassenger(String passengerNumber) {
        boolean isValid = false;
        if (passengerNumber != null && !passengerNumber.isEmpty()) {
            isValid = passengerNumber.matches(PASSENGER_PATTERN);
        }
        return isValid;
    }

    public static boolean checkTitle(String title) {
        boolean isValid = false;
        if (title != null && !title.isEmpty()) {
            isValid = title.matches(TITLE_PATTERN);
        }
        return isValid;
    }

    public static boolean checkDescription(String description) {
        boolean isValid = false;
        if (description != null && !description.isEmpty()) {
            isValid = description.matches(DESCRIPTION_PATTERN);
        }
        return isValid;
    }

    public static boolean checkType(String type) {
        boolean isTypeValid = false;
        if (type != null && !type.isEmpty()) {
            ApplicationType[] types = ApplicationType.values();
            for (ApplicationType applicationType : types) {
                if (type.equalsIgnoreCase(applicationType.name())) {
                    isTypeValid = true;
                }
            }
        }
        return isTypeValid;
    }

    public static boolean checkParameters(Map<String, String> parameters) {
        boolean areParametersValid = true;
        String title = parameters.get(TITLE);
        if (parameters.containsKey(TITLE) && !checkTitle(title)) {
            parameters.replace(ParameterName.TITLE, "");
            areParametersValid = false;
        }
        String departureDate = parameters.get(DEPARTURE_DATE);
        if (parameters.containsKey(DEPARTURE_DATE) && !AddressDateValidator.checkDate(departureDate)) {
            parameters.replace(ParameterName.DEPARTURE_DATE, "");
            areParametersValid = false;
        }
        String departureAddress = parameters.get(DEPARTURE_ADDRESS);
        if (parameters.containsKey(DEPARTURE_ADDRESS) && !AddressDateValidator.checkAddress(departureAddress)) {
            parameters.replace(ParameterName.DEPARTURE_ADDRESS, "");
            areParametersValid = false;
        }
        String departureCity = parameters.get(DEPARTURE_CITY);
        if (parameters.containsKey(DEPARTURE_CITY) && !AddressDateValidator.checkCity(departureCity)) {
            parameters.replace(ParameterName.DEPARTURE_CITY, "");
            areParametersValid = false;
        }
        String arrivalDate = parameters.get(ARRIVAL_DATE);
        if (parameters.containsKey(ARRIVAL_DATE) && !AddressDateValidator.checkDate(arrivalDate)) {
            parameters.replace(ParameterName.ARRIVAL_DATE, "");
            areParametersValid = false;
        }
        String arrivalAddress = parameters.get(ARRIVAL_ADDRESS);
        if (parameters.containsKey(ARRIVAL_ADDRESS) && !AddressDateValidator.checkAddress(arrivalAddress)) {
            parameters.replace(ParameterName.ARRIVAL_ADDRESS, "");
            areParametersValid = false;
        }
        String arrivalCity = parameters.get(ARRIVAL_CITY);
        if (parameters.containsKey(ARRIVAL_CITY) && !AddressDateValidator.checkCity(arrivalCity)) {
            parameters.replace(ParameterName.ARRIVAL_CITY, "");
            areParametersValid = false;
        }
        String description = parameters.get(DESCRIPTION);
        if (parameters.containsKey(DESCRIPTION) && !checkDescription(description)) {
            parameters.replace(ParameterName.DESCRIPTION, "");
            areParametersValid = false;
        }
        return areParametersValid;
    }

    public static boolean checkCargoAppParameters(Map<String, String> parameters) {
        boolean areParametersValid = checkParameters(parameters);
        String cargoWeight = parameters.get(CARGO_WEIGHT);
        if (parameters.containsKey(CARGO_WEIGHT) && !checkCargo(cargoWeight)) {
            parameters.replace(ParameterName.CARGO_WEIGHT, "");
            areParametersValid = false;
        }
        String cargoVolume = parameters.get(CARGO_VOLUME);
        if (parameters.containsKey(CARGO_VOLUME) && !checkCargo(cargoVolume)) {
            parameters.replace(ParameterName.CARGO_VOLUME, "");
            areParametersValid = false;
        }
        return areParametersValid;
    }

    public static boolean checkPassengerAppParameters(Map<String, String> parameters) {
        boolean areParametersValid = checkParameters(parameters);
        String passengerNumber = parameters.get(PASSENGERS_NUMBER);
        if (parameters.containsKey(PASSENGERS_NUMBER) && !checkPassenger(passengerNumber)) {
            parameters.replace(ParameterName.PASSENGERS_NUMBER, "");
            areParametersValid = false;
        }
        return areParametersValid;
    }
}
