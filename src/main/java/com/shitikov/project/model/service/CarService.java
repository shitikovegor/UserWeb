package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Car service.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface CarService {
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
     * @param carNumber the car number
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Car> findById(String carNumber) throws ServiceException;

    /**
     * Find by user list.
     *
     * @param user the user
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Car> findByUser(User user) throws ServiceException;

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
     * Remove used boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeUsed(String id) throws ServiceException;

    /**
     * Find available by user list.
     *
     * @param user the user
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Car> findAvailableByUser(User user) throws ServiceException;

    /**
     * Find max characteristics of cars by user map.
     *
     * @param user the user
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<String, Number> findMaxCharacteristicsByUser(User user) throws ServiceException;
}
