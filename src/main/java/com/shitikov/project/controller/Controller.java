package com.shitikov.project.controller;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.provider.CommandProvider;
import com.shitikov.project.util.ParameterName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Command command = CommandProvider.defineCommand(request);
        String page = command.execute(request);

        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            request.getSession().setAttribute("nullPage", "Page is null");
            response.sendRedirect(request.getContextPath()
                    + ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString("path.page.home"));
        }
    }
}


