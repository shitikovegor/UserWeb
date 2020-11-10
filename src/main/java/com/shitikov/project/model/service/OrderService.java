package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

/**
 * The interface Order service.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface OrderService {
    /**
     * Add boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean add(Map<String, Object> parameters) throws ServiceException;

    /**
     * Remove boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean remove(String id) throws ServiceException;

    /**
     * Find by user map.
     *
     * @param user the user
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Order, Long> findByUser(User user) throws ServiceException;

    /**
     * Check user for app optional.
     *
     * @param user        the user
     * @param application the application
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Car> checkUserForApp(User user, Application application) throws ServiceException;

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
     * Find by app id optional.
     *
     * @param applicationId the application id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Order> findByAppId(String applicationId) throws ServiceException;
}
