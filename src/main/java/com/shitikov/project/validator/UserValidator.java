package com.shitikov.project.validator;

import java.util.Map;

import static com.shitikov.project.util.ParameterName.*;

public class UserValidator {
    private static final String LOGIN_PATTERN = "^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]{4,20}";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";
    private static final String NAME_SURNAME_PATTERN = "[\\p{L}\\s-]{1,50}";
    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";
    private static final String PHONE_PATTERN = "^\\+?\\d{12}";
    private static final String ADDRESS_PATTERN = "[\\p{L}\\p{Digit}\\s\\-,]{1,150}";
    private static final String CITY_PATTERN = "[\\p{L}\\s-\\p{Digit}]{1,50}";

    private UserValidator() {
    }

    public static boolean checkLogin(String login) {
        return login.matches(LOGIN_PATTERN);
    }

    public static boolean checkPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public static boolean checkName(String name) {
        return name.matches(NAME_SURNAME_PATTERN);
    }

    public static boolean checkSurname(String surname) {
        return surname.matches(NAME_SURNAME_PATTERN);
    }

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean checkPhone(String phone) {
        return phone.matches(PHONE_PATTERN);
    }

    public static boolean checkAddress(String address) {
        return address.matches(ADDRESS_PATTERN);
    }

    public static boolean checkCity(String city) {
        return city.matches(CITY_PATTERN);
    }

    public static boolean checkParameters(Map<String, String> parameters) {
        boolean areParametersCorrect = true;

        if (!checkLogin(parameters.get(LOGIN))) {
            areParametersCorrect = false;
            parameters.replace(LOGIN, "");
        }
        if (!checkPassword(parameters.get(PASSWORD))) {
            areParametersCorrect = false;
            parameters.replace(PASSWORD, "");
        }
        if (!checkName(parameters.get(NAME))) {
            areParametersCorrect = false;
            parameters.replace(NAME, "");
        }
        if (!checkSurname(parameters.get(SURNAME))) {
            areParametersCorrect = false;
            parameters.replace(SURNAME, "");
        }
        if (!checkEmail(parameters.get(EMAIL))) {
            areParametersCorrect = false;
            parameters.replace(EMAIL, "");
        }
        if (!checkPhone(parameters.get(PHONE))) {
            areParametersCorrect = false;
            parameters.replace(PHONE, "");
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
