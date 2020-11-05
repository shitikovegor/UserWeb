package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;


public class LogoutCommand implements Command {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new Router(resourceBundle.getString("path.page.index"));
    }
}


