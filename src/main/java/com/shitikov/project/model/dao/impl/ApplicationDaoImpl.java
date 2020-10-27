package com.shitikov.project.model.dao.impl;

import com.shitikov.project.model.builder.AddressBuilder;
import com.shitikov.project.model.builder.AddressTimeDataBuilder;
import com.shitikov.project.model.builder.CargoApplicationBuilder;
import com.shitikov.project.model.builder.PassengerApplicationBuilder;
import com.shitikov.project.model.dao.ApplicationDao;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.*;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.pool.ConnectionPool;
import com.shitikov.project.util.ParameterName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.shitikov.project.util.ParameterName.*;

public class ApplicationDaoImpl implements ApplicationDao {
    private static final String SQL_CHECK_BY_APP_ID = "SELECT application_id FROM applications WHERE application_id = ?";
    private static final String SQL_INSERT_APPLICATION = "INSERT INTO applications(title, application_type, date, " +
            "departure_date, departure_address, departure_city, arrival_date, arrival_address, arrival_city, " +
            "description, cargo_weight, cargo_volume, passengers_number, user_id_fk) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?, " +
            "(SELECT user_id FROM users WHERE login = ?))";
    private static final String SQL_FIND_BY_USER_ID = "SELECT app.application_id, app.title, app.application_type, app.date, " +
            "app.cargo_weight, app.cargo_volume, app.passengers_number, app.departure_date, app.departure_address, " +
            "app.departure_city, app.arrival_date, app.arrival_address, app.arrival_city, app.description, ord.status " +
            "FROM applications app LEFT JOIN orders ord ON app.application_id = ord.application_id_fk WHERE user_id_fk = ?";


    public ApplicationDaoImpl() {
    }

    @Override
    public boolean add(Application application, String login) throws DaoException {
        if (application == null || login == null) {
            throw new DaoException("Parameter is null.");
        }
        boolean isApplicationAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_APPLICATION)) {

            statement.setString(1, application.getTitle());
            statement.setString(2, application.getApplicationType().getName());
            statement.setLong(3, application.getDate());
            AddressTimeData data = application.getAddressTimeData();
            statement.setLong(4, data.getDepartureDate());
            statement.setString(5, data.getDepartureAddress().getStreetHome());
            statement.setString(6, data.getDepartureAddress().getCity());
            statement.setLong(7, data.getArrivalDate());
            statement.setString(8, data.getArrivalAddress().getStreetHome());
            statement.setString(9, data.getArrivalAddress().getCity());
            statement.setString(10, application.getDescription());
            if (application instanceof CargoApplication) {
                CargoApplication cargoApp = (CargoApplication) application;
                statement.setDouble(11, cargoApp.getCargoWeight());
                statement.setDouble(12, cargoApp.getCargoVolume());
                statement.setInt(13, 0);
            } else {
                PassengerApplication passengerApp = (PassengerApplication) application;
                statement.setDouble(11, 0);
                statement.setDouble(12, 0);
                statement.setInt(13, passengerApp.getPassengersNumber());
            }
            statement.setString(14, login);
            
            int result = statement.executeUpdate();
            isApplicationAdded = result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
        return isApplicationAdded;
    }

    @Override
    public Optional<Application> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Map<OrderStatus, Application> findByUser(User user) throws DaoException {
        if (user == null) {
            throw new DaoException("User is null.");
        }
        Map<OrderStatus, Application> applications = new HashMap<>();

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {

            statement.setLong(1, user.getUserId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ApplicationType applicationType =
                            ApplicationType.valueOf(resultSet.getString(ParameterName.APPLICATION_TYPE).toUpperCase());
                    String status = resultSet.getString(STATUS);
                    OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : OrderStatus.ACTIVE;
                    if (applicationType == ApplicationType.CARGO) {
                        CargoApplication cargoApplication = buildCargoApplication(resultSet);
                        applications.put(orderStatus, cargoApplication);
                    } else {
                        PassengerApplication passengerApplication = buildPassengerApplication(resultSet);
                        applications.put(orderStatus, passengerApplication);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return applications;
    }

    @Override
    public List<Application> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Long applicationId, Map<String, String> parameters) throws DaoException {
        return false;
    }

    @Override
    public boolean remove(Application application) throws DaoException {
        return false;
    }

    @Override
    public List<Application> findByUserLogin(String login) throws DaoException {
        return null;
    }

    @Override
    public boolean checkByApplicationId(long applicationId) throws DaoException {
        return false;
    }

    private CargoApplication buildCargoApplication(ResultSet resultSet) throws SQLException {
        CargoApplication application = new CargoApplicationBuilder()
                .buildCargoWeight(resultSet.getDouble(CARGO_WEIGHT))
                .buildCargoVolume(resultSet.getDouble(CARGO_VOLUME))
                .buildTitle(resultSet.getString(TITLE))
                .buildApplicationType(ApplicationType.CARGO)
                .buildDate(resultSet.getLong(DATE))
                .buildAddressTimeData(new AddressTimeDataBuilder()
                        .buildDepartureDate(resultSet.getLong(DEPARTURE_DATE))
                        .buildDepartureAddress(new AddressBuilder()
                                .buildStreetHome(resultSet.getString(DEPARTURE_ADDRESS))
                                .buildCity(resultSet.getString(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(resultSet.getLong(ARRIVAL_DATE))
                        .buildArrivalAddress(new AddressBuilder()
                                .buildStreetHome(resultSet.getString(ARRIVAL_ADDRESS))
                                .buildCity(resultSet.getString(ARRIVAL_CITY))
                                .buildAddress())
                        .buildAddressTimeData())
                .buildDescription(resultSet.getString(DESCRIPTION))
                .buildApplication();

        return application;
    }

    private PassengerApplication buildPassengerApplication(ResultSet resultSet) throws SQLException {
        PassengerApplication application = new PassengerApplicationBuilder()
                .buildPassengersNumber(resultSet.getInt(PASSENGERS_NUMBER))
                .buildTitle(resultSet.getString(TITLE))
                .buildApplicationType(ApplicationType.CARGO)
                .buildDate(resultSet.getLong(DATE))
                .buildAddressTimeData(new AddressTimeDataBuilder()
                        .buildDepartureDate(resultSet.getLong(DEPARTURE_DATE))
                        .buildDepartureAddress(new AddressBuilder()
                                .buildStreetHome(resultSet.getString(DEPARTURE_ADDRESS))
                                .buildCity(resultSet.getString(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(resultSet.getLong(ARRIVAL_DATE))
                        .buildArrivalAddress(new AddressBuilder()
                                .buildStreetHome(resultSet.getString(ARRIVAL_ADDRESS))
                                .buildCity(resultSet.getString(ARRIVAL_CITY))
                                .buildAddress())
                        .buildAddressTimeData())
                .buildDescription(resultSet.getString(DESCRIPTION))
                .buildApplication();

        return application;
    }
}
