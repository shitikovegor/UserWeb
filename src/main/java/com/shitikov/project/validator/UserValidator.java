package com.shitikov.project.validator;

import java.util.Map;

import static com.shitikov.project.util.ParameterName.*;

public class UserValidator {
    private static UserValidator instance;
//    public static final String LOGIN = "login";
//    public static final String PASSWORD = "password";
//    public static final String NAME = "name";
//    public static final String SURNAME = "surname";
//    public static final String EMAIL = "email";
//    public static final String PHONE = "phone";
// TODO: 09.10.2020 Can use parameterName or create new fields?
    private static final String LOGIN_PATTERN = "^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]{4,20}";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";
    private static final String NAME_SURNAME_PATTERN = "[\\p{L}\\s-]{1,50}";
    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";
    private static final String PHONE_PATTERN = "^\\+?\\d{12}";

    private UserValidator() {
    }

    public static UserValidator getInstance() {
        if (instance == null) {
            instance = new UserValidator();
        }
        return instance;
    }

    public boolean checkLogin(String login) {
        return login.matches(LOGIN_PATTERN);
    }

    public boolean checkPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public boolean checkParameter(String parameter, String pattern) {
        return parameter.matches(pattern);
    }

    public boolean checkParameters(Map<String, String> parameters) {
        boolean areParametersCorrect = true;

        if (!checkLogin(parameters.get(LOGIN))) {
            areParametersCorrect = false;
            parameters.replace(LOGIN, "");
        }
        if (!checkPassword(parameters.get(PASSWORD))) {
            areParametersCorrect = false;
            parameters.replace(PASSWORD, "");
        }
        if (!checkParameter(parameters.get(NAME), NAME_SURNAME_PATTERN)) {
            areParametersCorrect = false;
            parameters.replace(NAME, "");
        }
        if (!checkParameter(parameters.get(SURNAME), NAME_SURNAME_PATTERN)) {
            areParametersCorrect = false;
            parameters.replace(SURNAME, "");
        }
        if (!checkParameter(parameters.get(EMAIL), EMAIL_PATTERN)) {
            areParametersCorrect = false;
            parameters.replace(EMAIL, "");
        }
        if (!checkParameter(parameters.get(PHONE), PHONE_PATTERN)) {
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
