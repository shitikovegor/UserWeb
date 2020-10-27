package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CarDao extends BaseDao<String, Car> {
    Optional<Car> findByNumber(String carNumber) throws DaoException;

    List<Car> findByUser(User user) throws DaoException;

    boolean removeByCarNumber(String carNumber) throws DaoException;

//    boolean updateParameters(String carNumber, Map<String, String> parameters) throws DaoException;

    boolean checkCarNumber(String carNumber) throws DaoException;

    boolean updateById(long carId, Map<String, String> parameters) throws DaoException;
}
