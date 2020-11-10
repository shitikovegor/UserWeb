package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.UserBuilder;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;

/**
 * The type User.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class User extends Entity {
    private long userId;
    private String login;
    private String name;
    private String surname;
    private String email;
    private long phone;
    private RoleType roleType;
    private SubjectType subjectType;
    private boolean blocked;
    private boolean activated;

    /**
     * Instantiates a new User.
     *
     * @param userId      the user id
     * @param login       the login
     * @param name        the name
     * @param surname     the surname
     * @param email       the email
     * @param phone       the phone
     * @param roleType    the role type
     * @param subjectType the subject type
     */
    public User(long userId, String login, String name, String surname, String email,
                long phone, RoleType roleType, SubjectType subjectType) {
        this.userId = userId;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.roleType = roleType;
        this.subjectType = subjectType;
        this.blocked = false;
        this.activated = false;
    }

    /**
     * Instantiates a new User.
     *
     * @param builder the builder
     */
    public User(UserBuilder builder) {
        this.userId = builder.getUserId();
        this.login = builder.getLogin();
        this.name = builder.getName();
        this.surname = builder.getSurname();
        this.email = builder.getEmail();
        this.phone = builder.getPhone();
        this.roleType = builder.getRoleType();
        this.subjectType = builder.getSubjectType();
        this.blocked = builder.isBlocked();
        this.activated = builder.isActivated();
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
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
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
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
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(long phone) {
        this.phone = phone;
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
     * Sets role type.
     *
     * @param roleType the role type
     */
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
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
     * Sets subject type.
     *
     * @param subjectType the subject type
     */
    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
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
     * Sets blocked.
     *
     * @param blocked the blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
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
     * Sets activated.
     *
     * @param activated the activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (userId != other.userId) {
            return false;
        }
        if (phone != other.phone) {
            return false;
        }
        if (blocked != other.blocked) {
            return false;
        }
        if (activated != other.activated) {
            return false;
        }
        if (login != null ? !login.equals(other.login) : other.login != null) {
            return false;
        }
        if (name != null ? !name.equals(other.name) : other.name != null) {
            return false;
        }
        if (surname != null ? !surname.equals(other.surname) : other.surname != null) {
            return false;
        }
        if (email != null ? !email.equals(other.email) : other.email != null) {
            return false;
        }
        if (roleType != other.roleType) {
            return false;
        }
        return subjectType == other.subjectType;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = (int) (userId ^ (userId >>> 32));
        result = prime * result + (login != null ? login.hashCode() : 0);
        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (surname != null ? surname.hashCode() : 0);
        result = prime * result + (email != null ? email.hashCode() : 0);
        result = prime * result + (int) (phone ^ (phone >>> 32));
        result = prime * result + (roleType != null ? roleType.hashCode() : 0);
        result = prime * result + (subjectType != null ? subjectType.hashCode() : 0);
        result = prime * result + (blocked ? 1 : 0);
        result = prime * result + (activated ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId='").append(userId).append('\'');
        sb.append("login='").append(login).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone=").append(phone);
        sb.append(", roleType=").append(roleType);
        sb.append(", subjectType=").append(subjectType);
        sb.append(", blocked=").append(blocked);
        sb.append(", active=").append(activated);
        sb.append('}');
        return sb.toString();
    }
}
