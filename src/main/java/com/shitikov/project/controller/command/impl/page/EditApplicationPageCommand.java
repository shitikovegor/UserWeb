package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandType;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.CargoApplication;
import com.shitikov.project.model.entity.application.PassengerApplication;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;


public class EditApplicationPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;

        String applicationId = request.getParameter(APPLICATION_ID);

        try {
            Optional<Application> applicationOptional = ApplicationServiceImpl.getInstance().findById(applicationId);
            if (applicationOptional.isPresent()) {
                Application application = applicationOptional.get();
                logger.log(Level.INFO, "Application found successfully.");
                request.setAttribute(APPLICATION_ID, application.getApplicationId());
                request.setAttribute(TITLE, application.getTitle());
                request.setAttribute(APPLICATION_TYPE, application.getApplicationType().getName());
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

                request.setAttribute(AttributeName.EDIT_APPLICATION, true);
                router = new Router(resourceBundle.getString("path.page.add_edit_application"));
            } else {
                logger.log(Level.INFO, "Application didn't find.");
                String page = getRedirectPage(request, CommandType.ACCOUNT_PAGE);
                router = new Router(Router.Type.REDIRECT, page);
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


