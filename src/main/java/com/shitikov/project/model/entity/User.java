package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.UserBuilder;

public class User extends Entity {
    private String login;
    private String password;
    private RoleType roleType;

    public User(String login, String password, RoleType roleType) {
        this.login = login;
        this.password = password;
        this.roleType = roleType;
    }

    public User(UserBuilder builder) {
        this.login = builder.getLogin();
        this.password = builder.getPassword();
        this.roleType = builder.getRoleType();
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public User setRoleType(RoleType roleType) {
        this.roleType = roleType;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        User other = (User) obj;

        if (login != null ? !login.equals(other.login) : other.login != null)
            return false;
        if (password != null ? !password.equals(other.password) : other.password != null)
            return false;
        return roleType == other.roleType;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", roleType=").append(roleType);
        sb.append('}');
        return sb.toString();
    }
}
