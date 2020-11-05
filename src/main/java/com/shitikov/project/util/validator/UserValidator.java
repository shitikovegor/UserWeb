package com.shitikov.project.util.validator;

import java.util.Map;

import static com.shitikov.project.util.ParameterName.*;

public class UserValidator extends Validator {
    private static final String LOGIN_PATTERN = "^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]{4,20}";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";
    private static final String NAME_SURNAME_PATTERN = "[\\p{L}\\s-]{1,50}";
    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";
    private static final String PHONE_PATTERN = "^\\+?\\d{12}";

    private UserValidator() {
    }

    public static boolean checkLogin(String login) {
        boolean isValid = false;
        if (login != null && !login.isEmpty()) {
            isValid = login.matches(LOGIN_PATTERN);
        }
        return isValid;
    }

    public static boolean checkPassword(String password) {
        boolean isValid = false;
        if (password != null && !password.isEmpty()) {
            isValid = password.matches(PASSWORD_PATTERN);
        }
        return isValid;
    }

    public static boolean checkName(String name) {
        boolean isValid = false;
        if (name != null && !name.isEmpty()) {
            isValid = name.matches(NAME_SURNAME_PATTERN);
        }
        return isValid;
    }

    public static boolean checkEmail(String email) {
        boolean isValid = false;
        if (email != null && !email.isEmpty()) {
            isValid = email.matches(EMAIL_PATTERN);
        }
        return isValid;
    }

    public static boolean checkPhone(String phone) {
        boolean isValid = false;
        if (phone != null && !phone.isEmpty()) {
            isValid = phone.matches(PHONE_PATTERN);
        }
        return isValid;
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
        if (!checkName(parameters.get(SURNAME))) {
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
