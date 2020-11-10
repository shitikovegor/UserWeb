package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

/**
 * The interface Application service.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface ApplicationService {
    /**
     * Add boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean add(Map<String, String> parameters) throws ServiceException;

    /**
     * Remove boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean remove(String id) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Application> findById(String id) throws ServiceException;

    /**
     * Find by user map.
     *
     * @param user the user
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Application, OrderStatus> findByUser(User user) throws ServiceException;

    /**
     * Find all map.
     *
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Application, OrderStatus> findAll() throws ServiceException;

    /**
     * Find by parameters map.
     *
     * @param parameters the parameters
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Application, OrderStatus> findByParameters(Map<String, String> parameters) throws ServiceException;

    /**
     * Update boolean.
     *
     * @param id         the id
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(String id, Map<String, String> parameters) throws ServiceException;

    /**
     * Find recent active apps map.
     *
     * @param numberOfApps the number of apps
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Application, OrderStatus> findRecentActiveApps(int numberOfApps) throws ServiceException;
}
