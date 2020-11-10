package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.PAGES_PATH;


/**
 * The type Home page command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class HomePageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final int NUMBER_OF_RECENT_APPS = 3;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        try {
            ApplicationService service = ApplicationServiceImpl.getInstance();
            Map<Application, OrderStatus> applications = service.findRecentActiveApps(NUMBER_OF_RECENT_APPS);
            request.setAttribute(ParameterName.APPLICATIONS, applications);

            HttpSession session = request.getSession();
            if (session.isNew()) {
                session.setAttribute(ParameterName.LOCALE, DEFAULT_LOCALE);
                session.setAttribute(ParameterName.ROLE_TYPE, RoleType.GUEST);
            }
            router = new Router(resourceBundle.getString("path.page.home"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


