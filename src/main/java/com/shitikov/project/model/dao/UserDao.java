package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.DaoException;

public interface UserDao extends BaseDao<Long, User>{
    boolean checkLogin(String login) throws DaoException;

    RoleType getRole(String login) throws DaoException;

    String getPassword(String login, String password) throws DaoException;

    boolean updatePassword(String newPassword) throws DaoException;
}
