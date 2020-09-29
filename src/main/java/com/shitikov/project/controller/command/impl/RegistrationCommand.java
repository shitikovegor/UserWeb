package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class RegistrationCommand implements Command {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.config");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        Map<String, String> parameters = new HashMap<>();
        parameters.put(ParameterName.LOGIN, request.getParameter(ParameterName.LOGIN));
        parameters.put(ParameterName.PASSWORD, request.getParameter(ParameterName.PASSWORD));
        parameters.put(ParameterName.NAME, request.getParameter(ParameterName.NAME));
        parameters.put(ParameterName.SURNAME, request.getParameter(ParameterName.SURNAME));
        parameters.put(ParameterName.EMAIL, request.getParameter(ParameterName.EMAIL));
        parameters.put(ParameterName.PHONE, request.getParameter(ParameterName.PHONE));
        parameters.put(ParameterName.SUBJECT_TYPE, request.getParameter(ParameterName.SUBJECT_TYPE));
        parameters.put(ParameterName.ROLE_TYPE, request.getParameter(ParameterName.ROLE_TYPE));

        try {
            if (UserServiceImpl.getInstance().add(parameters)) {
                request.setAttribute("userAddedMessage", MessageManager.getProperty("command.registration.success"));
                page = resourceBundle.getString("path.page.login");
            } else {
                request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("command.registration.error"));
                page = resourceBundle.getString("path.page.registration");
            }
        } catch (ServiceException e) {
            // TODO: 01.09.2020 log + error message
            page = resourceBundle.getString("path.page.error");
        }
        return page;
    }
}


