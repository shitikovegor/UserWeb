package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;


public class AddApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;

        String title = request.getParameter(TITLE).replaceAll("</?script>", "");
        String applicationType = request.getParameter(APPLICATION_TYPE);
        String cargoWeight = request.getParameter(CARGO_WEIGHT);
        String cargoVolume = request.getParameter(CARGO_VOLUME);
        String passengerNumber = request.getParameter(PASSENGERS_NUMBER);
        String departureDate = request.getParameter(DEPARTURE_DATE);
        String departureAddress = request.getParameter(DEPARTURE_ADDRESS).replaceAll("</?script>", "");
        String departureCity = request.getParameter(DEPARTURE_CITY).replaceAll("</?script>", "");
        String arrivalDate = request.getParameter(ARRIVAL_DATE);
        String arrivalAddress = request.getParameter(ARRIVAL_ADDRESS).replaceAll("</?script>", "");
        String arrivalCity = request.getParameter(ARRIVAL_CITY).replaceAll("</?script>", "");
        String description = request.getParameter(DESCRIPTION).replaceAll("</?script>", "").trim();

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

            if (ApplicationServiceImpl.getInstance().add(parameters, user.getLogin())) {
                logger.log(Level.INFO, "Application added successfully.");

                RequestAttributeHandler handler =
                        (RequestAttributeHandler) session.getAttribute(ParameterName.REQUEST_ATTRIBUTE_HANDLER);
                Map<String, Object> attributes = handler.getRequestAttributes();
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                router = new Router(Router.Type.REDIRECT, resourceBundle.getString("path.page.account"));
            } else {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    if (!entry.getValue().isEmpty()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    } else {
                        request.setAttribute(entry.getKey().concat(ATTRIBUTE_SUBSTRING_INVALID), true);
                    }
                }
                router = new Router(resourceBundle.getString("path.page.add_application"));
            }
        } catch (ServiceException e) {
            router = new Router(resourceBundle.getString("path.page.error"));
        }
        return router;
    }
}


