package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandType;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.CargoApplication;
import com.shitikov.project.model.entity.application.PassengerApplication;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;


public class ApplicationPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String applicationId = request.getParameter(APPLICATION_ID);
        String currentPage = String.valueOf(session.getAttribute(CURRENT_PAGE));
        OrderStatus status = OrderStatus.valueOf(request.getParameter(STATUS));
        Router router;
        try {
            Optional<Application> applicationOptional = ApplicationServiceImpl.getInstance().findById(applicationId);
            if (applicationOptional.isPresent()) {
                Application application = applicationOptional.get();
                logger.log(Level.INFO, "Application found successfully.");
                request.setAttribute(APPLICATION_ID, application.getApplicationId());
                request.setAttribute(TITLE, application.getTitle());
                request.setAttribute(DATE, application.getDate());
                request.setAttribute(APPLICATION_TYPE, application.getApplicationType());
                request.setAttribute(DEPARTURE_DATE, application.getAddressTimeData().getDepartureDate());
                request.setAttribute(DEPARTURE_ADDRESS, application.getAddressTimeData().getDepartureAddress().getStreetHome());
                request.setAttribute(DEPARTURE_CITY, application.getAddressTimeData().getDepartureAddress().getCity());
                request.setAttribute(ARRIVAL_DATE, application.getAddressTimeData().getArrivalDate());
                request.setAttribute(ARRIVAL_ADDRESS, application.getAddressTimeData().getArrivalAddress().getStreetHome());
                request.setAttribute(ARRIVAL_CITY, application.getAddressTimeData().getArrivalAddress().getCity());
                request.setAttribute(DESCRIPTION, application.getDescription());
                if (application instanceof CargoApplication) {
                    request.setAttribute(CARGO_WEIGHT, ((CargoApplication) application).getCargoWeight());
                    request.setAttribute(CARGO_VOLUME, ((CargoApplication) application).getCargoVolume());
                } else {
                    request.setAttribute(PASSENGERS_NUMBER, ((PassengerApplication) application).getPassengersNumber());
                }
                RequestAttributeHandler handler =
                        (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
                session.setAttribute(AttributeName.PAGE_PARAMETERS, handler);

                request.setAttribute(PREVIOUS_PAGE, currentPage);
                request.setAttribute(STATUS, status);
                router = new Router(resourceBundle.getString("path.page.application"));
            } else {
                logger.log(Level.INFO, "Application didn't find.");
                String page = getRedirectPage(request, CommandType.APPLICATIONS_PAGE);
                router = new Router(Router.Type.REDIRECT, page);
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


