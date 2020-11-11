package com.shitikov.project.validator;

import com.shitikov.project.controller.command.AttributeName;

import java.util.Map;

import static com.shitikov.project.util.ParameterName.*;

/**
 * The type User validator.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class UserValidator extends Validator {
    private static final String LOGIN_PATTERN = "^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]{3,19}";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";
    private static final String NAME_SURNAME_PATTERN = "^(?=.*[\\p{L}]$)[\\p{L}\\s-]{0,49}";
    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";
    private static final String PHONE_PATTERN = "^\\+?375\\d{9}";

    private UserValidator() {
    }

    /**
     * Check login boolean.
     *
     * @param login the login
     * @return the boolean
     */
    public static boolean checkLogin(String login) {
        boolean isValid = false;
        if (login != null && !login.isEmpty()) {
            isValid = login.matches(LOGIN_PATTERN);
        }
        return isValid;
    }

    /**
     * Check password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public static boolean checkPassword(String password) {
        boolean isValid = false;
        if (password != null && !password.isEmpty()) {
            isValid = password.matches(PASSWORD_PATTERN);
        }
        return isValid;
    }

    /**
     * Check name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean checkName(String name) {
        boolean isValid = false;
        if (name != null && !name.isEmpty()) {
            isValid = name.matches(NAME_SURNAME_PATTERN);
        }
        return isValid;
    }

    /**
     * Check email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean checkEmail(String email) {
        boolean isValid = false;
        if (email != null && !email.isEmpty()) {
            isValid = email.matches(EMAIL_PATTERN);
        }
        return isValid;
    }

    /**
     * Check phone boolean.
     *
     * @param phone the phone
     * @return the boolean
     */
    public static boolean checkPhone(String phone) {
        boolean isValid = false;
        if (phone != null && !phone.isEmpty()) {
            isValid = phone.matches(PHONE_PATTERN);
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
        boolean areParametersCorrect = true;

        if (!checkLogin(parameters.get(LOGIN))) {
            areParametersCorrect = false;
            parameters.replace(LOGIN, AttributeName.EMPTY_LINE);
        }
        if (!checkPassword(parameters.get(PASSWORD))) {
            areParametersCorrect = false;
            parameters.replace(PASSWORD, AttributeName.EMPTY_LINE);
        }
        if (!checkName(parameters.get(NAME))) {
            areParametersCorrect = false;
            parameters.replace(NAME, AttributeName.EMPTY_LINE);
        }
        if (!checkName(parameters.get(SURNAME))) {
            areParametersCorrect = false;
            parameters.replace(SURNAME, AttributeName.EMPTY_LINE);
        }
        if (!checkEmail(parameters.get(EMAIL))) {
            areParametersCorrect = false;
            parameters.replace(EMAIL, AttributeName.EMPTY_LINE);
        }
        if (!checkPhone(parameters.get(PHONE))) {
            areParametersCorrect = false;
            parameters.replace(PHONE, AttributeName.EMPTY_LINE);
        }

        if (parameters.get(SUBJECT_TYPE) == null) {
            areParametersCorrect = false;
            parameters.remove(SUBJECT_TYPE);
        }

        if (parameters.get(ROLE_TYPE) == null) {
            areParametersCorrect = false;
            parameters.remove(ROLE_TYPE);
        }

        return areParametersCorrect;
    }
}
