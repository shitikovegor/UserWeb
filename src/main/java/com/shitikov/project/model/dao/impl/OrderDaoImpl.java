package com.shitikov.project.model.dao.impl;

import com.shitikov.project.model.builder.OrderBuilder;
import com.shitikov.project.model.dao.OrderDao;
import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.ApplicationType;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.pool.ConnectionPool;
import com.shitikov.project.util.ParameterName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static com.shitikov.project.util.ParameterName.PHONE;
import static com.shitikov.project.util.ParameterName.STATUS;

public class OrderDaoImpl implements OrderDao {
    private static final String SQL_FIND_BY_USER_ID = "SELECT ord.order_id, ord.car_id_fk, ord.user_id_fk, ord.status, " +
            "app.application_id, app.title, app.application_type, app.date, app.cargo_weight, app.cargo_volume, " +
            "app.passengers_number, app.departure_date, app.departure_address, " +
            "app.departure_city, app.arrival_date, app.arrival_address, app.arrival_city, app.description, us.phone " +
            "FROM orders ord JOIN applications app ON ord.application_id_fk = app.application_id " +
            "JOIN users us ON us.user_id = app.user_id_fk WHERE ord.user_id_fk = ?";
    private static final String SQL_FIND_BY_APP_ID = "SELECT ord.order_id, ord.car_id_fk, ord.user_id_fk, ord.status, " +
            "app.application_id, app.title, app.application_type, app.date, app.cargo_weight, app.cargo_volume, " +
            "app.passengers_number, app.departure_date, app.departure_address, " +
            "app.departure_city, app.arrival_date, app.arrival_address, app.arrival_city, app.description " +
            "FROM orders ord JOIN applications app ON ord.application_id_fk = app.application_id " +
            "WHERE ord.application_id_fk = ?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(application_id_fk, car_id_fk, user_id_fk, " +
            "status) VALUES (?,?,?,?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM orders WHERE order_id = ?";
    private static final String SQL_UPDATE_STATUS = "UPDATE orders SET status = ? WHERE order_id = ?";

    @Override
    public boolean add(Order order, String ... parameter) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER)) {

            statement.setLong(1, order.getApplication().getApplicationId());
            statement.setLong(2, order.getCarId());
            statement.setLong(3, order.getUserId());
            statement.setString(4, order.getStatus().getName());

            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
    }

    @Override
    public Optional<Order> findById(long id) throws DaoException {
        throw new UnsupportedOperationException("this class doesn't support this method");
    }

    @Override
    public boolean update(Long id, Map<String, String> parameters) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS)) {

            statement.setString(1, parameters.get(STATUS));
            statement.setLong(2, id);
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
    public Map<Order, Long> findByUser(User user) throws DaoException {
       try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {

            Map<Order, Long> orders = new TreeMap<>(new Order.StatusComparator());
            statement.setLong(1, user.getUserId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long orderId = resultSet.getLong(ParameterName.ORDER_ID);
                    long carId = resultSet.getLong(ForeignKey.CAR_ID_FK);
                    OrderStatus status = OrderStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
                    long phone = resultSet.getLong(PHONE);

                    ApplicationType applicationType =
                            ApplicationType.valueOf(resultSet.getString(ParameterName.APPLICATION_TYPE).toUpperCase());
                    Application application;
                    if (applicationType == ApplicationType.CARGO) {
                        application = ApplicationDaoImpl.buildCargoApplication(resultSet);
                    } else {
                        application = ApplicationDaoImpl.buildPassengerApplication(resultSet);
                    }
                    Order order = new OrderBuilder()
                            .buildOrderId(orderId)
                            .buildCarId(carId)
                            .buildApplication(application)
                            .buildStatus(status)
                            .buildOrder();
                    orders.put(order, phone);
                }
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public Optional<Order> findByAppId(long applicationId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_APP_ID)) {

            Order order = null;
            statement.setLong(1, applicationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long orderId = resultSet.getLong(ParameterName.ORDER_ID);
                    long carId = resultSet.getLong(ForeignKey.CAR_ID_FK);
                    OrderStatus status = OrderStatus.valueOf(resultSet.getString(STATUS).toUpperCase());

                    ApplicationType applicationType =
                            ApplicationType.valueOf(resultSet.getString(ParameterName.APPLICATION_TYPE).toUpperCase());
                    Application application;
                    if (applicationType == ApplicationType.CARGO) {
                        application = ApplicationDaoImpl.buildCargoApplication(resultSet);
                    } else {
                        application = ApplicationDaoImpl.buildPassengerApplication(resultSet);
                    }
                    order = new OrderBuilder()
                            .buildOrderId(orderId)
                            .buildCarId(carId)
                            .buildApplication(application)
                            .buildStatus(status)
                            .buildOrder();
                }
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }
}
