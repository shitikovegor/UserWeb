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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.*;


public class SaveUserSettingsCommand implements Command {
    private static final String INVALID_VALUE = "-1";
    private static final String EXISTS = "exists";
    private static Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);


    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        String page;
        String loginToChange = request.getParameter(ParameterName.LOGIN);
        String nameToChange = request.getParameter(ParameterName.NAME);
        String surnameToChange = request.getParameter(ParameterName.SURNAME);
        String emailToChange = request.getParameter(ParameterName.EMAIL);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(ParameterName.USER);
            Map<String, String> parameters
                    = checkParameters(loginToChange, nameToChange, surnameToChange, emailToChange, user);
            service.updateParameters(user.getLogin(), parameters);

            if (parameters.get(ParameterName.LOGIN) != null) {
                if (parameters.get(ParameterName.LOGIN).equals(INVALID_VALUE)) {
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
                if (parameters.get(ParameterName.NAME).equals(INVALID_VALUE)) {
                    request.setAttribute(NAME_INVALID, true);
                    parameters.remove(ParameterName.NAME);
                } else {
                    user.setName(nameToChange);
                    request.setAttribute(NAME_UPDATED, true);
                }
            }
            if (parameters.get(ParameterName.SURNAME) != null) {
                if (parameters.get(ParameterName.SURNAME).equals(INVALID_VALUE)) {
                    request.setAttribute(SURNAME_INVALID, true);
                    parameters.remove(ParameterName.SURNAME);
                } else {
                    user.setSurname(surnameToChange);
                    request.setAttribute(SURNAME_UPDATED, true);
                }
            }
            if (parameters.get(ParameterName.EMAIL) != null) {
                if (parameters.get(ParameterName.EMAIL).equals(INVALID_VALUE)) {
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
            page = resourceBundle.getString("path.page.account");

        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            page = resourceBundle.getString("path.page.error");
        }
        return page;
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


