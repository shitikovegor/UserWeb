package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.OrderService;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;


/**
 * The type Complete application command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class CompleteApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        try {
            String applicationId = request.getParameter(APPLICATION_ID);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            Optional<Order> order = orderService.findByAppId(applicationId);
            if (order.isPresent()) {
                Map<String, String> parameters = new HashMap<>();
                parameters.put(STATUS, OrderStatus.COMPLETED.getName());
                String orderId = String.valueOf(order.get().getOrderId());

                if (!orderService.update(orderId, parameters)) {
                    request.setAttribute(AttributeName.APP_COMPLETE_ERROR, true);
                    logger.log(Level.INFO, "Order {} didn't complete.", order);
                }
                RequestAttributeHandler handler =
                        (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
                Map<String, Object> attributes = handler.getRequestAttributes();
                attributes.replace(APPLICATIONS, ApplicationServiceImpl.getInstance().findByUser(user));

                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                router = new Router(resourceBundle.getString("path.page.account"));
            } else {
                logger.log(Level.INFO, "Order {} not found", order);
                router = new Router(resourceBundle.getString("path.page.error404"));
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


