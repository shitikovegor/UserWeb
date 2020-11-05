package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderDao extends BaseDao<Long, Order> {
    Map<Order, Long> findByUser(User user) throws DaoException;

    List<Order> findAll() throws DaoException;

    Optional<Order> findByAppId(long applicationId) throws DaoException;

    boolean removeByCarId(long carId) throws DaoException;
}
