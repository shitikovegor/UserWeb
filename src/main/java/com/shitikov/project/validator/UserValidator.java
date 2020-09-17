package com.shitikov.project.validator;

public class UserValidator {
    private static UserValidator instance;
    private static final String LOGIN_PATTERN = "^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]{6,10}";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";

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

    public boolean checkLoginPassword(String login, String password) {
        return checkLogin(login) && checkPassword(password);
    }
}
