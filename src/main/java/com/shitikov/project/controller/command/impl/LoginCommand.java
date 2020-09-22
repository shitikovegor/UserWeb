package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String ERROR_MESSAGE = "Login or password is incorrect.";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String page = null;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        try {
            if (UserServiceImpl.getInstance().checkLogin(login, password)) {
                request.setAttribute("user", login);
                page = ConfigurationManager.getProperty("path.page.home");
            } else {
                request.setAttribute("errorLoginPassMessage", ERROR_MESSAGE);
                page = ConfigurationManager.getProperty("path.page.login");
            }
        } catch (ServiceException e) {
            // TODO: 01.09.2020 log + error message
            page = ConfigurationManager.getProperty("path.page.error");
        }
        return page;
    }
}


