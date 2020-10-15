package com.shitikov.project.controller.command.impl.open.page;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;


public class RegistrationPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString("path.page.registration");
    }
}


