package com.shitikov.project.controller.command;

import com.shitikov.project.controller.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String COMMAND_PARAM = "?command=";

    Router execute(HttpServletRequest request);

    default String getRedirectPage(HttpServletRequest request, CommandType commandType) {
        return request.getContextPath() + request.getRequestURI() + COMMAND_PARAM + commandType.name().toLowerCase();
    }
}
