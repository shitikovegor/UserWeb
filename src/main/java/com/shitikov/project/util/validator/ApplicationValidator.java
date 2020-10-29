package com.shitikov.project.util.validator;

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
        return cargo.matches(CARGO_PATTERN);
    }

    public static boolean checkPassenger(String passengerNumber) {
        return passengerNumber.matches(PASSENGER_PATTERN);
    }

    public static boolean checkTitle(String title) {
        return title.matches(TITLE_PATTERN);
    }

    public static boolean checkDescription(String description) {
        return description.matches(DESCRIPTION_PATTERN);
    }

    public static boolean checkParameters(Map<String, String> parameters) {
        boolean areParametersValid = true;
        String title = parameters.get(TITLE);
        if (title != null && !checkTitle(title)) {
            parameters.replace(ParameterName.TITLE, "");
            areParametersValid = false;
        }
        String cargoWeight = parameters.get(CARGO_WEIGHT);
        if (cargoWeight!= null && !checkCargo(cargoWeight)) {
            parameters.replace(ParameterName.CARGO_WEIGHT, "");
            areParametersValid = false;
        }
        String cargoVolume = parameters.get(CARGO_VOLUME);
        if (cargoVolume != null && !checkCargo(cargoVolume)) {
            parameters.replace(ParameterName.CARGO_VOLUME, "");
            areParametersValid = false;
        }
        String passengerNumber = parameters.get(PASSENGERS_NUMBER);
        if (passengerNumber != null && !checkPassenger(passengerNumber)) {
            parameters.replace(ParameterName.PASSENGERS_NUMBER, "");
            areParametersValid = false;
        }
        String departureDate = parameters.get(DEPARTURE_DATE);
        if (departureDate != null && !AddressDateValidator.checkDate(departureDate)) {
            parameters.replace(ParameterName.DEPARTURE_DATE, "");
            areParametersValid = false;
        }
        String departureAddress = parameters.get(DEPARTURE_ADDRESS);
        if (departureAddress != null && !AddressDateValidator.checkAddress(departureAddress)) {
            parameters.replace(ParameterName.DEPARTURE_ADDRESS, "");
            areParametersValid = false;
        }
        String departureCity = parameters.get(DEPARTURE_CITY);
        if (departureCity != null && !AddressDateValidator.checkCity(departureCity)) {
            parameters.replace(ParameterName.DEPARTURE_CITY, "");
            areParametersValid = false;
        }
        String arrivalDate = parameters.get(ARRIVAL_DATE);
        if (arrivalDate != null && !AddressDateValidator.checkDate(arrivalDate)) {
            parameters.replace(ParameterName.ARRIVAL_DATE, "");
            areParametersValid = false;
        }
        String arrivalAddress = parameters.get(ARRIVAL_ADDRESS);
        if (arrivalAddress != null && !AddressDateValidator.checkAddress(arrivalAddress)) {
            parameters.replace(ParameterName.ARRIVAL_ADDRESS, "");
            areParametersValid = false;
        }
        String arrivalCity = parameters.get(ARRIVAL_CITY);
        if (arrivalCity != null && !AddressDateValidator.checkCity(arrivalCity)) {
            parameters.replace(ParameterName.ARRIVAL_CITY, "");
            areParametersValid = false;
        }
        String description = parameters.get(DESCRIPTION);
        if (description != null && !checkDescription(description)) {
            parameters.replace(ParameterName.DESCRIPTION, "");
            areParametersValid = false;
        }
        return areParametersValid;
    }

    // TODO: 28.10.2020 can I use methods from other validator class in this?
}
