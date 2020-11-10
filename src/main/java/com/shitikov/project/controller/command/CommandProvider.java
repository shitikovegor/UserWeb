package com.shitikov.project.controller.command;

import com.shitikov.project.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

import static com.shitikov.project.controller.command.CommandType.EMPTY_COMMAND;


/**
 * The type Command provider.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public final class CommandProvider {
    private static final char UNDERSCORE = '_';
    private static final char HYPHEN = '-';

    private CommandProvider() {
    }

    /**
     * Define command command.
     *
     * @param request the request
     * @return the command
     */
    public static Command defineCommand(HttpServletRequest request) {
        Command definedCommand;
        String commandName = request.getParameter(ParameterName.COMMAND);



        Optional<Command> command = Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.toString()
                        .equalsIgnoreCase(commandName.replace(HYPHEN,UNDERSCORE).toUpperCase()))
                .findFirst()
                .map(CommandType::getCommand);

        definedCommand = command.orElseGet(EMPTY_COMMAND::getCommand);

        return definedCommand;
    }
}