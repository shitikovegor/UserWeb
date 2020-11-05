package com.shitikov.project.model.service;

import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface ApplicationService {
    boolean add(Map<String, String> parameters) throws ServiceException;

    boolean remove(String id) throws ServiceException;

    Optional<Application> findById(String id) throws ServiceException;

    Map<Application, OrderStatus> findByUser(User user) throws ServiceException;

    Map<Application, OrderStatus> findAll() throws ServiceException;

    Map<Application, OrderStatus> findByParameters(Map<String, String> parameters) throws ServiceException;

    boolean update(String id, Map<String, String> parameters) throws ServiceException;
}
