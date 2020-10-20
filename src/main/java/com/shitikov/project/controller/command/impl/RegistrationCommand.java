package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.MessageManager;
import com.shitikov.project.util.ParameterName;
import com.shitikov.project.util.mail.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.*;


public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String EXISTS = "exists";
    private static final String ATTRIBUTE_SUBSTRING_INVALID = "_invalid";
    private static final String EMAIL_SUBJECT = "HelpByCar. Activate your account"; // TODO: 09.10.2020 from properties
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        Map<String, String> parameters = new HashMap<>();
        parameters.put(ParameterName.LOGIN, request.getParameter(ParameterName.LOGIN));
        parameters.put(ParameterName.PASSWORD, request.getParameter(ParameterName.PASSWORD));
        parameters.put(ParameterName.NAME, request.getParameter(ParameterName.NAME));
        parameters.put(ParameterName.SURNAME, request.getParameter(ParameterName.SURNAME));
        parameters.put(ParameterName.EMAIL, request.getParameter(ParameterName.EMAIL));
        parameters.put(ParameterName.PHONE, request.getParameter(ParameterName.PHONE));
        parameters.put(ParameterName.SUBJECT_TYPE, request.getParameter(ParameterName.SUBJECT_TYPE));
        parameters.put(ParameterName.ROLE_TYPE, request.getParameter(ParameterName.ROLE_TYPE));

        try {
            if (UserServiceImpl.getInstance().add(parameters)) {
                request.setAttribute("userAddedMessage", MessageManager.getProperty("command.registration.success")); // TODO: 09.10.2020 page address need be in constant?

                Properties properties = new Properties();
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/mail.properties");
                properties.load(inputStream);
                String emailBody = String.format(ParameterName.EMAIL_BODY,
                        request.getRequestURL(), request.getParameter(ParameterName.LOGIN));
                MailSender sender = new MailSender(request.getParameter(ParameterName.EMAIL)
                        , EMAIL_SUBJECT, emailBody, properties);
                sender.send();
                page = resourceBundle.getString("path.page.activation");
            } else {
                if (parameters.get(ParameterName.LOGIN).equals(EXISTS)) {
                    request.setAttribute(LOGIN_EXISTS, true);
                    parameters.remove(ParameterName.LOGIN);
                }
                if (parameters.get(ParameterName.EMAIL).equals(EXISTS)) {
                    request.setAttribute(EMAIL_EXISTS, true);
                    parameters.remove(ParameterName.EMAIL);
                }
                parameters.remove(ParameterName.PASSWORD);
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    if (!entry.getValue().isEmpty()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    } else {
                        request.setAttribute(entry.getKey().concat(ATTRIBUTE_SUBSTRING_INVALID), true);
                    }
                }
                page = resourceBundle.getString("path.page.registration");
            }
        } catch (ServiceException | IOException e) {

            page = resourceBundle.getString("path.page.error");
        }
        return page;
    }
}


