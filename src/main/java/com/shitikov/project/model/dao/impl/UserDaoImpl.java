package com.shitikov.project.model.dao.impl;

import com.shitikov.project.model.builder.AddressBuilder;
import com.shitikov.project.model.builder.UserBuilder;
import com.shitikov.project.model.dao.UserDao;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.pool.ConnectionPool;
import com.shitikov.project.util.ParameterName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final String SQL_INSERT_USER
            = "INSERT INTO users(login, password, name, surname, email, phone, role, subject, blocked, active)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_INSERT_ADDRESS = "INSERT INTO addresses SET address = ?, city = ?, " +
            "user_id_fk = (SELECT user_id FROM users WHERE login = ?)";
    private static final String SQL_CHECK_BY_LOGIN
            = "SELECT user_id, login, password, role FROM users WHERE login = ?";
    private static final String SQL_FIND_BY_PARAMETER
            = "SELECT user_id, login, name, surname, email, phone, role, subject, blocked, active FROM users WHERE %s" +
            " = ?";
    private static final String SQL_ACTIVATE_ACCOUNT = "UPDATE users SET active = 1 WHERE login = ?";
    private static final String SQL_UPDATE_PARAMETERS = "UPDATE users SET %s WHERE login = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";
    private static final String SQL_UPDATE_PHONE = "UPDATE users SET phone = ? WHERE login = ?";
    private static final String SQL_UPDATE_ADDRESS = "UPDATE addresses INNER JOIN" +
            " users ON addresses.user_id_fk = users.user_id SET %s WHERE users.login = ?";
    private static final String SQL_FIND_ADDRESS_BY_LOGIN =
            "SELECT address, city FROM addresses INNER JOIN users ON addresses.user_id_fk = users.user_id WHERE users.login = ?";
    private static UserDaoImpl instance;

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(User user, String password) throws DaoException {
        boolean isUserAdded = false;
        if (user == null) {
            throw new DaoException("User is null.");
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, password);
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getEmail());
            statement.setLong(6, user.getPhone());
            statement.setString(7, user.getRoleType().getRoleName());
            statement.setString(8, user.getSubjectType().getSubjectName());
            statement.setBoolean(9, user.isBlocked());
            statement.setBoolean(10, user.isActive());
            int result = statement.executeUpdate();
            isUserAdded = result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
        return isUserAdded;
    }

    @Override
    public boolean remove(User user) throws DaoException {
        return false;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        if (login == null) {
            throw new DaoException("Login is null.");
        }
        User user = null;
        String sqlRequest = String.format(SQL_FIND_BY_PARAMETER, ParameterName.LOGIN);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString(ParameterName.NAME);
                    String surname = resultSet.getString(ParameterName.SURNAME);
                    String email = resultSet.getString(ParameterName.EMAIL);
                    Long phone = resultSet.getLong(ParameterName.PHONE);
                    RoleType roleType =
                            RoleType.valueOf(resultSet.getString(ParameterName.ROLE_TYPE).toUpperCase());
                    SubjectType subjectType =
                            SubjectType.valueOf(resultSet.getString(ParameterName.SUBJECT_TYPE).toUpperCase());
                    boolean blocked = resultSet.getBoolean(ParameterName.BLOCKED);
                    boolean active = resultSet.getBoolean(ParameterName.ACTIVE);

                    user = new UserBuilder()
                            .buildLogin(login)
                            .buildName(name)
                            .buildSurname(surname)
                            .buildEmail(email)
                            .buildPhone(phone)
                            .buildSubjectType(subjectType)
                            .buildRoleType(roleType)
                            .buildBlocked(blocked)
                            .buildActive(active)
                            .buildUser();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public String findPassword(String login) throws DaoException {
        if (login == null) {
            throw new DaoException("Login is null.");
        }
        String hashedPassword = "";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_LOGIN)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString(ParameterName.PASSWORD);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return hashedPassword;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Address> findUserAddress(String login) throws DaoException {
        if (login == null) {
            throw new DaoException("Login is null.");
        }
        Address address = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ADDRESS_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String streetHome = resultSet.getString(ParameterName.ADDRESS);
                    String city = resultSet.getString(ParameterName.CITY);

                    address = new AddressBuilder()
                            .buildStreetHome(streetHome)
                            .buildCity(city)
                            .buildAddress();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return Optional.ofNullable(address);
    }

    @Override
    public boolean addAddress(String login, Map<String, String> parameters) throws DaoException {
        boolean isUserAdded = false;
        if (login == null || parameters == null || parameters.isEmpty()) {
            throw new DaoException("Invalid data.");
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ADDRESS)) {

            statement.setString(1, parameters.get(ParameterName.ADDRESS));
            statement.setString(2, parameters.get(ParameterName.CITY));
            statement.setString(3, login);
            int result = statement.executeUpdate();
            isUserAdded = result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
        return isUserAdded;
    }

    @Override
    public boolean checkLogin(String login) throws DaoException {
        if (login == null) {
            throw new DaoException("Login is null.");
        }
        boolean isloginExists;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_LOGIN)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                isloginExists = resultSet.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isloginExists;
    }

    @Override
    public boolean checkEmail(String email) throws DaoException {
        boolean isEmailInBase;
        if (email == null) {
            throw new DaoException("Email is null.");
        }
        String sqlRequest = String.format(SQL_FIND_BY_PARAMETER, ParameterName.EMAIL);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                isEmailInBase = resultSet.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isEmailInBase;
    }

    @Override
    public boolean checkUserAddress(String login) throws DaoException {
        boolean hasUserAddress;
        if (login == null) {
            throw new DaoException("Login is null.");
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ADDRESS_BY_LOGIN)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                hasUserAddress = resultSet.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return hasUserAddress;
    }

    @Override
    public RoleType findRole(String login) throws DaoException {
        if (login == null) {
            throw new DaoException("Login is null.");
        }
        RoleType role = RoleType.GUEST;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_LOGIN)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleName = resultSet.getString(ParameterName.ROLE_TYPE);
                    role = RoleType.valueOf(roleName.toUpperCase());
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return role;
    }

    @Override
    public boolean updateParameters(String login, Map<String, String> parameters) throws DaoException {
        boolean isUpdated;
        if (login == null || parameters == null) {
            throw new DaoException("Incorrect data. ");
        }
        StringBuilder parametersSQL = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            parametersSQL.append(entry.getKey())
                    .append(" = \"")
                    .append(entry.getValue())
                    .append("\", ");
        }
        parametersSQL.delete(parametersSQL.lastIndexOf(","), parametersSQL.length());
        String sqlRequest = String.format(SQL_UPDATE_PARAMETERS, parametersSQL);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            statement.setString(1, login);
            int result = statement.executeUpdate();
            isUpdated = result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateContactParameters(String login, Map<String, String> parameters) throws DaoException {
        boolean isUpdated;
        if (login == null || parameters == null) {
            throw new DaoException("Incorrect data. ");
        }
        StringBuilder parametersSQL = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            parametersSQL.append(entry.getKey())
                    .append(" = \"")
                    .append(entry.getValue())
                    .append("\", ");
        }
        parametersSQL.delete(parametersSQL.lastIndexOf(","), parametersSQL.length());
        String sqlRequest = String.format(SQL_UPDATE_ADDRESS, parametersSQL);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            statement.setString(1, login);
            int result = statement.executeUpdate();
            isUpdated = result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updatePhone(String login, long phone) throws DaoException {
        boolean isUpdated;
        if (login == null) {
            throw new DaoException("Incorrect data. ");
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PHONE)) {

            statement.setLong(1, phone);
            statement.setString(2, login);
            int result = statement.executeUpdate();
            isUpdated = result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
        return isUpdated;
    }

    @Override
    public boolean updatePassword(String login, String newPassword) throws DaoException {
        boolean isUpdated;
        if (login == null || newPassword == null) {
            throw new DaoException("Incorrect data. ");
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {

            statement.setString(1, newPassword);
            statement.setString(2, login);
            int result = statement.executeUpdate();
            isUpdated = result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
        return isUpdated;
    }

    @Override
    public boolean activate(String login) throws DaoException {
        boolean isActivated;
        if (login == null) {
            throw new DaoException("User is null.");
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ACTIVATE_ACCOUNT)) {

            statement.setString(1, login);
            int result = statement.executeUpdate();
            isActivated = result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isActivated;
    }
}
