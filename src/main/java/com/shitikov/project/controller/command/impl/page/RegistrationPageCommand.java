package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;


/**
 * The type Registration page command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class RegistrationPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(ResourceBundle.getBundle(ParameterName.PAGES_PATH).getString("path.page.registration"));
    }
}


