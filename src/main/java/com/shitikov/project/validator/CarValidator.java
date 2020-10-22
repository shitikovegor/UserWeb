package com.shitikov.project.validator;

public class CarValidator {
    private static final String NUMBER_PATTERN ="\\d{4}\\p{Alpha}{2}[1-7]";

    private CarValidator() {}

    public static boolean checkCarNumber(String carNumber) {
        return carNumber.matches(NUMBER_PATTERN);
    }
}
