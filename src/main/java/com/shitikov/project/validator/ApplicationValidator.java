package com.shitikov.project.validator;

public class ApplicationValidator {
    private static final String CARGO_PATTERN = "\\d+\\.*\\d*";
    private static final String PASSENGER_PATTERN = "\\d+";
    private static final String TITLE_PATTERN = "[\\p{L}0-9\\s-,._!?%#&*+]{1,100}";
    private static final String DESCRIPTION_PATTERN = "[\\p{L}0-9\\s-,._!?%#&*+]+";

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
}
