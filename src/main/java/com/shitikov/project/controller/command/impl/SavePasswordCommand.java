package com.shitikov.project.controller.command.impl;

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
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.*;


public class SavePasswordCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);


    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        String page;
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
            page = resourceBundle.getString("path.page.account");

        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            page = resourceBundle.getString("path.page.error");
        }
        return page;
    }
}


