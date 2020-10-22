package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.CarServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.ADDING_ERROR;
import static com.shitikov.project.util.ParameterName.*;


public class AddCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String EXISTS = "exists";
    private static final String ATTRIBUTE_SUBSTRING_INVALID = "_invalid";
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String weight = request.getParameter(CARRYING_WEIGHT).isEmpty() ? "0.0" : request.getParameter(CARRYING_WEIGHT);
        String volume = request.getParameter(CARRYING_VOLUME).isEmpty() ? "0.0" : request.getParameter(CARRYING_VOLUME);
        String passengers = request.getParameter(PASSENGERS_NUMBER).isEmpty() ? "0"
                : request.getParameter(PASSENGERS_NUMBER);

        Map<String, String> parameters = new HashMap<>();
        parameters.put(CAR_NUMBER, request.getParameter(CAR_NUMBER));
        parameters.put(CARRYING_WEIGHT, weight);
        parameters.put(CARRYING_VOLUME, volume);
        parameters.put(PASSENGERS_NUMBER, passengers);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);


            if (CarServiceImpl.getInstance().add(parameters, user.getLogin())) {
// TODO: 21.10.2020 cars from session and add car to carslist
                logger.log(Level.INFO, "Car added successfully.");
                page = resourceBundle.getString("path.page.account");
            } else {
                request.setAttribute(ADDING_ERROR, true);
                logger.log(Level.INFO, "Car didn't add.");
                page = resourceBundle.getString("path.page.add_car");
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            page = resourceBundle.getString("path.page.error");
        }
        return page;
    }
}


