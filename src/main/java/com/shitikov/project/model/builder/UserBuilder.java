package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.entity.User;

/**
 * The type User builder.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class UserBuilder {
    private long userId;
    private String login;
    private String name;
    private String surname;
    private String email;
    private long phone;
    private RoleType roleType;
    private SubjectType subjectType;
    private boolean blocked = false;
    private boolean activated = false;

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Build user id user builder.
     *
     * @param userId the user id
     * @return the user builder
     */
    public UserBuilder buildUserId(long userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Build login user builder.
     *
     * @param login the login
     * @return the user builder
     */
    public UserBuilder buildLogin(String login) {
        this.login = login;
        return this;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Build name user builder.
     *
     * @param name the name
     * @return the user builder
     */
    public UserBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Build surname user builder.
     *
     * @param surname the surname
     * @return the user builder
     */
    public UserBuilder buildSurname(String surname) {
        this.surname = surname;
        return this;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Build email user builder.
     *
     * @param email the email
     * @return the user builder
     */
    public UserBuilder buildEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public long getPhone() {
        return phone;
    }

    /**
     * Build phone user builder.
     *
     * @param phone the phone
     * @return the user builder
     */
    public UserBuilder buildPhone(long phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Gets subject type.
     *
     * @return the subject type
     */
    public SubjectType getSubjectType() {
        return subjectType;
    }

    /**
     * Build subject type user builder.
     *
     * @param subjectType the subject type
     * @return the user builder
     */
    public UserBuilder buildSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    /**
     * Is blocked boolean.
     *
     * @return the boolean
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Build blocked user builder.
     *
     * @param blocked the blocked
     * @return the user builder
     */
    public UserBuilder buildBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    /**
     * Is activated boolean.
     *
     * @return the boolean
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Build activated user builder.
     *
     * @param active the active
     * @return the user builder
     */
    public UserBuilder buildActivated(boolean active) {
        this.activated = active;
        return this;
    }

    /**
     * Gets role type.
     *
     * @return the role type
     */
    public RoleType getRoleType() {
        return roleType;
    }

    /**
     * Build role type user builder.
     *
     * @param roleType the role type
     * @return the user builder
     */
    public UserBuilder buildRoleType(RoleType roleType) {
        this.roleType = roleType;
        return this;
    }

    /**
     * Build user user.
     *
     * @return the user
     */
    public User buildUser() {
        return new User(this);
    }
}
