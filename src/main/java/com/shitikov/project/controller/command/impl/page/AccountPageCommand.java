package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.model.service.CarService;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.model.service.impl.CarServiceImpl;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.ResourceBundle;


public class AccountPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        CarService carService = CarServiceImpl.getInstance();
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);
        Router router;

        Optional<Address> userAddressOpt;
        try {
            userAddressOpt = userService.findAddress(user.getLogin());
            if (userAddressOpt.isPresent()) {
                Address userAddress = userAddressOpt.get();
                request.setAttribute(ParameterName.ADDRESS, userAddress.getStreetHome());
                request.setAttribute(ParameterName.CITY, userAddress.getCity());
            }
            if (user.getRoleType() == RoleType.DRIVER) {
                request.setAttribute("cars", carService.findByUser(user));
            } else {
                request.setAttribute("applications", applicationService.findByUser(user));
            }

            router = new Router(resourceBundle.getString("path.page.account"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error"));
        }




        return router;
    }
}


