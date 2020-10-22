package com.shitikov.project.model.dao;

import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.exception.DaoException;

import java.util.Map;
import java.util.Optional;

public interface CarDao extends BaseDao<Car> {
    Optional<Car> findByNumber(String carNumber) throws DaoException;

    boolean updateParameters(String carNumber, Map<String, String> parameters) throws DaoException;

    boolean checkCarNumber(String carNumber) throws DaoException;
}
