package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandType;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.*;
import static com.shitikov.project.util.ParameterName.*;


/**
 * The type Add application command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class AddApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;

        String title = request.getParameter(TITLE).replaceAll(XSS_PATTERN, EMPTY_LINE);
        String applicationType = request.getParameter(APPLICATION_TYPE);
        String cargoWeight = request.getParameter(CARGO_WEIGHT);
        String cargoVolume = request.getParameter(CARGO_VOLUME);
        String passengerNumber = request.getParameter(PASSENGERS_NUMBER);
        String departureDate = request.getParameter(DEPARTURE_DATE);
        String departureAddress = request.getParameter(DEPARTURE_ADDRESS).replaceAll(XSS_PATTERN, EMPTY_LINE);
        String departureCity = request.getParameter(DEPARTURE_CITY).replaceAll(XSS_PATTERN, EMPTY_LINE);
        String arrivalDate = request.getParameter(ARRIVAL_DATE);
        String arrivalAddress = request.getParameter(ARRIVAL_ADDRESS).replaceAll(XSS_PATTERN, EMPTY_LINE);
        String arrivalCity = request.getParameter(ARRIVAL_CITY).replaceAll(XSS_PATTERN, EMPTY_LINE);
        String description = request.getParameter(DESCRIPTION).replaceAll(XSS_PATTERN, EMPTY_LINE).trim();

        Map<String, String> parameters = new HashMap<>();
        parameters.put(TITLE, title);
        parameters.put(APPLICATION_TYPE, applicationType);
        parameters.put(CARGO_WEIGHT, cargoWeight);
        parameters.put(CARGO_VOLUME, cargoVolume);
        parameters.put(PASSENGERS_NUMBER, passengerNumber);
        parameters.put(DEPARTURE_DATE, departureDate);
        parameters.put(DEPARTURE_ADDRESS, departureAddress);
        parameters.put(DEPARTURE_CITY, departureCity);
        parameters.put(ARRIVAL_DATE, arrivalDate);
        parameters.put(ARRIVAL_ADDRESS, arrivalAddress);
        parameters.put(ARRIVAL_CITY, arrivalCity);
        parameters.put(DESCRIPTION, description);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            parameters.put(LOGIN, user.getLogin());

            if (ApplicationServiceImpl.getInstance().add(parameters)) {
                logger.log(Level.INFO, "Application added successfully.");

                RequestAttributeHandler handler =
                        (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
                Map<String, Object> attributes = handler.getRequestAttributes();
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                String page = getRedirectPage(request, CommandType.ACCOUNT_PAGE);
                router = new Router(Router.Type.REDIRECT, page);
            } else {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    if (!entry.getValue().isEmpty()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    } else {
                        request.setAttribute(entry.getKey().concat(AttributeName.ATTRIBUTE_SUBSTRING_INVALID), true);
                    }
                }
                router = new Router(resourceBundle.getString("path.page.add_edit_application"));
            }
        } catch (ServiceException e) {
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


