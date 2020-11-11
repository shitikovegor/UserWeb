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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.*;


/**
 * The type Save user settings command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class SaveUserSettingsCommand implements Command {
    private static final String EXISTS = "exists";
    private static final Logger logger = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        UserService service = UserServiceImpl.getInstance();
        Router router;
        String loginToChange = request.getParameter(ParameterName.LOGIN);
        String nameToChange = request.getParameter(ParameterName.NAME);
        String surnameToChange = request.getParameter(ParameterName.SURNAME);
        String emailToChange = request.getParameter(ParameterName.EMAIL);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(ParameterName.USER);
            Map<String, String> parameters
                    = checkParameters(loginToChange, nameToChange, surnameToChange, emailToChange, user);
            service.update(user.getLogin(), parameters);

            if (parameters.get(ParameterName.LOGIN) != null) {
                if (parameters.get(ParameterName.LOGIN).isEmpty()) {
                    request.setAttribute(LOGIN_INVALID, true);
                    parameters.remove(ParameterName.LOGIN);
                } else if (parameters.get(ParameterName.LOGIN).equals(EXISTS)) {
                    request.setAttribute(LOGIN_EXISTS, true);
                    parameters.remove(ParameterName.LOGIN);
                } else {
                    user.setLogin(loginToChange);
                    request.setAttribute(LOGIN_UPDATED, true);
                }
            }
            if (parameters.get(ParameterName.NAME) != null) {
                if (parameters.get(ParameterName.NAME).isEmpty()) {
                    request.setAttribute(NAME_INVALID, true);
                    parameters.remove(ParameterName.NAME);
                } else {
                    user.setName(nameToChange);
                    request.setAttribute(NAME_UPDATED, true);
                }
            }
            if (parameters.get(ParameterName.SURNAME) != null) {
                if (parameters.get(ParameterName.SURNAME).isEmpty()) {
                    request.setAttribute(SURNAME_INVALID, true);
                    parameters.remove(ParameterName.SURNAME);
                } else {
                    user.setSurname(surnameToChange);
                    request.setAttribute(SURNAME_UPDATED, true);
                }
            }
            if (parameters.get(ParameterName.EMAIL) != null) {
                if (parameters.get(ParameterName.EMAIL).isEmpty()) {
                    request.setAttribute(EMAIL_INVALID, true);
                    parameters.remove(ParameterName.EMAIL);
                } else if (parameters.get(ParameterName.EMAIL).equals(EXISTS)) {
                    request.setAttribute(EMAIL_EXISTS, true);
                    parameters.remove(ParameterName.EMAIL);
                } else {
                    user.setEmail(emailToChange);
                    request.setAttribute(EMAIL_UPDATED, true);
                }
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

    private Map<String, String> checkParameters
            (String loginToChange, String nameToChange, String surnameToChange, String emailToChange, User user) {

        Map<String, String> parameters = new HashMap<>();
        if (!loginToChange.equals(user.getLogin())) {
            parameters.put(ParameterName.LOGIN, loginToChange);
        }
        if (!nameToChange.equals(user.getName())) {
            parameters.put(ParameterName.NAME, nameToChange);
        }
        if (!surnameToChange.equals(user.getSurname())) {
            parameters.put(ParameterName.SURNAME, surnameToChange);
        }
        if (!emailToChange.equals(user.getEmail())) {
            parameters.put(ParameterName.EMAIL, emailToChange);
        }
        return parameters;
    }
}


