package com.shitikov.project.model.dao.impl;

import com.shitikov.project.model.dao.CarDao;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CarDaoImpl implements CarDao {

    private static final String SQL_CHECK_BY_CAR_NUMBER = "SELECT car_id FROM cars WHERE car_number = ?";
    private static final String SQL_INSERT_CAR = "INSERT INTO cars SET car_number = ?, carrying_weight = ?" +
            ", carrying_volume = ?, passengers_number = ?, user_id_fk = (SELECT user_id FROM users WHERE login = ?)";

    public CarDaoImpl(){}

    @Override
    public boolean add(Car car, String login) throws DaoException {
        if (car == null) {
            throw new DaoException("Car is null.");
        }
        boolean isCarAdded = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CAR)) {

            statement.setString(1, car.getCarNumber());
            statement.setDouble(2, car.getCarryingWeight());
            statement.setDouble(3, car.getCarryingVolume());
            statement.setInt(4, car.getPassengers());
            statement.setString(5, login);
            int result = statement.executeUpdate();
            isCarAdded = result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
        return isCarAdded;
    }

    @Override
    public boolean remove(Car car) throws DaoException {
        return false;
    }

    @Override
    public List<Car> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Car> findByNumber(String carNumber) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean updateParameters(String carNumber, Map<String, String> parameters) throws DaoException {
        return false;
    }

    @Override
    public boolean checkCarNumber(String carNumber) throws DaoException {
        if (carNumber == null) {
            throw new DaoException("Car number is null.");
        }
        boolean isNumberInBase;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_CAR_NUMBER)) {

            statement.setString(1, carNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                isNumberInBase = resultSet.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isNumberInBase;
    }
}
