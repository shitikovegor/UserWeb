package com.shitikov.project.controller;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandProvider;
import com.shitikov.project.model.pool.ConnectionPool;
import com.shitikov.project.util.ParameterName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
//        String page = command.execute(request);
        Router router = command.execute(request);
        String page = router.getPage();
        HttpSession session = request.getSession();
        RequestAttributeHandler handler = new RequestAttributeHandler();
        handler.setRequestAttributes(request);
        session.setAttribute(ParameterName.REQUEST_ATTRIBUTE_HANDLER, handler);

        if (router.getType() == Router.Type.FORWARD) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(page);
        }

//        if (page != null) {
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//            dispatcher.forward(request, response);
//        } else {
//            request.getSession().setAttribute("nullPage", "Page is null");
//            response.sendRedirect(request.getContextPath()
//                    + ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString("path.page.home"));
//        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.INSTANCE.destroyPool();
    }
}


