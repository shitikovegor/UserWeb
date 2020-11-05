package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface OrderService {
    boolean add(Map<String, Object> parameters) throws ServiceException;

    boolean remove(String id) throws ServiceException;

    Optional<Order> findById(String id) throws ServiceException;

    Map<Order, Long> findByUser(User user) throws ServiceException;

    Optional<Car> checkUserForApp(User user, Application application) throws ServiceException;

    boolean update(String id, Map<String, String> parameters) throws ServiceException;

    Optional<Order> findByAppId(String applicationId) throws ServiceException;
}
