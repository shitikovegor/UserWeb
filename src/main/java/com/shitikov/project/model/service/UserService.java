package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface User service.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface UserService {
    /**
     * Add boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean add(Map<String, String> parameters) throws ServiceException;

    /**
     * Add user address boolean.
     *
     * @param login      the login
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addUserAddress(String login, Map<String, String> parameters) throws ServiceException;

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findByLogin(String login) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findAll() throws ServiceException;

    /**
     * Find role role type.
     *
     * @param login the login
     * @return the role type
     * @throws ServiceException the service exception
     */
    RoleType findRole(String login) throws ServiceException;

    /**
     * Find address optional.
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Address> findAddress(String login) throws ServiceException;

    /**
     * Find phone by application id long.
     *
     * @param applicationId the application id
     * @return the long
     * @throws ServiceException the service exception
     */
    long findPhoneByApplicationId(String applicationId) throws ServiceException;

    /**
     * Check login boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkLogin(String login) throws ServiceException;

    /**
     * Check password boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkPassword(String login, String password) throws ServiceException;

    /**
     * Update boolean.
     *
     * @param login      the login
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(String login, Map<String, String> parameters) throws ServiceException;

    /**
     * Update phone boolean.
     *
     * @param login the login
     * @param phone the phone
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updatePhone(String login, String phone) throws ServiceException;

    /**
     * Update password boolean.
     *
     * @param login       the login
     * @param newPassword the new password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updatePassword(String login, String newPassword) throws ServiceException;

    /**
     * Update contact parameters boolean.
     *
     * @param login      the login
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateContactParameters(String login, Map<String, String> parameters) throws ServiceException;

    /**
     * Activate boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean activate(String login) throws ServiceException;

    /**
     * Block boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean block(String login) throws ServiceException;

    /**
     * Unblock boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean unblock(String login) throws ServiceException;
}
