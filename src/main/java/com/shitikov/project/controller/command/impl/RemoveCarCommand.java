package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.CarService;
import com.shitikov.project.model.service.impl.CarServiceImpl;
import com.shitikov.project.model.service.impl.OrderServiceImpl;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import static com.shitikov.project.util.ParameterName.*;


/**
 * The type Remove car command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class RemoveCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        CarService carService = CarServiceImpl.getInstance();
        String carId = request.getParameter(CAR_ID);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            boolean isAdministrator = false;
            if (user.getRoleType() == RoleType.ADMINISTRATOR) {
                isAdministrator = true;
                user = UserServiceImpl.getInstance().findByLogin((String) session.getAttribute(LOGIN)).get();
            }

            Map<Order, Long> orders = OrderServiceImpl.getInstance().findByUser(user);

            boolean hasUncompletedOrders = false;
            Set<Order> ordersSet = orders.keySet();
            Iterator<Order> iterator = ordersSet.iterator();
            while (!hasUncompletedOrders && iterator.hasNext()) {
                Order order = iterator.next();
                if (order.getStatus() == OrderStatus.CONFIRMED) {
                    hasUncompletedOrders = true;
                }
            }

            if (orders.isEmpty()) {
                if (!carService.remove(carId)) {
                    request.setAttribute(AttributeName.CAR_REMOVE_ERROR, true);
                    logger.log(Level.INFO, "Car {} didn't remove.", carId);
                }
            } else if (hasUncompletedOrders){
                request.setAttribute(AttributeName.HAVE_ORDERS, true);
                logger.log(Level.INFO, "Car {} didn't remove. User has orders to complete.", carId);
            } else {
                carService.removeUsed(carId);
                logger.log(Level.INFO, "Car {} removed.", carId);
            }

            RequestAttributeHandler handler =
                    (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
            Map<String, Object> attributes = handler.getRequestAttributes();
            attributes.replace(CARS, CarServiceImpl.getInstance().findByUser(user));

            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            router = isAdministrator ? new Router(resourceBundle.getString("path.page.account_for_admin")) :
                    new Router(resourceBundle.getString("path.page.account"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


