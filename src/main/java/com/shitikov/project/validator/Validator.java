package com.shitikov.project.validator;

/**
 * The interface Validator.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface Validator {
    /**
     * The constant APP_ID_PATTERN.
     */
    String APP_ID_PATTERN = "^[1-9]\\d{0,17}";

    /**
     * Check id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    static boolean checkId(String id) {
        return id.matches(APP_ID_PATTERN);
    }
}
