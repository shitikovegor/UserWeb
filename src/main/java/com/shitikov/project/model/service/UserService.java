package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.RoleType;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean add(String login, String password, RoleType role) throws ServiceException;
    boolean remove(String login);
    boolean removeById(String id);
    Optional<User> findById(String id);
    List<User> findAll();
    boolean checkLogin(String login, String password) throws ServiceException;
    boolean updatePassword(String newPassword);
}
