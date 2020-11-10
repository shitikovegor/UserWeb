package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface User dao.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface UserDao extends BaseDao<String, User>{

    /**
     * Add address boolean.
     *
     * @param login      the login
     * @param parameters the parameters
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean addAddress(String login, Map<String, String> parameters) throws DaoException;

    /**
     * Check login boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean checkLogin(String login) throws DaoException;

    /**
     * Check email boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean checkEmail(String email) throws DaoException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findAll() throws DaoException;

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Find password string.
     *
     * @param login the login
     * @return the string
     * @throws DaoException the dao exception
     */
    String findPassword(String login) throws DaoException;

    /**
     * Find role role type.
     *
     * @param login the login
     * @return the role type
     * @throws DaoException the dao exception
     */
    RoleType findRole(String login) throws DaoException;

    /**
     * Find user address optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Address> findUserAddress(String login) throws DaoException;

    /**
     * Find phone by application id long.
     *
     * @param applicationId the application id
     * @return the long
     * @throws DaoException the dao exception
     */
    long findPhoneByApplicationId(long applicationId) throws DaoException;

    /**
     * Update contact parameters boolean.
     *
     * @param login      the login
     * @param parameters the parameters
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateContactParameters(String login, Map<String, String> parameters) throws DaoException;

    /**
     * Update phone boolean.
     *
     * @param login the login
     * @param phone the phone
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updatePhone(String login, long phone) throws DaoException;

    /**
     * Update password boolean.
     *
     * @param login       the login
     * @param newPassword the new password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updatePassword(String login, String newPassword) throws DaoException;

    /**
     * Update activation boolean.
     *
     * @param login  the login
     * @param status the status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateActivation(String login, int status) throws DaoException;

    /**
     * Update block boolean.
     *
     * @param login  the login
     * @param status the status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateBlock(String login, int status) throws DaoException;
}
