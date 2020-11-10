package com.shitikov.project.controller;

import com.shitikov.project.controller.command.CommandType;

import java.util.EnumSet;
import java.util.Set;

import static com.shitikov.project.controller.command.CommandType.*;

/**
 * The enum Role available command.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public enum RoleAvailableCommand {
    GUEST(EnumSet.of(LOGIN,
            REGISTRATION,
            ACTIVATE_ACCOUNT,
            CHANGE_LANGUAGE,
            SEARCH,
            HOME_PAGE,
            REGISTRATION_PAGE,
            LOGIN_PAGE,
            APPLICATIONS_PAGE,
            APPLICATION_PAGE,
            BACK,
            PAGINATION)),

    CLIENT(EnumSet.of(CHANGE_LANGUAGE,
            SEARCH,
            LOGOUT,
            SAVE_USER_SETTINGS,
            SAVE_CONTACT_SETTINGS,
            SAVE_PASSWORD,
            ADD_APPLICATION,
            EDIT_APPLICATION,
            REMOVE_APPLICATION,
            CANCEL_APPLICATION,
            COMPLETE_APPLICATION,
            HOME_PAGE,
            APPLICATIONS_PAGE,
            APPLICATION_PAGE,
            BACK,
            PAGINATION,
            ACCOUNT_PAGE,
            ADD_APPLICATION_PAGE,
            EDIT_APPLICATION_PAGE)),

    DRIVER(EnumSet.of(CHANGE_LANGUAGE,
            SEARCH,
            LOGOUT,
            SAVE_USER_SETTINGS,
            SAVE_CONTACT_SETTINGS,
            SAVE_PASSWORD,
            ADD_CAR,
            EDIT_CAR,
            REMOVE_CAR,
            OFFER_HELP,
            REMOVE_ORDER,
            HOME_PAGE,
            APPLICATIONS_PAGE,
            APPLICATION_PAGE,
            BACK,
            PAGINATION,
            ACCOUNT_PAGE,
            ADD_CAR_PAGE,
            EDIT_CAR_PAGE)),

    ADMINISTRATOR(EnumSet.of(CHANGE_LANGUAGE,
            SEARCH,
            LOGOUT,
            EDIT_APPLICATION,
            EDIT_CAR,
            REMOVE_CAR,
            REMOVE_APPLICATION,
            CANCEL_APPLICATION,
            COMPLETE_APPLICATION,
            REMOVE_ORDER,
            BLOCK_USER,
            UNBLOCK_USER,
            HOME_PAGE,
            APPLICATIONS_PAGE,
            APPLICATION_PAGE,
            EDIT_APPLICATION_PAGE,
            EDIT_CAR_PAGE,
            BACK,
            PAGINATION,
            ACCOUNT_FOR_ADMIN_PAGE,
            USERS_PAGE));

    private final Set<CommandType> availableCommands;

    RoleAvailableCommand(Set<CommandType> availableCommands) {
        this.availableCommands = availableCommands;
    }

    public Set<CommandType> getAvailableCommands() {
        return availableCommands;
    }
}
