package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.entity.User;

public class UserBuilder {
    private String login;
    private String name;
    private String surname;
    private String email;
    private long phone;
    private RoleType roleType;
    private SubjectType subjectType;
    private boolean blocked = false;

    public String getLogin() {
        return login;
    }

    public UserBuilder buildLogin(String login) {
        this.login = login;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserBuilder buildSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserBuilder buildEmail(String email) {
        this.email = email;
        return this;
    }

    public long getPhone() {
        return phone;
    }

    public UserBuilder buildPhone(long phone) {
        this.phone = phone;
        return this;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public UserBuilder buildSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    public boolean isBlocked() {
        return blocked;
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
