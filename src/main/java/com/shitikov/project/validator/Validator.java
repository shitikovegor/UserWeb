package com.shitikov.project.validator;

/**
 * The type Validator.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public abstract class Validator {
    private static final String APP_ID_PATTERN = "^[1-9]\\d{0,17}";

    /**
     * Check id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean checkId(String id) {
        return id.matches(APP_ID_PATTERN);
    }
}
