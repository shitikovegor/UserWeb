package com.shitikov.project.controller.command.impl;

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
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        String page;

        String login = request.getParameter(ParameterName.LOGIN);

        try {
            if (service.checkLogin(login)) {
                service.activate(login);
                User user = service.findByLogin(login).get();
                HttpSession session = request.getSession();
                session.setAttribute(ParameterName.USER, user);
                session.setAttribute(ParameterName.ROLE_TYPE, service.findRole(login).getRoleName());
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


