package com.shitikov.project.validator;

public class CarValidator {
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
}
