package com.shitikov.project.controller.command.impl.open.page;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class AccountPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public String execute(HttpServletRequest request) {
        UserService service = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterName.USER);
        String page;

        Optional<Address> userAddressOpt;
        try {
            userAddressOpt = service.findAddress(user.getLogin());
            if (userAddressOpt.isPresent()) {
                Address userAddress = userAddressOpt.get();
                request.setAttribute(ParameterName.ADDRESS, userAddress.getStreetHome());
                request.setAttribute(ParameterName.CITY, userAddress.getCity());
            }

        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            page = resourceBundle.getString("path.page.error");
        }

        request.setAttribute("cars", new ArrayList<Car>());
// TODO: 14.10.2020 do other fields

        page = resourceBundle.getString("path.page.account");
        return page;
    }
}


