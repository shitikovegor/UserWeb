package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandType;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.CarService;
import com.shitikov.project.model.service.impl.CarServiceImpl;
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
 * The type Edit car command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class EditCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);
    private static final String ZERO = "0";

    @Override
    public Router execute(HttpServletRequest request) {
        CarService carService = CarServiceImpl.getInstance();
        Router router;

        String weight =
                request.getParameter(CARRYING_WEIGHT).isEmpty() ? ZERO : request.getParameter(CARRYING_WEIGHT);
        String volume = request.getParameter(CARRYING_VOLUME).isEmpty() ? ZERO : request.getParameter(CARRYING_VOLUME);
        String passengers = request.getParameter(PASSENGERS_NUMBER).isEmpty() ? ZERO : request.getParameter(PASSENGERS_NUMBER);

        boolean areAllZeros = weight.equals(volume)
                && volume.equals(passengers)
                && passengers.equals(ZERO);

        try {
            HttpSession session = request.getSession();
            RequestAttributeHandler handler =
                    (RequestAttributeHandler) session.getAttribute(REQUEST_ATTRIBUTE_HANDLER);
            Map<String, Object> attributes = handler.getRequestAttributes();

            Map<String, String> parameters = new HashMap<>();
            for (String parameterName : CAR_PARAMS) {
                String parameter = request.getParameter(parameterName).replaceAll(XSS_PATTERN, EMPTY_LINE).trim();
                if (parameter != null && !parameter.equals(attributes.get(parameterName))) {
                    parameters.put(parameterName, parameter);
                }
            }
            if (!areAllZeros && carService.update(request.getParameter(CAR_ID), parameters)) {
                logger.log(Level.INFO, "Car edited successfully.");
                String page;
                if (session.getAttribute(ROLE_TYPE) != RoleType.ADMINISTRATOR) {
                    page = getRedirectPage(request, CommandType.ACCOUNT_PAGE);
                } else {
                    page = getRedirectPage(request, CommandType.ACCOUNT_FOR_ADMIN_PAGE);
                }
                router = new Router(Router.Type.REDIRECT, page);
            } else {
                request.setAttribute(ADD_ERROR, true);
                logger.log(Level.INFO, "Car didn't add.");
                String parameter;
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    parameter = parameters.get(entry.getKey());
                    if (entry.getValue() != null && parameter == null) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    } else if (!parameter.isEmpty()) {
                        request.setAttribute(entry.getKey(), parameter);
                    } else {
                        request.setAttribute(entry.getKey().concat(ATTRIBUTE_SUBSTRING_INVALID), true);
                    }
                }
                String page = resourceBundle.getString("path.page.add_edit_car");
                router = new Router(page);
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


