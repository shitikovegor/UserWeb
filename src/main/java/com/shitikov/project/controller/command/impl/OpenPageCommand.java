package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class OpenPageCommand implements Command {
    private static final String PARAM_PAGE_NAME = "page";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String page = request.getParameter(PARAM_PAGE_NAME);

        return ConfigurationManager.getProperty(page);
    }
}


