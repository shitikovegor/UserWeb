package com.shitikov.project.controller.command.impl.open.page;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AccountPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);

        request.setAttribute("cars", new ArrayList<Car>());
// TODO: 14.10.2020 do other fields

        String page = resourceBundle.getString("path.page.account");
        return page;
    }
}


