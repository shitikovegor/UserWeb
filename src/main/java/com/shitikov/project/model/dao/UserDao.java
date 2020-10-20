package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.DaoException;

import java.util.Map;
import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    boolean addAddress(String login, Map<String, String> parameters) throws DaoException;

    boolean checkLogin(String login) throws DaoException;

    boolean checkEmail(String email) throws DaoException;

    boolean checkUserAddress(String login) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    String findPassword(String login) throws DaoException;

    RoleType findRole(String login) throws DaoException;

    Optional<Address> findUserAddress(String login) throws DaoException;

    boolean updateParameters(String login, Map<String, String> parameters) throws DaoException;

    boolean updateContactParameters(String login, Map<String, String> parameters) throws DaoException;

    boolean updatePhone(String login, long phone) throws DaoException;

    boolean updatePassword(String login, String newPassword) throws DaoException;

    boolean activate(String login) throws DaoException;


}
