package com.shitikov.project.model.entity;

import com.shitikov.project.model.builder.UserBuilder;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;

public class User extends Entity {
    private String login;
    private String name;
    private String surname;
    private String email;
    private long phone;
    private RoleType roleType;
    private SubjectType subjectType;
    private boolean blocked;

    public User(String login, String name, String surname, String email,
                long phone, RoleType roleType, SubjectType subjectType) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.roleType = roleType;
        this.subjectType = subjectType;
        this.blocked = false;
    }

    public User(UserBuilder builder) {
        this.login = builder.getLogin();
        this.name = builder.getName();
        this.surname = builder.getSurname();
        this.email = builder.getEmail();
        this.phone = builder.getPhone();
        this.roleType = builder.getRoleType();
        this.subjectType = builder.getSubjectType();
        this.blocked = builder.isBlocked();
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public long getPhone() {
        return phone;
    }

    public User setPhone(long phone) {
        this.phone = phone;
        return this;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public User setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public User setBlocked(boolean blocked) {
        this.blocked = blocked;
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
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User other = (User) obj;

        if (phone != other.phone) {
            return false;
        }
        if (blocked != other.blocked) {
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
        int result = login != null ? login.hashCode() : 0;
        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (surname != null ? surname.hashCode() : 0);
        result = prime * result + (email != null ? email.hashCode() : 0);
        result = prime * result + (int) (phone ^ (phone >>> 32));
        result = prime * result + (roleType != null ? roleType.hashCode() : 0);
        result = prime * result + (subjectType != null ? subjectType.hashCode() : 0);
        result = prime * result + (blocked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("login='").append(login).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone=").append(phone);
        sb.append(", roleType=").append(roleType);
        sb.append(", subjectType=").append(subjectType);
        sb.append(", blocked=").append(blocked);
        sb.append('}');
        return sb.toString();
    }
}
