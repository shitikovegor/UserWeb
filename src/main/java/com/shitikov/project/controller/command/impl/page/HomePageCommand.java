package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;


public class HomePageCommand implements Command {
    private static final String DEFAULT_LOCALE = "ru_RU";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.isNew()) {
            session.setAttribute(ParameterName.LOCALE, DEFAULT_LOCALE);
            session.setAttribute(ParameterName.ROLE_TYPE, RoleType.GUEST);
        }
        return new Router(ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString("path.page.home"));
    }
}


