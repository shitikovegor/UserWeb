package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.RoleType;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean add(String login, String password, RoleType role) throws ServiceException;
    boolean remove(String login) throws ServiceException;
    boolean removeById(String id) throws ServiceException;
    Optional<User> findById(String id) throws ServiceException;
    List<User> findAll() throws ServiceException;
    boolean checkLogin(String login, String password) throws ServiceException;
    boolean updatePassword(String newPassword) throws ServiceException;
}
