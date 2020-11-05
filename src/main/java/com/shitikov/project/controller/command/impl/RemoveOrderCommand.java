package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.OrderService;
import com.shitikov.project.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;


public class RemoveOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String orderId = request.getParameter(ORDER_ID);
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            if (orderService.remove(orderId)) {
                logger.log(Level.INFO, "Order removed successfully.");

                RequestAttributeHandler handler =
                        (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
                Map<String, Object> attributes = handler.getRequestAttributes();
                attributes.replace(ORDERS, orderService.findByUser(user));

                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            } else {
                request.setAttribute(AttributeName.ORDER_REMOVE_ERROR, true);
                logger.log(Level.INFO, "Order didn't remove.");
            }
            router = new Router(resourceBundle.getString("path.page.account"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


