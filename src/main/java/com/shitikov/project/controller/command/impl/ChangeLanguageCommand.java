package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String language = request.getParameter(ParameterName.LOCALE);
        HttpSession session = request.getSession();
        session.setAttribute(ParameterName.LOCALE, language);
        RequestAttributeHandler handler =
                (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
        Map<String, Object> attributes = handler.getRequestAttributes();

        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        return new Router(session.getAttribute(ParameterName.CURRENT_PAGE).toString());
    }
}
