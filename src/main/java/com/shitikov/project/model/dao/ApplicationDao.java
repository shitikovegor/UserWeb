package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.DaoException;

import java.util.Map;

/**
 * The interface Application dao.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface ApplicationDao extends BaseDao<Long, Application> {

    /**
     * Find by user map.
     *
     * @param user the user
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<Application, OrderStatus> findByUser(User user) throws DaoException;

    /**
     * Find all map.
     *
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<Application, OrderStatus> findAll() throws DaoException;

    /**
     * Find by parameters map.
     *
     * @param validParameters the valid parameters
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<Application, OrderStatus> findByParameters(Map<String, Object> validParameters) throws DaoException;

    /**
     * Find recent active apps map.
     *
     * @param number the number
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<Application, OrderStatus> findRecentActiveApps(int number) throws DaoException;
}
