package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.*;


public class SavePasswordCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);


    @Override
    public Router execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        Router router;
        String password = request.getParameter(ParameterName.PASSWORD);
        String newPassword = request.getParameter(ParameterName.NEW_PASSWORD);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(ParameterName.USER);

            if (service.checkPassword(user.getLogin(), password)) {
                if (!service.updatePassword(user.getLogin(), newPassword)) {
                    request.setAttribute(NEW_PASSWORD_INVALID, true);
                    logger.log(Level.DEBUG, "New password is invalid.");
                }
                request.setAttribute(PASSWORD_UPDATED, true);
                logger.log(Level.DEBUG, "Password updated.");
            } else {
                request.setAttribute(PASSWORD_INVALID, true);
                logger.log(Level.DEBUG, "Password is invalid.");
            }
            request.setAttribute(SHOW_ACCORDION, true);

            RequestAttributeHandler handler =
                    (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
            Map<String, Object> attributes = handler.getRequestAttributes();
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            router = new Router(resourceBundle.getString("path.page.account"));

        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


