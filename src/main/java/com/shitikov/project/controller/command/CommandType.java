package com.shitikov.project.controller.command;

import com.shitikov.project.controller.command.impl.*;
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
    EDIT_CAR(new EditCarCommand()),
    REMOVE_CAR(new RemoveCarCommand()),
    ADD_APPLICATION(new AddApplicationCommand()),
    EDIT_APPLICATION(new EditApplicationCommand()),
    REMOVE_APPLICATION(new RemoveApplicationCommand()),
    CANCEL_APPLICATION(new CancelApplicationCommand()),
    COMPLETE_APPLICATION(new CompleteApplicationCommand()),
    OFFER_HELP(new OfferHelpCommand()),
    REMOVE_ORDER(new RemoveOrderCommand()),
    SEARCH(new SearchCommand()),
    //pages
    HOME_PAGE(new HomePageCommand()),
    REGISTRATION_PAGE(new RegistrationPageCommand()),
    LOGIN_PAGE(new LoginPageCommand()),
    ACCOUNT_PAGE(new AccountPageCommand()),
    ADD_CAR_PAGE(new AddCarPageCommand()),
    EDIT_CAR_PAGE(new EditCarPageCommand()),
    ADD_APPLICATION_PAGE(new AddApplicationPageCommand()),
    EDIT_APPLICATION_PAGE(new EditApplicationPageCommand()),
    APPLICATIONS_PAGE(new ApplicationsPageCommand()),
    APPLICATION_PAGE(new ApplicationPageCommand()),
    BACK(new BackCommand()),
    PAGINATION(new PaginationCommand());


    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
