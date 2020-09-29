package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;


public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.config");


    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        String page = null;

        String login = request.getParameter(ParameterName.LOGIN);
        String password = request.getParameter(ParameterName.PASSWORD);

        try {
            if (service.checkLogin(login, password)) {
                HttpSession session = request.getSession();
                session.setAttribute(ParameterName.LOGIN, login);
                session.setAttribute(ParameterName.ROLE_TYPE, service.getRole(login));
                request.setAttribute("user", login);
                page = resourceBundle.getString("path.page.home");
            } else {
                request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("command.login.error"));
                page = resourceBundle.getString("path.page.login");
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            page = resourceBundle.getString("path.page.error");
        }
        return page;
    }
}


