package com.shitikov.project.util.validator;

public abstract class Validator {
    private static final String APP_ID_PATTERN = "^[1-9]\\d{0,17}";

    public static boolean checkId(String id) {
        return id.matches(APP_ID_PATTERN);
    }
}
