package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.model.service.CarService;
import com.shitikov.project.model.service.OrderService;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.model.service.impl.CarServiceImpl;
import com.shitikov.project.model.service.impl.OrderServiceImpl;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


/**
 * The type Account page command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class AccountPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        CarService carService = CarServiceImpl.getInstance();
        OrderService orderService = OrderServiceImpl.getInstance();
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);
        Router router;

        Optional<Address> userAddressOpt;
        try {
            userAddressOpt = userService.findAddress(user.getLogin());
            if (userAddressOpt.isPresent()) {
                Address userAddress = userAddressOpt.get();
                request.setAttribute(ParameterName.ADDRESS, userAddress.getStreetHome());
                request.setAttribute(ParameterName.CITY, userAddress.getCity());
            }
            if (user.getRoleType() == RoleType.DRIVER) {
                request.setAttribute(ParameterName.ORDERS, orderService.findByUser(user));
                request.setAttribute(ParameterName.CARS, carService.findAvailableByUser(user));
            } else {
                Map<Application, OrderStatus> applications = applicationService.findByUser(user);
                Map<Application, OrderStatus> sorted = applications.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey(new Application.IdComparator()))
                        .sorted(Map.Entry.comparingByValue())
                        .collect(
                                Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                        LinkedHashMap::new));
                request.setAttribute(ParameterName.APPLICATIONS, sorted);
            }
            router = new Router(resourceBundle.getString("path.page.account"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


