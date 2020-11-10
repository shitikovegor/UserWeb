package com.shitikov.project.controller.command.impl.page;

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
import java.util.List;
import java.util.ResourceBundle;


/**
 * The type Users page command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class UsersPageCommand implements Command {
    private static final int OBJECTS_ON_PAGE = 10;
    private static final int FIRST_PAGE = 1;
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        Router router;
        try {
            List<User> users = userService.findAll();

            request.setAttribute(ParameterName.USERS, users);
            request.setAttribute(AttributeName.CURRENT_NUMBER, FIRST_PAGE);
            request.setAttribute(AttributeName.TOTAL_PAGES, Math.ceil((double) users.size() / OBJECTS_ON_PAGE));
            request.setAttribute(AttributeName.OBJECTS_ON_PAGE, OBJECTS_ON_PAGE);
            router = new Router(resourceBundle.getString("path.page.users"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


