package com.shitikov.project.controller.command.type;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    REGISTRATION_PAGE(new RegistrationPageCommand()),
    LOGIN_PAGE(new LoginPageCommand()),
    OPEN_PAGE(new OpenPageCommand()),
    EMPTY_COMMAND(new EmptyCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
