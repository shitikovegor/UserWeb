package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;


public class RemoveApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;

        String applicationId = request.getParameter(APPLICATION_ID);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            if (!ApplicationServiceImpl.getInstance().remove(applicationId)) {
                request.setAttribute(AttributeName.APP_REMOVE_ERROR, true);
                logger.log(Level.INFO, "Application didn't remove.");
            }
            RequestAttributeHandler handler =
                    (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
            Map<String, Object> attributes = handler.getRequestAttributes();
            attributes.replace(APPLICATIONS, ApplicationServiceImpl.getInstance().findByUser(user));

            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            router = new Router(resourceBundle.getString("path.page.account"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


