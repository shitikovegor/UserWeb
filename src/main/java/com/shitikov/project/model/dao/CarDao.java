package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;

import java.util.List;
import java.util.Map;

/**
 * The interface Car dao.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface CarDao extends BaseDao<Long, Car> {

    /**
     * Find by user list.
     *
     * @param user the user
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Car> findByUser(User user) throws DaoException;

    /**
     * Check car number boolean.
     *
     * @param carNumber the car number
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean checkCarNumber(String carNumber) throws DaoException;

    /**
     * Remove used boolean.
     *
     * @param parseLong the parse long
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean removeUsed(long parseLong) throws DaoException;

    /**
     * Find available by user list.
     *
     * @param user the user
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Car> findAvailableByUser(User user) throws DaoException;

    /**
     * Find max characteristics of cars by user map.
     *
     * @param user the user
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<String, Number> findMaxCharacteristicsByUser(User user) throws DaoException;
}
