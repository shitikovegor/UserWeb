package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;


/**
 * The type Activate account command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class ActivateAccountCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        UserService service = UserServiceImpl.getInstance();
        Router router;
        String login = request.getParameter(ParameterName.LOGIN);

        try {
            if (service.checkLogin(login)) {
                service.activate(login);
                User user = service.findByLogin(login).get();
                HttpSession session = request.getSession();
                session.setAttribute(ParameterName.USER, user);
                session.setAttribute(ParameterName.ROLE_TYPE, user.getRoleType());
                request.setAttribute(ParameterName.USER, login);
                router = new Router(resourceBundle.getString("path.page.index"));
                logger.log(Level.INFO, "Account {} activated", login);
            } else {
                request.setAttribute(AttributeName.LOGIN_PASSWORD_INVALID, true);
                router = new Router(resourceBundle.getString("path.page.login"));
                logger.log(Level.INFO, "Account {} didn't activate", login);
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


