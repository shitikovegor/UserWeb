package com.shitikov.project.validator;

public class UserValidator {
    private static UserValidator instance;
    private static final String LOGIN_PATTERN = "^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]{4,20}";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";
    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
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

    public boolean checkEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public boolean checkPhone(String phone) {
        return phone.matches(PHONE_PATTERN);
    }

    public boolean checkParameters(String login, String password, String email, String phone) {
        return checkLogin(login) && checkPassword(password) && checkEmail(email) && checkPhone(phone);
    }
}
