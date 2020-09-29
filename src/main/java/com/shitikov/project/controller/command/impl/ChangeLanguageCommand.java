package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String page = ResourceBundle.getBundle("properties.config").getString("path.page.home");
        String language = request.getParameter("locale");
        HttpSession session = request.getSession();
        session.setAttribute(ParameterName.LOCALE, language);
        return session.getAttribute(ParameterName.CURRENT_PAGE).toString(); // TODO: 28.09.2020 page from filter
    }
}
