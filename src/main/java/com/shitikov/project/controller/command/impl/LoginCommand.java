package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.MessageManager;
import com.shitikov.project.util.ParameterName;
import com.shitikov.project.util.mail.MailSender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;


public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);
    private static final String EMAIL_SUBJECT = "HelpByCar. Reactivate your account";


    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        String page;

        String login = request.getParameter(ParameterName.LOGIN);
        String password = request.getParameter(ParameterName.PASSWORD);

        try {
            if (service.checkLogin(login) && service.checkPassword(login, password)) {
                User user = service.findByLogin(login).get();

                if (user.isActive()) {
                    HttpSession session = request.getSession();
                    session.setAttribute(ParameterName.USER, user);
                    session.setAttribute(ParameterName.ROLE_TYPE, user.getRoleType());
                    request.setAttribute("user", login);
                    page = resourceBundle.getString("path.page.home");
                } else {
                    Properties properties = new Properties();
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/mail.properties");
                    properties.load(inputStream);
                    String emailBody = String.format(ParameterName.EMAIL_BODY, request.getParameter(ParameterName.LOGIN));
                    MailSender sender = new MailSender(user.getEmail()
                            , EMAIL_SUBJECT, emailBody, properties);
                    sender.send();
                    request.setAttribute("isAccountActivated", false);
                    page = resourceBundle.getString("path.page.activation");
                }

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


