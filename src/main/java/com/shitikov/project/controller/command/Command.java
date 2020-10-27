package com.shitikov.project.controller.command;

import com.shitikov.project.controller.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Command {
    static final String COMMAND_PARAM = "?command=";

    Router execute(HttpServletRequest request) throws IOException, ServletException;

    default String getRedirectPage(HttpServletRequest request, CommandType commandType) {
        return request.getContextPath() + request.getRequestURI() + COMMAND_PARAM + commandType.name().toLowerCase();
    }
}
