package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.RoleType;
import com.shitikov.project.model.entity.User;

public class UserBuilder {
    private String login;
    private String password;
    private RoleType roleType;

    public String getLogin() {
        return login;
    }

    public UserBuilder buildLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserBuilder buildPassword(String password) {
        this.password = password;
        return this;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public UserBuilder buildRoleType(RoleType roleType) {
        this.roleType = roleType;
        return this;
    }

    public User buildUser() {
        return new User(this);
    }
}
