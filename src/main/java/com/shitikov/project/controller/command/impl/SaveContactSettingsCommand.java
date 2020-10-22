package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.model.service.impl.UserServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.shitikov.project.controller.command.AttributeName.*;


public class SaveContactSettingsCommand implements Command {
    private static final String INVALID_VALUE = "-1";
    private static final String EXISTS = "exists";
    private static Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);


    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        String page;
        String streetHomeToChange = request.getParameter(ParameterName.ADDRESS).replaceAll("</?script>", "");
        String cityToChange = request.getParameter(ParameterName.CITY).replaceAll("</?script>", "");
        String phoneToChange = request.getParameter(ParameterName.PHONE).replaceAll("</?script>", "");

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(ParameterName.USER);
            String login = user.getLogin();
            Optional<Address> userAddress = service.findAddress(login);
            String changedStreetHome = "";
            String changedCity = "";

            boolean isPhoneUpdated = false;
            if (!phoneToChange.isEmpty()
                    && Long.compare(Long.parseLong(phoneToChange), user.getPhone()) != 0) {
                isPhoneUpdated = service.updatePhone(user.getLogin(), phoneToChange);
            }

            Map<String, String> parameters = new HashMap<>();
            if (!userAddress.isPresent() && !streetHomeToChange.isEmpty()
                && !cityToChange.isEmpty()) {
                parameters.put(ParameterName.ADDRESS, streetHomeToChange);
                parameters.put(ParameterName.CITY, cityToChange);
                service.addUserAddress(login, parameters);
                changedStreetHome = (parameters.get(ParameterName.ADDRESS) != null) ?
                        parameters.get(ParameterName.ADDRESS) : "";
                changedCity = (parameters.get(ParameterName.CITY) != null) ?
                        parameters.get(ParameterName.CITY) : "";
            } else if (!streetHomeToChange.isEmpty() && !cityToChange.isEmpty()) {
                if (!streetHomeToChange.equals(userAddress.get().getStreetHome())) {
                    parameters.put(ParameterName.ADDRESS, streetHomeToChange);
                }
                if (!cityToChange.equals(userAddress.get().getCity())) {
                    parameters.put(ParameterName.CITY, cityToChange);
                }
                if (!parameters.isEmpty()) {
                    service.updateContactParameters(login, parameters);
                    changedStreetHome = (parameters.get(ParameterName.ADDRESS) != null) ?
                            parameters.get(ParameterName.ADDRESS) : "";
                    changedCity = (parameters.get(ParameterName.CITY) != null) ?
                            parameters.get(ParameterName.CITY) : "";
                }
            }

            if (changedStreetHome.equals(INVALID_VALUE)) {
                request.setAttribute(ADDRESS_INVALID, true);
                request.setAttribute(ParameterName.ADDRESS, userAddress.get().getStreetHome());
            } else if (changedStreetHome.isEmpty() && userAddress.isPresent()) {
                request.setAttribute(ParameterName.ADDRESS, userAddress.get().getStreetHome());
            } else if (!changedStreetHome.isEmpty()){
                request.setAttribute(ParameterName.ADDRESS, changedStreetHome);
                request.setAttribute(ADDRESS_UPDATED, true);
            }

            if (changedCity.equals(INVALID_VALUE)) {
                request.setAttribute(CITY_INVALID, true);
                request.setAttribute(ParameterName.CITY, userAddress.get().getCity());
            } else if (changedCity.isEmpty() && userAddress.isPresent()) {
                request.setAttribute(ParameterName.CITY, userAddress.get().getCity());
            } else if (!changedCity.isEmpty()){
                request.setAttribute(ParameterName.CITY, changedCity);
                request.setAttribute(CITY_UPDATED, true);
            }

            if (isPhoneUpdated) {
                user.setPhone(Long.parseLong(phoneToChange));
                request.setAttribute(PHONE_UPDATED, true);
            }
            request.setAttribute(SHOW_ACCORDION, true);
            page = resourceBundle.getString("path.page.account");
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            page = resourceBundle.getString("path.page.error");
        }
        return page;
    }
}


