package com.shitikov.project.util;

public class ParameterName {
    public static final String LOCALE = "locale";
    public static final String CURRENT_PAGE = "current_page";
    public static final String USER = "user";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String SUBJECT_TYPE = "subject";
    public static final String ROLE_TYPE = "role";
    public static final String BLOCKED = "blocked";
    public static final String ACTIVE = "active";

    //properties paths
    public static final String PAGES_PATH = "config.pages";

    //user activation address
    public static final String EMAIL_BODY = "http://localhost:8080/controller?command=activate-account&login=%s";

    private ParameterName() {
    }

    // TODO: 14.10.2020 where need to keep parameter names - in util or in controller?
}
