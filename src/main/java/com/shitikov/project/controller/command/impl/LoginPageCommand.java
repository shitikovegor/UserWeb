package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class LoginPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String page = ConfigurationManager.getProperty("path.page.login");

        return page;
    }
}


