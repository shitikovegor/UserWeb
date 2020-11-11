package com.shitikov.project.controller.filter;

import com.shitikov.project.controller.RolePermission;
import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.CommandProvider;
import com.shitikov.project.controller.command.CommandType;
import com.shitikov.project.controller.command.impl.EmptyCommand;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.util.ParameterName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static com.shitikov.project.model.entity.type.RoleType.GUEST;

/**
 * The type Role security filter.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
@WebFilter(urlPatterns = {"/controller"})
public class RoleSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        RoleType role = (RoleType) session.getAttribute(ParameterName.ROLE_TYPE);
        String commandName = httpRequest.getParameter(ParameterName.COMMAND);
        Command command = CommandProvider.defineCommand(httpRequest);

        if (role == null) {
            role = GUEST;
        }

        if (command.getClass() != EmptyCommand.class) {
            Set<CommandType> commandTypeSet = switch (role) {
                case ADMINISTRATOR -> RolePermission.ADMINISTRATOR.getAvailableCommands();
                case DRIVER -> RolePermission.DRIVER.getAvailableCommands();
                case CLIENT -> RolePermission.CLIENT.getAvailableCommands();
                case GUEST -> RolePermission.GUEST.getAvailableCommands();
            };
            if (!commandTypeSet.contains(CommandType.valueOf(commandName.replace("-","_").toUpperCase()))) {
                httpResponse.sendError(403);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
