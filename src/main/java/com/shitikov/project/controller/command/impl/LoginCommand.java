package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ParameterName;
import com.shitikov.project.util.mail.MailSender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.ACCOUNT_ACTIVATED;
import static com.shitikov.project.controller.command.AttributeName.LOGIN_PASSWORD_INVALID;
import static com.shitikov.project.util.ParameterName.*;


public class LoginCommand implements Command {
    private static final String EMAIL_SUBJECT = "HelpByCar. Reactivate your account";
    private static final String MAIL_PROPERTIES = "config/mail.properties";
    private static Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        UserService service = UserServiceImpl.getInstance();
        Router router;

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        try {
            if (service.checkLogin(login) && service.checkPassword(login, password)) {
                User user = service.findByLogin(login).get();

                if (user.isActive()) {
                    HttpSession session = request.getSession();
                    session.setAttribute(USER, user);
                    session.setAttribute(ROLE_TYPE, user.getRoleType());
                    request.setAttribute(USER, login);
                    router = new Router(resourceBundle.getString("path.page.home"));
                } else {
                    Properties properties = new Properties();
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(MAIL_PROPERTIES);
                    properties.load(inputStream);
                    String emailBody = String.format(EMAIL_BODY,
                            request.getRequestURL(), request.getParameter(LOGIN));
                    MailSender sender = new MailSender(user.getEmail()
                            , EMAIL_SUBJECT, emailBody, properties);
                    sender.send();
                    request.setAttribute(ACCOUNT_ACTIVATED, false);
                    router = new Router(resourceBundle.getString("path.page.activation"));
                }

            } else {
                request.setAttribute(LOGIN_PASSWORD_INVALID, true);
                router = new Router(resourceBundle.getString("path.page.login"));
            }
        } catch (ServiceException | IOException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


