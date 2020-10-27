package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.MessageManager;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;


public class ActivateAccountCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);


    @Override
    public Router execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        Router router;

        String login = request.getParameter(ParameterName.LOGIN);

        try {
            if (service.checkLogin(login)) {
                service.activate(login);
                User user = service.findByLogin(login).get();
                HttpSession session = request.getSession();
                session.setAttribute(ParameterName.USER, user);
                request.setAttribute("user", login);
                router = new Router(resourceBundle.getString("path.page.home"));
            } else {
                request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("command.login.error"));
                router = new Router(resourceBundle.getString("path.page.login"));
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error"));
        }
        return router;
    }
}


