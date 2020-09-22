package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class RegistrationPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String page = ConfigurationManager.getProperty("path.page.registration");

        return page;
    }
}


