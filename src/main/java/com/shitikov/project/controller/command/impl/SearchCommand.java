package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.shitikov.project.util.ParameterName.*;


/**
 * The type Search command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class SearchCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);
    private static final int OBJECTS_ON_PAGE = 5;
    private static final int FIRST_PAGE = 1;

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        try {
            HttpSession session = request.getSession();
            Map<String, String> searchParameters = new HashMap<>();
            searchParameters.put(CARGO, request.getParameter(CARGO));
            searchParameters.put(PASSENGER, request.getParameter(PASSENGER));
            searchParameters.put(DEPARTURE_DATE_FROM, request.getParameter(DEPARTURE_DATE_FROM));
            searchParameters.put(DEPARTURE_DATE_TO, request.getParameter(DEPARTURE_DATE_TO));
            searchParameters.put(PASSENGER_NUMBER_FROM, request.getParameter(PASSENGER_NUMBER_FROM));
            searchParameters.put(PASSENGER_NUMBER_TO, request.getParameter(PASSENGER_NUMBER_TO));
            searchParameters.put(CARGO_WEIGHT_FROM, request.getParameter(CARGO_WEIGHT_FROM));
            searchParameters.put(CARGO_WEIGHT_TO, request.getParameter(CARGO_WEIGHT_TO));
            searchParameters.put(CARGO_VOLUME_FROM, request.getParameter(CARGO_VOLUME_FROM));
            searchParameters.put(CARGO_VOLUME_TO, request.getParameter(CARGO_VOLUME_TO));
            searchParameters.put(CITY, request.getParameter(CITY));
            searchParameters.put(ACTIVE, request.getParameter(ACTIVE));
            searchParameters.put(CONFIRMED, request.getParameter(CONFIRMED));
            searchParameters.put(COMPLETED, request.getParameter(COMPLETED));
            searchParameters.put(CANCELED, request.getParameter(CANCELED));

            Map<Application, OrderStatus> applications = ApplicationServiceImpl.getInstance().findByParameters(searchParameters);
            Map<Application, OrderStatus> sorted = applications.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(
                            Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                    LinkedHashMap::new));

            logger.log(Level.INFO, "Applications found: {}", sorted);
            RequestAttributeHandler handler =
                    (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
            Map<String, Object> attributes = handler.getRequestAttributes();
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String> parameter : searchParameters.entrySet()) {
                request.setAttribute(parameter.getKey(), parameter.getValue());
            }

            request.setAttribute(APPLICATIONS, sorted);
            request.setAttribute(AttributeName.CURRENT_NUMBER, FIRST_PAGE);
            request.setAttribute(AttributeName.TOTAL_PAGES, Math.ceil((double) applications.size() / OBJECTS_ON_PAGE));
            request.setAttribute(AttributeName.OBJECTS_ON_PAGE, OBJECTS_ON_PAGE);
            router = new Router(resourceBundle.getString("path.page.applications"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }

        return router;
    }
}


