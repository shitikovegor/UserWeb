package com.shitikov.project.controller.command.impl;

import com.shitikov.project.controller.RequestAttributeHandler;
import com.shitikov.project.controller.Router;
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
    public Router execute(HttpServletRequest request) throws IOException, ServletException {
        UserService service = UserServiceImpl.getInstance();
        Router router;
        String streetHomeToChange = request.getParameter(ParameterName.ADDRESS).replaceAll("</?script>", "");
        String cityToChange = request.getParameter(ParameterName.CITY).replaceAll("</?script>", "");
        String phoneToChange = request.getParameter(ParameterName.PHONE).replaceAll("</?script>", "");

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(ParameterName.USER);
            String login = user.getLogin();

            RequestAttributeHandler handler =
                    (RequestAttributeHandler) session.getAttribute(ParameterName.REQUEST_ATTRIBUTE_HANDLER);
            Map<String, Object> attributes = handler.getRequestAttributes();

            Optional<Address> userAddress = service.findAddress(login);
            String changedStreetHome = "";
            String changedCity = "";
            boolean isAddressUpdated = false;
            boolean isPhoneUpdated = false;
            if (!phoneToChange.isEmpty() && Long.parseLong(phoneToChange) != user.getPhone()) {
                isPhoneUpdated = service.updatePhone(user.getLogin(), phoneToChange);
            }

            Map<String, String> parameters = new HashMap<>();
            if (!userAddress.isPresent() && !streetHomeToChange.isEmpty() && !cityToChange.isEmpty()) {
                parameters.put(ParameterName.ADDRESS, streetHomeToChange);
                parameters.put(ParameterName.CITY, cityToChange);
                isAddressUpdated = service.addUserAddress(login, parameters);
                if (isAddressUpdated) {
                    request.setAttribute(CONTACT_UPDATED, true);
                    attributes.put(ParameterName.ADDRESS, streetHomeToChange);
                    attributes.put(ParameterName.CITY, cityToChange);
                    logger.log(Level.INFO, "Contact updated");
                } else {
                    request.setAttribute(DATA_INVALID, true);
                }
            } else if (userAddress.isPresent()) {
                Address userAddressPr = userAddress.get();
                if (!streetHomeToChange.equals(userAddressPr.getStreetHome()) && !streetHomeToChange.isEmpty()) {
                    parameters.put(ParameterName.ADDRESS, streetHomeToChange);
                }
                if (!cityToChange.equals(userAddressPr.getCity()) && !cityToChange.isEmpty()) {
                    parameters.put(ParameterName.CITY, cityToChange);
                }
                if (!parameters.isEmpty()) {
                    isAddressUpdated = service.updateContactParameters(login, parameters);
                }

                if (isAddressUpdated) {
                    request.setAttribute(CONTACT_UPDATED, true);
                    attributes.replace(ParameterName.ADDRESS, streetHomeToChange);
                    attributes.replace(ParameterName.CITY, cityToChange);
                    logger.log(Level.INFO, "Contact updated");
                } else {
                    if (parameters.get(ParameterName.ADDRESS) != null && parameters.get(ParameterName.ADDRESS).equals("")) {
                        request.setAttribute(ADDRESS_INVALID, true);
                    }
                    if (parameters.get(ParameterName.CITY) != null && parameters.get(ParameterName.CITY).equals("")) {
                        request.setAttribute(CITY_INVALID, true);
                    }
//                    request.setAttribute(ParameterName.ADDRESS, userAddressPr.getStreetHome());
//                    request.setAttribute(ParameterName.CITY, userAddressPr.getCity());
                }
            } else if (streetHomeToChange.isEmpty() || cityToChange.isEmpty()) {
                request.setAttribute(DATA_INVALID, true);
            }
            if (isPhoneUpdated) {
                user.setPhone(Long.parseLong(phoneToChange));
                request.setAttribute(PHONE_UPDATED, true);
            }
            request.setAttribute(SHOW_ACCORDION, true);

            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            router = new Router(resourceBundle.getString("path.page.account"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error"));
        }
        return router;
    }
}


