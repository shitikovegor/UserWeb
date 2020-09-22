package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.RoleType;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ConfigurationManager;
import com.shitikov.project.util.MessageManager;

import javax.servlet.http.HttpServletRequest;


public class RegistrationCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        try {
            if (UserServiceImpl.getInstance().add(login, password, RoleType.USER)) {
                request.setAttribute("userAddedMessage", MessageManager.getProperty("command.registration.success"));
                page = ConfigurationManager.getProperty("path.page.login");
            } else {
                request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("command.registration.error"));
                page = ConfigurationManager.getProperty("path.page.registration");
            }
        } catch (ServiceException e) {
            // TODO: 01.09.2020 log + error message
            page = ConfigurationManager.getProperty("path.page.error");
        }
        return page;
    }
}


