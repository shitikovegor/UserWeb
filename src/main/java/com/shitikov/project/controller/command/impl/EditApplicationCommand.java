package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandType;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.shitikov.project.util.ParameterName.*;


public class EditApplicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        Router router;

        try {
            HttpSession session = request.getSession();
            RequestAttributeHandler handler =
                    (RequestAttributeHandler) session.getAttribute(ParameterName.REQUEST_ATTRIBUTE_HANDLER);
            Map<String, Object> attributes = handler.getRequestAttributes();

            Map<String, String> parameters = new HashMap<>();
            for (String parameterName : APPLICATION_PARAMS) {
                Object attribute;
                if (attributes.get(parameterName) instanceof Long) {
                    LocalDate localDate =
                            Instant.ofEpochMilli((Long) attributes.get(parameterName)).atZone(ZoneId.systemDefault()).toLocalDate();
                    attribute = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else {
                    attribute = attributes.get(parameterName);
                }
                String parameter = request.getParameter(parameterName).replaceAll("</?script>", "").trim();
                if (parameter != null && attribute != null
                        && !parameter.equals(attribute.toString())) {
                    parameters.put(parameterName, parameter);
                }
            }

            if (applicationService.update(request.getParameter(APPLICATION_ID), parameters)) {
                logger.log(Level.INFO, "Application edited successfully.");

                String page = getRedirectPage(request, CommandType.ACCOUNT_PAGE);
                router = new Router(Router.Type.REDIRECT, page);
            } else {
                request.setAttribute(AttributeName.ADD_ERROR, true);
                logger.log(Level.INFO, "Application didn't add.");
                String parameter;
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    parameter = parameters.get(entry.getKey());
                    if (entry.getValue() != null && parameter == null) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    } else if (!parameter.isEmpty()) {
                        request.setAttribute(entry.getKey(), parameter);
                    } else {
                        request.setAttribute(entry.getKey().concat(ATTRIBUTE_SUBSTRING_INVALID), true);
                    }
                }
                String page = resourceBundle.getString("path.page.add_edit_application");
                router = new Router(page);
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            router = new Router(resourceBundle.getString("path.page.error"));
        }
        return router;
    }
    // TODO: 28.10.2020 repeat in EditCarCommand
}


