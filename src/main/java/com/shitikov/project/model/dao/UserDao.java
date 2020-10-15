package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<Long, User>{
    boolean checkLogin(String login) throws DaoException;

    String findPassword(String login, String password) throws DaoException;

    RoleType findRole(String login) throws DaoException;

    boolean updatePassword(String newPassword) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    boolean isUserInBase(User user) throws DaoException;

    boolean isEmailInBase(String email) throws DaoException;

    boolean activate(String login) throws DaoException;
}
