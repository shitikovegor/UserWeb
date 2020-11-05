package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;

import java.util.List;

public interface CarDao extends BaseDao<Long, Car> {

    List<Car> findAll() throws DaoException;

    List<Car> findByUser(User user) throws DaoException;

    boolean removeByCarNumber(String carNumber) throws DaoException;

    boolean checkCarNumber(String carNumber) throws DaoException;
}
