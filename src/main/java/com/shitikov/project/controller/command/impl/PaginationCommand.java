package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * The type Pagination command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class PaginationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;

        HttpSession session = request.getSession();
        RequestAttributeHandler handler =
                (RequestAttributeHandler) session.getAttribute(AttributeName.REQUEST_ATTRIBUTE_HANDLER);
        Map<String, Object> attributes = handler.getRequestAttributes();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        String currentNumber = request.getParameter(AttributeName.CURRENT_NUMBER);
        request.setAttribute(AttributeName.CURRENT_NUMBER, currentNumber);
        router = new Router((String) session.getAttribute(ParameterName.CURRENT_PAGE));

        return router;
    }
}


