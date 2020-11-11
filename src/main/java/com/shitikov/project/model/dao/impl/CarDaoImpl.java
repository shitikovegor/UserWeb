package com.shitikov.project.model.dao.impl;

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

/**
 * The type Car dao.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class CarDaoImpl implements CarDao {
    private static CarDaoImpl instance;
    private static final String SQL_CHECK_BY_CAR_NUMBER = "SELECT car_id FROM cars WHERE car_number = ?";
    private static final String SQL_INSERT_CAR = "INSERT INTO cars SET car_number = ?, carrying_weight = ?" +
            ", carrying_volume = ?, passengers_number = ?, user_id_fk = (SELECT user_id FROM users WHERE login = ?)";
    private static final String SQL_FIND_BY_USER_ID = "SELECT car_id, car_number, carrying_weight, carrying_volume, " +
            "passengers_number, removed FROM cars WHERE user_id_fk = ?";
    private static final String SQL_FIND_AVAILABLE_BY_USER_ID = "SELECT car_id, car_number, carrying_weight, " +
            "carrying_volume, passengers_number FROM cars WHERE user_id_fk = ? && removed = 0";
    private static final String SQL_FIND_BY_ID = "SELECT car_id, car_number, carrying_weight, " +
            "carrying_volume, passengers_number FROM cars WHERE car_id = ?";
    private static final String SQL_UPDATE_PARAMETERS = "UPDATE cars SET %s WHERE car_id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM cars WHERE car_id = ?";
    private static final String SQL_REMOVE_USED = "UPDATE cars SET removed = 1 WHERE car_id = ?";


    private CarDaoImpl(){
    }

    public static CarDaoImpl getInstance() {
        if (instance == null) {
            instance = new CarDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Car car, String ... login) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CAR)) {

            statement.setString(1, car.getCarNumber());
            statement.setDouble(2, car.getCarryingWeight());
            statement.setDouble(3, car.getCarryingVolume());
            statement.setInt(4, car.getPassengers());
            statement.setString(5, login[0]);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
    }

    @Override
    public boolean remove(long id) throws DaoException {
       try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {

            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public Optional<Car> findById(long id) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Car car = null;
                if (resultSet.next()) {
                    String carNumber = resultSet.getString(ParameterName.CAR_NUMBER);
                    double carryingWeight = resultSet.getDouble(ParameterName.CARRYING_WEIGHT);
                    double carryingVolume = resultSet.getDouble(ParameterName.CARRYING_VOLUME);
                    int passengersNumber = resultSet.getInt(ParameterName.PASSENGERS_NUMBER);

                    car =  Car.newBuilder()
                            .buildCarId(id)
                            .buildCarNumber(carNumber)
                            .buildCarryingWeight(carryingWeight)
                            .buildCarryingVolume(carryingVolume)
                            .buildPassengers(passengersNumber)
                            .buildCar();
                }
                return Optional.ofNullable(car);
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public List<Car> findByUser(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {

            statement.setLong(1, user.getUserId());
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Car> cars = new ArrayList<>();
                while (resultSet.next()) {
                    long carId = resultSet.getLong(ParameterName.CAR_ID);
                    String carNumber = resultSet.getString(ParameterName.CAR_NUMBER);
                    double carryingWeight = resultSet.getDouble(ParameterName.CARRYING_WEIGHT);
                    double carryingVolume = resultSet.getDouble(ParameterName.CARRYING_VOLUME);
                    int passengersNumber = resultSet.getInt(ParameterName.PASSENGERS_NUMBER);
                    boolean removed = resultSet.getBoolean(ParameterName.REMOVED);

                    Car car = Car.newBuilder()
                            .buildCarId(carId)
                            .buildCarNumber(carNumber)
                            .buildCarryingWeight(carryingWeight)
                            .buildCarryingVolume(carryingVolume)
                            .buildPassengers(passengersNumber)
                            .buildRemoved(removed)
                            .buildCar();
                    cars.add(car);
                }
                return cars;
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public boolean update(Long carId, Map<String, String> parameters) throws DaoException {
        StringBuilder parametersSQL = new StringBuilder();
        List<String> searchValues = fillParametersSQL(parameters, parametersSQL);
        String sqlRequest = String.format(SQL_UPDATE_PARAMETERS, parametersSQL);

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            for (int i = 0; i < searchValues.size(); i++) {
                String value = searchValues.get(i);
                statement.setString(i + 1, value);
            }
            statement.setLong(searchValues.size() + 1, carId);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
    }

    @Override
    public boolean checkCarNumber(String carNumber) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_CAR_NUMBER)) {
            statement.setString(1, carNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public boolean removeUsed(long id) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_USED)) {

            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public List<Car> findAvailableByUser(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_AVAILABLE_BY_USER_ID)) {

            statement.setLong(1, user.getUserId());
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Car> cars = new ArrayList<>();
                while (resultSet.next()) {
                    long carId = resultSet.getLong(ParameterName.CAR_ID);
                    String carNumber = resultSet.getString(ParameterName.CAR_NUMBER);
                    double carryingWeight = resultSet.getDouble(ParameterName.CARRYING_WEIGHT);
                    double carryingVolume = resultSet.getDouble(ParameterName.CARRYING_VOLUME);
                    int passengersNumber = resultSet.getInt(ParameterName.PASSENGERS_NUMBER);

                    Car car = Car.newBuilder()
                            .buildCarId(carId)
                            .buildCarNumber(carNumber)
                            .buildCarryingWeight(carryingWeight)
                            .buildCarryingVolume(carryingVolume)
                            .buildPassengers(passengersNumber)
                            .buildCar();
                    cars.add(car);
                }
                return cars;
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }
}
