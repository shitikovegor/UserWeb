package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * The type Block user command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class BlockUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);


    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        Router router;

        String login = request.getParameter(ParameterName.LOGIN);

        try {
            if (userService.checkLogin(login)) {
                userService.block(login);
                logger.log(Level.INFO, "User {} blocked", login);
                HttpSession session = request.getSession();

                RequestAttributeHandler handler =
                        (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);

                Map<String, Object> attributes = handler.getRequestAttributes();
                attributes.replace(ParameterName.USERS, userService.findAll());
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }

                router = new Router(resourceBundle.getString("path.page.users"));
            } else {
                logger.log(Level.INFO, "User {} didn't find", login);
                router = new Router(resourceBundle.getString("path.page.error404"));
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


