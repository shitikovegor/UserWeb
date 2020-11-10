package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;

import java.util.Map;
import java.util.Optional;

/**
 * The interface Order dao.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public interface OrderDao extends BaseDao<Long, Order> {
    /**
     * Find by user map.
     *
     * @param user the user
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<Order, Long> findByUser(User user) throws DaoException;

    /**
     * Find by app id optional.
     *
     * @param applicationId the application id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Order> findByAppId(long applicationId) throws DaoException;
}
