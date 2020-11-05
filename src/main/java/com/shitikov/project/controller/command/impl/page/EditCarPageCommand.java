package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandType;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.CarServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;


public class EditCarPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;

        String carId = request.getParameter(CAR_ID);

        try {
            Optional<Car> carOptional = CarServiceImpl.getInstance().findById(carId);
            if (carOptional.isPresent()) {
                Car car = carOptional.get();
                logger.log(Level.INFO, "Car found successfully.");
                request.setAttribute(CAR_ID, car.getCarId());
                request.setAttribute(CAR_NUMBER, car.getCarNumber());
                request.setAttribute(CARRYING_WEIGHT, car.getCarryingWeight());
                request.setAttribute(CARRYING_VOLUME, car.getCarryingVolume());
                request.setAttribute(PASSENGERS_NUMBER, car.getPassengers());
                request.setAttribute(AttributeName.EDIT_CAR, true);
                router = new Router(resourceBundle.getString("path.page.add_edit_car"));
            } else {
                logger.log(Level.INFO, "Car didn't find.");
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


