package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Address;
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
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.PAGES_PATH;


/**
 * The type Add application page command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class AddApplicationPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(ParameterName.USER);
            UserService userService = UserServiceImpl.getInstance();
            Optional <Address> userAddress = userService.findAddress(user.getLogin());

            if (userAddress.isPresent()) {
                request.setAttribute(ParameterName.DEPARTURE_ADDRESS, userAddress.get().getStreetHome());
                request.setAttribute(ParameterName.DEPARTURE_CITY, userAddress.get().getCity());
            }
            request.setAttribute(ParameterName.DEPARTURE_DATE, new Date().getTime());
            request.setAttribute(ParameterName.ARRIVAL_DATE, new Date().getTime());

            router = new Router(resourceBundle.getString("path.page.add_edit_application"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


