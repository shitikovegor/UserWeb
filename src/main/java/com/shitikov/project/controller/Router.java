package com.shitikov.project.controller;

import com.shitikov.project.util.ParameterName;

import java.util.ResourceBundle;

public class Router {
    private static final String INDEX_PATH = "path.page.index";
    public enum Type {
        FORWARD,
        REDIRECT;
    }
    private Type type;
    private String page;

    public Router() {
        type = Type.FORWARD;
        page = ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString(INDEX_PATH);
    }

    public Router(String page) {
        this.type = Type.FORWARD;
        this.page = page;
    }

    public Router(Type type, String page) {
        this.type = type;
        this.page = page;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
