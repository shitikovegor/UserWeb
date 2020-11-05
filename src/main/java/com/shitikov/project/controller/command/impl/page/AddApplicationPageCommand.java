package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.ResourceBundle;


public class AddApplicationPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        request.setAttribute(ParameterName.DEPARTURE_DATE, new Date().getTime());
        request.setAttribute(ParameterName.ARRIVAL_DATE, new Date().getTime());
        return new Router(ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString("path.page.add_edit_application"));
    }
}


