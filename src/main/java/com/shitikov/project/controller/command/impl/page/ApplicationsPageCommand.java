package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.model.service.impl.CarServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


/**
 * The type Applications page command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class ApplicationsPageCommand implements Command {
    private static final int OBJECTS_ON_PAGE = 5;
    private static final int FIRST_PAGE = 1;
    private static final Logger logger = LogManager.getLogger();
    private static final int START_INDEX = 0;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        Router router;
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute(ParameterName.ROLE_TYPE) == RoleType.DRIVER) {
                User user = (User) session.getAttribute(ParameterName.USER);
                List<Car> cars = CarServiceImpl.getInstance().findByUser(user);
                double weightMax = 0;
                double volumeMax = 0;
                int passengerMax = 0;
                for (Car car : cars) {
                    double carWeight = car.getCarryingWeight();
                    double carVolume = car.getCarryingVolume();
                    int carPassenger = car.getPassengers();
                    weightMax = Math.max(carWeight, weightMax);
                    volumeMax = Math.max(carVolume, volumeMax);
                    passengerMax = Math.max(carPassenger, passengerMax);
                }
                request.setAttribute(ParameterName.CARGO_WEIGHT_FROM, START_INDEX);
                request.setAttribute(ParameterName.CARGO_WEIGHT_TO, weightMax);
                request.setAttribute(ParameterName.CARGO_VOLUME_FROM, START_INDEX);
                request.setAttribute(ParameterName.CARGO_VOLUME_TO, volumeMax);
                request.setAttribute(ParameterName.PASSENGER_NUMBER_FROM, START_INDEX);
                request.setAttribute(ParameterName.PASSENGER_NUMBER_TO, passengerMax);
            }

            Map<Application, OrderStatus> applications = applicationService.findAll();
            Map<Application, OrderStatus> sorted = applications.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey(new Application.DepartureDateComparator()))
                    .sorted(Map.Entry.comparingByValue())
                    .collect(
                            Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                    LinkedHashMap::new));

            request.setAttribute(ParameterName.APPLICATIONS, sorted);
            request.setAttribute(AttributeName.CURRENT_NUMBER, FIRST_PAGE);
            request.setAttribute(AttributeName.TOTAL_PAGES, Math.ceil((double) sorted.size() / OBJECTS_ON_PAGE));
            request.setAttribute(AttributeName.OBJECTS_ON_PAGE, OBJECTS_ON_PAGE);
            router = new Router(resourceBundle.getString("path.page.applications"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


