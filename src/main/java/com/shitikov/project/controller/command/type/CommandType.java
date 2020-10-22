package com.shitikov.project.controller.command.type;

import com.shitikov.project.controller.command.Command;
import com.shitikov.project.controller.command.impl.*;
import com.shitikov.project.controller.command.impl.open.page.*;
import com.shitikov.project.controller.command.impl.page.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    EMPTY_COMMAND(new EmptyCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    ACTIVATE_ACCOUNT(new ActivateAccountCommand()),
    SAVE_USER_SETTINGS(new SaveUserSettingsCommand()),
    SAVE_CONTACT_SETTINGS(new SaveContactSettingsCommand()),
    SAVE_PASSWORD(new SavePasswordCommand()),
    ADD_CAR(new AddCarCommand()),
    //pages
    HOME_PAGE(new HomePageCommand()),
    REGISTRATION_PAGE(new RegistrationPageCommand()),
    LOGIN_PAGE(new LoginPageCommand()),
    ACCOUNT_PAGE(new AccountPageCommand()),
    ADD_CAR_PAGE(new AddCarPageCommand()),
    ADD_APPLICATION_PAGE(new AddApplicationPageCommand());


    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
