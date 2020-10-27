package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class EmptyCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString("path.page.index"));
    }
}
