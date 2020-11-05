package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean add(Map<String, String> parameters) throws ServiceException;

    boolean addUserAddress(String login, Map<String, String> parameters) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    List<User> findAll() throws ServiceException;

    RoleType findRole(String login) throws ServiceException;

    Optional<Address> findAddress(String login) throws ServiceException;

    long findPhoneByApplicationId(String applicationId) throws ServiceException;

    boolean checkLogin(String login) throws ServiceException;

    boolean checkPassword(String login, String password) throws ServiceException;

    boolean checkUserAddress(String login) throws ServiceException;

    boolean update(String login, Map<String, String> parameters) throws ServiceException;

    boolean updatePhone(String login, String phone) throws ServiceException;

    boolean updatePassword(String login, String newPassword) throws ServiceException;

    boolean updateContactParameters(String login, Map<String, String> parameters) throws ServiceException;

    boolean activate(String login) throws ServiceException;
}
