package com.shitikov.project.controller;

import com.shitikov.project.util.ParameterName;

import java.util.ResourceBundle;

/**
 * The type Router.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class Router {
    private static final String INDEX_PATH = "path.page.index";

    /**
     * The enum Type.
     *
     * @author Shitikov Egor
     * @version 1.0
     */
    public enum Type {
        /**
         * Forward type.
         */
        FORWARD,
        /**
         * Redirect type.
         */
        REDIRECT
    }
    private Type type;
    private String page;

    /**
     * Instantiates a new Router.
     */
    public Router() {
        type = Type.FORWARD;
        page = ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString(INDEX_PATH);
    }

    /**
     * Instantiates a new Router.
     *
     * @param page the page
     */
    public Router(String page) {
        this.type = Type.FORWARD;
        this.page = page;
    }

    /**
     * Instantiates a new Router.
     *
     * @param type the type
     * @param page the page
     */
    public Router(Type type, String page) {
        this.type = type;
        this.page = page;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(String page) {
        this.page = page;
    }
}
