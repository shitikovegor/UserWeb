package com.shitikov.project.model.dao.impl;

import com.shitikov.project.model.dao.ApplicationDao;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.AddressTimeData;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.CargoApplication;
import com.shitikov.project.model.entity.application.PassengerApplication;
import com.shitikov.project.model.entity.type.ApplicationType;
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

/**
 * The type Application dao.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class ApplicationDaoImpl implements ApplicationDao {
    private static final String SQL_FIND_BY_ID = "SELECT application_id, title, application_type, date, " +
            "cargo_weight, cargo_volume, passengers_number, departure_date, departure_address, " +
            "departure_city, arrival_date, arrival_address, arrival_city, description FROM applications WHERE application_id = ?";
    private static final String SQL_INSERT_APPLICATION = "INSERT INTO applications(title, application_type, date, " +
            "departure_date, departure_address, departure_city, arrival_date, arrival_address, arrival_city, " +
            "description, cargo_weight, cargo_volume, passengers_number, user_id_fk) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?, " +
            "(SELECT user_id FROM users WHERE login = ?))";
    private static final String SQL_FIND_ALL = "SELECT app.application_id, app.title, app.application_type, app.date, " +
            "app.cargo_weight, app.cargo_volume, app.passengers_number, app.departure_date, app.departure_address, " +
            "app.departure_city, app.arrival_date, app.arrival_address, app.arrival_city, app.description, ord.status " +
            "FROM applications app LEFT JOIN orders ord ON app.application_id = ord.application_id_fk ";
    private static final String SQL_FIND_BY_USER_ID = "SELECT app.application_id, app.title, app.application_type, app.date, " +
            "app.cargo_weight, app.cargo_volume, app.passengers_number, app.departure_date, app.departure_address, " +
            "app.departure_city, app.arrival_date, app.arrival_address, app.arrival_city, app.description, ord.status " +
            "FROM applications app LEFT JOIN orders ord ON app.application_id = ord.application_id_fk WHERE " +
            "app.user_id_fk = ? ORDER BY app.application_id";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM applications WHERE application_id = ?";
    private static final String SQL_UPDATE_PARAMETERS = "UPDATE applications SET %s WHERE application_id = ?";
    private static final String LAST_SOME_ELEMENTS = " WHERE ord.status IS NULL ORDER BY app.date DESC LIMIT ?";
    private static final String DATE_ORDER = " ORDER BY app.departure_date";
    private static final String WHERE = " WHERE ";
    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSED_PARENTHESIS = ") ";
    private static final String TYPE_FIELD = "app.application_type = ? ";
    private static final String LOGICAL_AND = " && ";
    private static final String LOGICAL_OR = " || ";
    private static final String DEP_DATE_FIELD = "app.departure_date ";
    private static final String BETWEEN = "BETWEEN ? AND ? ";
    private static final String PASS_NUMBER_FIELD = "app.passengers_number ";
    private static final String WEIGHT_FIELD = "app.cargo_weight ";
    private static final String VOLUME_FIELD = "app.cargo_volume ";
    private static final String CITY_FIELD = "app.departure_city = ? ";
    private static final String STATUS_NULL = "ord.status is null ";
    private static final String STATUS_FIELD = "ord.status = ? ";
    private static ApplicationDaoImpl instance;

    private ApplicationDaoImpl() {
    }

    public static ApplicationDaoImpl getInstance() {
        if (instance == null) {
            instance = new ApplicationDaoImpl();
        }
        return instance;
    }

    static CargoApplication buildCargoApplication(ResultSet resultSet) throws SQLException {
        CargoApplication application = CargoApplication.newBuilder()
                .buildCargoWeight(resultSet.getDouble(CARGO_WEIGHT))
                .buildCargoVolume(resultSet.getDouble(CARGO_VOLUME))
                .buildApplicationId(resultSet.getLong(APPLICATION_ID))
                .buildTitle(resultSet.getString(TITLE))
                .buildApplicationType(ApplicationType.CARGO)
                .buildDate(resultSet.getLong(DATE))
                .buildAddressTimeData(AddressTimeData.newBuilder()
                        .buildDepartureDate(resultSet.getLong(DEPARTURE_DATE))
                        .buildDepartureAddress(Address.newBuilder()
                                .buildStreetHome(resultSet.getString(DEPARTURE_ADDRESS))
                                .buildCity(resultSet.getString(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(resultSet.getLong(ARRIVAL_DATE))
                        .buildArrivalAddress(Address.newBuilder()
                                .buildStreetHome(resultSet.getString(ARRIVAL_ADDRESS))
                                .buildCity(resultSet.getString(ARRIVAL_CITY))
                                .buildAddress())
                        .buildAddressTimeData())
                .buildDescription(resultSet.getString(DESCRIPTION))
                .buildApplication();

        return application;
    }

    static PassengerApplication buildPassengerApplication(ResultSet resultSet) throws SQLException {
        PassengerApplication application = PassengerApplication.newBuilder()
                .buildPassengersNumber(resultSet.getInt(PASSENGERS_NUMBER))
                .buildApplicationId(resultSet.getLong(APPLICATION_ID))
                .buildTitle(resultSet.getString(TITLE))
                .buildApplicationType(ApplicationType.PASSENGER)
                .buildDate(resultSet.getLong(DATE))
                .buildAddressTimeData(AddressTimeData.newBuilder()
                        .buildDepartureDate(resultSet.getLong(DEPARTURE_DATE))
                        .buildDepartureAddress(Address.newBuilder()
                                .buildStreetHome(resultSet.getString(DEPARTURE_ADDRESS))
                                .buildCity(resultSet.getString(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(resultSet.getLong(ARRIVAL_DATE))
                        .buildArrivalAddress(Address.newBuilder()
                                .buildStreetHome(resultSet.getString(ARRIVAL_ADDRESS))
                                .buildCity(resultSet.getString(ARRIVAL_CITY))
                                .buildAddress())
                        .buildAddressTimeData())
                .buildDescription(resultSet.getString(DESCRIPTION))
                .buildApplication();

        return application;
    }

    @Override
    public boolean add(Application application, String... login) throws DaoException {
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
            statement.setString(14, login[0]);

            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
    }

    @Override
    public Optional<Application> findById(long id) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            Application application = null;
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ApplicationType applicationType =
                            ApplicationType.valueOf(resultSet.getString(ParameterName.APPLICATION_TYPE).toUpperCase());
                    if (applicationType == ApplicationType.CARGO) {
                        application = buildCargoApplication(resultSet);
                    } else {
                        application = buildPassengerApplication(resultSet);
                    }
                }
            }
            return Optional.ofNullable(application);
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public Map<Application, OrderStatus> findByUser(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {

            Map<Application, OrderStatus> applications = new LinkedHashMap<>();
            statement.setLong(1, user.getUserId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ApplicationType applicationType =
                            ApplicationType.valueOf(resultSet.getString(ParameterName.APPLICATION_TYPE).toUpperCase());
                    String status = resultSet.getString(STATUS);
                    OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : OrderStatus.ACTIVE;
                    if (applicationType == ApplicationType.CARGO) {
                        CargoApplication cargoApplication = buildCargoApplication(resultSet);
                        applications.put(cargoApplication, orderStatus);
                    } else {
                        PassengerApplication passengerApplication = buildPassengerApplication(resultSet);
                        applications.put(passengerApplication, orderStatus);
                    }
                }
            }
            return applications;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public Map<Application, OrderStatus> findAll() throws DaoException {
        String sqlWithOrder = SQL_FIND_ALL.concat(DATE_ORDER);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlWithOrder);
             ResultSet resultSet = statement.executeQuery()) {

            Map<Application, OrderStatus> applications = new LinkedHashMap<>();
            while (resultSet.next()) {
                ApplicationType applicationType =
                        ApplicationType.valueOf(resultSet.getString(ParameterName.APPLICATION_TYPE).toUpperCase());
                String status = resultSet.getString(STATUS);
                OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : OrderStatus.ACTIVE;
                if (applicationType == ApplicationType.CARGO) {
                    CargoApplication cargoApplication = buildCargoApplication(resultSet);
                    applications.put(cargoApplication, orderStatus);
                } else {
                    PassengerApplication passengerApplication = buildPassengerApplication(resultSet);
                    applications.put(passengerApplication, orderStatus);
                }
            }
            return applications;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public Map<Application, OrderStatus> findByParameters(Map<String, Object> parameters) throws DaoException {
        if (parameters.isEmpty()) {
            return findAll();
        }
        StringBuilder sqlRequest = new StringBuilder(SQL_FIND_ALL);
        List<Object> searchValues = fillSearchParameters(sqlRequest, parameters);
        sqlRequest.append(DATE_ORDER);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest.toString())) {

            for (int i = 0; i < searchValues.size(); i++) {
                Object value = searchValues.get(i);
                if (value instanceof Long) {
                    statement.setLong(i + 1, (Long) value);
                } else if (value instanceof Integer) {
                    statement.setInt(i + 1, (Integer) value);
                } else if (value instanceof Double) {
                    statement.setDouble(i + 1, (Double) value);
                } else {
                    statement.setString(i + 1, (String) value);
                }
            }
            Map<Application, OrderStatus> applications = new LinkedHashMap<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ApplicationType applicationType =
                            ApplicationType.valueOf(resultSet.getString(ParameterName.APPLICATION_TYPE).toUpperCase());
                    String status = resultSet.getString(STATUS);
                    OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : OrderStatus.ACTIVE;
                    if (applicationType == ApplicationType.CARGO) {
                        CargoApplication cargoApplication = buildCargoApplication(resultSet);
                        applications.put(cargoApplication, orderStatus);
                    } else {
                        PassengerApplication passengerApplication = buildPassengerApplication(resultSet);
                        applications.put(passengerApplication, orderStatus);
                    }
                }
            }
            return applications;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public boolean update(Long applicationId, Map<String, String> parameters) throws DaoException {
        StringBuilder parametersSQL = new StringBuilder();
        List<String> searchValues = fillParametersSQL(parameters, parametersSQL);
        String sqlRequest = String.format(SQL_UPDATE_PARAMETERS, parametersSQL);

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            for (int i = 0; i < searchValues.size(); i++) {
                String value = searchValues.get(i);
                statement.setString(i + 1, value);
            }
            statement.setLong(searchValues.size() + 1, applicationId);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
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
    public Map<Application, OrderStatus> findRecentActiveApps(int numberOfApps) throws DaoException {
        String sqlRecent = SQL_FIND_ALL.concat(LAST_SOME_ELEMENTS);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRecent)) {

            statement.setInt(1, numberOfApps);
            try (ResultSet resultSet = statement.executeQuery()) {
                Map<Application, OrderStatus> applications = new HashMap<>();
                while (resultSet.next()) {
                    ApplicationType applicationType =
                            ApplicationType.valueOf(resultSet.getString(ParameterName.APPLICATION_TYPE).toUpperCase());
                    String status = resultSet.getString(STATUS);
                    OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : OrderStatus.ACTIVE;
                    if (applicationType == ApplicationType.CARGO) {
                        CargoApplication cargoApplication = buildCargoApplication(resultSet);
                        applications.put(cargoApplication, orderStatus);
                    } else {
                        PassengerApplication passengerApplication = buildPassengerApplication(resultSet);
                        applications.put(passengerApplication, orderStatus);
                    }
                }
                return applications;
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    private List<Object> fillSearchParameters(StringBuilder sqlRequest, Map<String, Object> parameters) {
        List<Object> searchValues = new ArrayList<>();
        sqlRequest.append(WHERE);
        if (parameters.containsKey(CARGO) && parameters.containsKey(PASSENGER)) {
            sqlRequest.append(OPEN_PARENTHESIS)
                    .append(TYPE_FIELD)
                    .append(LOGICAL_OR)
                    .append(TYPE_FIELD)
                    .append(CLOSED_PARENTHESIS);
            searchValues.add(parameters.get(CARGO));
            searchValues.add(parameters.get(PASSENGER));
        } else if (parameters.containsKey(CARGO)) {
            sqlRequest.append(TYPE_FIELD);
            searchValues.add(parameters.get(CARGO));
        } else if (parameters.containsKey(PASSENGER)) {
            sqlRequest.append(TYPE_FIELD);
            searchValues.add(parameters.get(PASSENGER));
        }
        if (parameters.containsKey(DEPARTURE_DATE_FROM)) {
            checkParamWhere(sqlRequest);
            sqlRequest.append(DEP_DATE_FIELD)
                    .append(BETWEEN);
            searchValues.add(parameters.get(DEPARTURE_DATE_FROM));
            searchValues.add(parameters.get(DEPARTURE_DATE_TO));
        }
        if (parameters.containsKey(PASSENGER_NUMBER_FROM)) {
            checkParamWhere(sqlRequest);
            sqlRequest.append(PASS_NUMBER_FIELD)
                    .append(BETWEEN);
            searchValues.add(parameters.get(PASSENGER_NUMBER_FROM));
            searchValues.add(parameters.get(PASSENGER_NUMBER_TO));
        }
        if (parameters.containsKey(CARGO_WEIGHT_FROM)) {
            checkParamWhere(sqlRequest);
            sqlRequest.append(WEIGHT_FIELD)
                    .append(BETWEEN);
            searchValues.add(parameters.get(CARGO_WEIGHT_FROM));
            searchValues.add(parameters.get(CARGO_WEIGHT_TO));
        }
        if (parameters.containsKey(CARGO_VOLUME_FROM)) {
            checkParamWhere(sqlRequest);
            sqlRequest.append(VOLUME_FIELD)
                    .append(BETWEEN);
            searchValues.add(parameters.get(CARGO_VOLUME_FROM));
            searchValues.add(parameters.get(CARGO_VOLUME_TO));
        }
        if (parameters.containsKey(CITY)) {
            checkParamWhere(sqlRequest);
            sqlRequest.append(CITY_FIELD);
            searchValues.add(parameters.get(CITY));
        }
        List<String> statuses = new ArrayList<>();
        if (parameters.containsKey(ACTIVE)) {
            statuses.add(null);
        }
        if (parameters.containsKey(CONFIRMED)) {
            statuses.add((String) parameters.get(CONFIRMED));
        }
        if (parameters.containsKey(COMPLETED)) {
            statuses.add((String) parameters.get(COMPLETED));
        }
        if (parameters.containsKey(CANCELED)) {
            statuses.add((String) parameters.get(CANCELED));
        }
        if (!statuses.isEmpty()) {
            checkParamWhere(sqlRequest);
            sqlRequest.append(OPEN_PARENTHESIS);
            for (String status : statuses) {
                if (status == null) {
                    sqlRequest.append(STATUS_NULL);
                } else {
                    sqlRequest.append(STATUS_FIELD);
                    searchValues.add(status);
                }
                sqlRequest.append(LOGICAL_OR);
            }
            sqlRequest.delete(sqlRequest.length() - 4, sqlRequest.length());
            sqlRequest.append(CLOSED_PARENTHESIS);
        }
        return searchValues;
    }

    private void checkParamWhere(StringBuilder sqlRequest) {
        if (!sqlRequest.substring(sqlRequest.length() - 7).equals(WHERE)) {
            sqlRequest.append(LOGICAL_AND);
        }
    }
}
