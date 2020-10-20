package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.EMAIL_EXISTS;
import static com.shitikov.project.controller.command.AttributeName.LOGIN_EXISTS;
import static com.shitikov.project.util.ParameterName.*;


public class AddCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String EXISTS = "exists";
    private static final String ATTRIBUTE_SUBSTRING_INVALID = "_invalid";
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        Map<String, String> parameters = new HashMap<>();
        parameters.put(CAR_NUMBER, request.getParameter(CAR_NUMBER));
        if (request.getParameter(CARRYING_WEIGHT) != null) {
            parameters.put(CARRYING_WEIGHT, request.getParameter(CARRYING_WEIGHT));
        }
        if (request.getParameter(CARRYING_VOLUME) != null) {
            parameters.put(CARRYING_VOLUME, request.getParameter(CARRYING_VOLUME));
        }
        if (request.getParameter(PASSENGERS_NUMBER) != null) {
            parameters.put(PASSENGERS_NUMBER, request.getParameter(PASSENGERS_NUMBER));
        }

        try {
            if (UserServiceImpl.getInstance().add(parameters)) {
                request.setAttribute("userAddedMessage", MessageManager.getProperty("command.registration.success")); // TODO: 09.10.2020 page address need be in constant?

                page = resourceBundle.getString("path.page.account");
            } else {
                if (parameters.get(LOGIN).equals(EXISTS)) {
                    request.setAttribute(LOGIN_EXISTS, true);
                    parameters.remove(LOGIN);
                }
                if (parameters.get(EMAIL).equals(EXISTS)) {
                    request.setAttribute(EMAIL_EXISTS, true);
                    parameters.remove(EMAIL);
                }
                parameters.remove(PASSWORD);
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    if (!entry.getValue().isEmpty()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    } else {
                        request.setAttribute(entry.getKey().concat(ATTRIBUTE_SUBSTRING_INVALID), true);
                    }
                }
                page = resourceBundle.getString("path.page.add_car");
            }
        } catch (ServiceException e) {

            page = resourceBundle.getString("path.page.error");
        }
        return page;
    }
}


