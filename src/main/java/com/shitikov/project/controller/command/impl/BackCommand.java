package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandType;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shitikov.project.util.ParameterName.PAGES_PATH;

/**
 * The type Back command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class BackCommand implements Command {
    private static final String PAGE_PATTERN = "jsp\\/([a-z-]+)\\.jsp";
    private static final String PAGE_INDICATOR = "_page";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        String previousPage = request.getParameter(ParameterName.PREVIOUS_PAGE);
        String pageForRedirect;
        Pattern pattern = Pattern.compile(PAGE_PATTERN);
        Matcher matcher = pattern.matcher(previousPage);
        if (matcher.find()) {
            RequestAttributeHandler handler =
                    (RequestAttributeHandler) session.getAttribute(AttributeName.PAGE_PARAMETERS);
            if (handler != null) {
                Map<String, Object> attributes = handler.getRequestAttributes();
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                session.removeAttribute(AttributeName.PAGE_PARAMETERS);
                router = new Router(previousPage);
            } else {
                CommandType commandType = CommandType.valueOf(matcher.group(1).concat(PAGE_INDICATOR).toUpperCase());
                pageForRedirect = getRedirectPage(request, commandType);
                router = new Router(Router.Type.REDIRECT, pageForRedirect);
            }
        } else {
            router = new Router(ResourceBundle.getBundle(PAGES_PATH).getString("path.page.error404"));
        }
        return router;
    }
}
