package com.shitikov.project.controller.command;

import com.shitikov.project.controller.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface Command {
    /**
     * The constant COMMAND_PARAM.
     */
    String COMMAND_PARAM = "?command=";

    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     */
    Router execute(HttpServletRequest request);

    /**
     * Gets redirect page.
     *
     * @param request     the request
     * @param commandType the command type
     * @return the redirect page
     */
    default String getRedirectPage(HttpServletRequest request, CommandType commandType) {
        return request.getContextPath() + request.getRequestURI() + COMMAND_PARAM + commandType.name().toLowerCase();
    }
}
