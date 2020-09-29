package com.shitikov.project.controller.command.type;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.impl.*;
import com.shitikov.project.controller.command.impl.open.page.HomePageCommand;
import com.shitikov.project.controller.command.impl.open.page.LoginPageCommand;
import com.shitikov.project.controller.command.impl.open.page.RegistrationPageCommand;

public enum CommandType {
    LOGIN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    EMPTY_COMMAND(new EmptyCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    //pages
    HOME_PAGE(new HomePageCommand()),
    REGISTRATION_PAGE(new RegistrationPageCommand()),
    LOGIN_PAGE(new LoginPageCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
