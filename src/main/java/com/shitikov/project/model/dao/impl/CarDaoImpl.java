package com.shitikov.project.model.dao.impl;

import com.shitikov.project.model.builder.CarBuilder;
import com.shitikov.project.model.dao.CarDao;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.pool.ConnectionPool;
import com.shitikov.project.util.ParameterName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CarDaoImpl implements CarDao {

    private static final String SQL_CHECK_BY_CAR_NUMBER = "SELECT car_id FROM cars WHERE car_number = ?";
    private static final String SQL_INSERT_CAR = "INSERT INTO cars SET car_number = ?, carrying_weight = ?" +
            ", carrying_volume = ?, passengers_number = ?, user_id_fk = (SELECT user_id FROM users WHERE login = ?)";
    private static final String SQL_FIND_BY_USER_ID = "SELECT car_id, car_number, carrying_weight, carrying_volume, passengers_number " +
            "FROM cars WHERE user_id_fk = ?";
    private static final String SQL_FIND_BY_CAR_NUMBER = "SELECT car_id, car_number, carrying_weight, " +
            "carrying_volume, passengers_number FROM cars WHERE car_number = ?";
    private static final String SQL_UPDATE_PARAMETERS = "UPDATE cars SET %s WHERE car_id = ?";
    private static final String SQL_DELETE_BY_CAR_NUMBER = "DELETE FROM cars WHERE car_number = ?";

    public CarDaoImpl(){}

    @Override
    public boolean add(Car car, String login) throws DaoException {
        if (car == null || login == null) {
            throw new DaoException("Parameter is null.");
        }
        boolean isCarAdded;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
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
    public boolean removeByCarNumber(String carNumber) throws DaoException {
        boolean isRemoved;
        if (carNumber == null) {
            throw new DaoException("Car number is null. ");
        }
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_CAR_NUMBER)) {

            statement.setString(1, carNumber);
            int result = statement.executeUpdate();
            isRemoved = result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isRemoved;
    }

    @Override
    public Optional<Car> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Car> findByNumber(String carNumber) throws DaoException {
        if (carNumber == null) {
            throw new DaoException("Car number is null.");
        }
        Car car = null;

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_CAR_NUMBER)) {

            statement.setString(1, carNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long carId = resultSet.getLong(ParameterName.CAR_ID);
                    double carryingWeight = resultSet.getDouble(ParameterName.CARRYING_WEIGHT);
                    double carryingVolume = resultSet.getDouble(ParameterName.CARRYING_VOLUME);
                    int passengersNumber = resultSet.getInt(ParameterName.PASSENGERS_NUMBER);

                    car = new CarBuilder()
                            .buildCarId(carId)
                            .buildCarNumber(carNumber)
                            .buildCarryingWeight(carryingWeight)
                            .buildCarryingVolume(carryingVolume)
                            .buildPassengers(passengersNumber)
                            .buildCar();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> findByUser(User user) throws DaoException {
        if (user == null) {
            throw new DaoException("User is null.");
        }
        List<Car> cars = new ArrayList<>();

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {

            statement.setLong(1, user.getUserId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long carId = resultSet.getLong(ParameterName.CAR_ID);
                    String carNumber = resultSet.getString(ParameterName.CAR_NUMBER);
                    double carryingWeight = resultSet.getDouble(ParameterName.CARRYING_WEIGHT);
                    double carryingVolume = resultSet.getDouble(ParameterName.CARRYING_VOLUME);
                    int passengersNumber = resultSet.getInt(ParameterName.PASSENGERS_NUMBER);

                    Car car = new CarBuilder()
                            .buildCarId(carId)
                            .buildCarNumber(carNumber)
                            .buildCarryingWeight(carryingWeight)
                            .buildCarryingVolume(carryingVolume)
                            .buildPassengers(passengersNumber)
                            .buildOwner(user)
                            .buildCar();
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return cars;
    }

    @Override
    public boolean update(String carNumber, Map<String, String> parameters) throws DaoException {
        return false;
    }

    @Override
    public boolean updateById(long carId, Map<String, String> parameters) throws DaoException {
        boolean isUpdated;
        if (parameters == null) {
            throw new DaoException("Incorrect data. ");
        }
        String parametersSQL = fillParametersSQL(parameters);
        String sqlRequest = String.format(SQL_UPDATE_PARAMETERS, parametersSQL);

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            statement.setLong(1, carId);
            int result = statement.executeUpdate();
            isUpdated = result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
        return isUpdated;
    }

    @Override
    public boolean checkCarNumber(String carNumber) throws DaoException {
        if (carNumber == null) {
            throw new DaoException("Car number is null.");
        }
        boolean isNumberInBase;

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
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
