package com.shitikov.project.controller.command.impl.page;

import com.shitikov.project.controller.Router;
import com.shitikov.project.controller.command.AttributeName;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.model.service.impl.ApplicationServiceImpl;
import com.shitikov.project.util.ParameterName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ApplicationsPageCommand implements Command {
    private static final int OBJECTS_ON_PAGE = 5;
    private static final int FIRST_PAGE = 1;
    private static final Logger logger = LogManager.getLogger();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(ParameterName.PAGES_PATH);

    @Override
    public Router execute(HttpServletRequest request) {
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        try {
            Map<Application, OrderStatus> applications = applicationService.findAll();
            Map<Application, OrderStatus> sorted = applications.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(
                            Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                    LinkedHashMap::new));

            request.setAttribute(ParameterName.APPLICATIONS, sorted);
            request.setAttribute(AttributeName.CURRENT_NUMBER, FIRST_PAGE);
            request.setAttribute(AttributeName.TOTAL_PAGES, Math.ceil((double) sorted.size() / OBJECTS_ON_PAGE));
            request.setAttribute(AttributeName.OBJECTS_ON_PAGE, OBJECTS_ON_PAGE);
            router = new Router(resourceBundle.getString("path.page.applications"));
        } catch (ServiceException e) {
            logger.log(Level.WARN, "Application error. ", e);
            router = new Router(resourceBundle.getString("path.page.error500"));
        }
        return router;
    }
}


