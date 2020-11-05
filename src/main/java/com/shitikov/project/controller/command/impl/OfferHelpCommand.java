package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.model.service.OrderService;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.model.service.impl.OrderServiceImpl;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;

public class OfferHelpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) throws IOException, ServletException {
        Router router;
        OrderService orderService = OrderServiceImpl.getInstance();
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        String applicationId = request.getParameter(APPLICATION_ID);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            Optional<Application> application = applicationService.findById(applicationId);

            boolean isAdded = false;
            boolean applicationFound;
            Optional<Car> car = Optional.empty();
            if (applicationFound = application.isPresent()) {
                car = orderService.checkUserForApp(user, application.get());
                if (car.isPresent()) {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put(USER_ID, user.getUserId());
                    parameters.put(CAR_ID, car.get().getCarId());
                    parameters.put(APPLICATION, application.get());
                    isAdded = orderService.add(parameters);
                }
            }
            if (isAdded) {
                logger.log(Level.INFO, "Order added");
                request.setAttribute(PHONE, userService.findPhoneByApplicationId(applicationId));
                request.setAttribute(AttributeName.ORDER_ADDED, true);
                request.setAttribute(CAR_NUMBER, car.get().getCarNumber());
                router = new Router(resourceBundle.getString("path.page.confirm_app"));
            } else if (!applicationFound) {
                logger.log(Level.INFO, "Application not found");
                router = new Router(resourceBundle.getString("path.page.error404"));
            } else {
                logger.log(Level.INFO, "Order not added");
                request.setAttribute(AttributeName.ORDER_ADDED, false);

                router = new Router(resourceBundle.getString("path.page.confirm_app"));
            }
        } catch (ServiceException e) {
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}
