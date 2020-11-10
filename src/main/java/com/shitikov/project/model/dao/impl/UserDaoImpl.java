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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type User dao.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance;
    private static final String SQL_INSERT_USER
            = "INSERT INTO users(login, password, name, surname, email, phone, role, subject, blocked, activated)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_INSERT_ADDRESS = "INSERT INTO addresses SET address = ?, city = ?, " +
            "user_id_fk = (SELECT user_id FROM users WHERE login = ?)";
    private static final String SQL_CHECK_BY_LOGIN
            = "SELECT user_id, login, password, role FROM users WHERE login = ?";
    private static final String SQL_FIND_BY_PARAMETER
            = "SELECT user_id, login, name, surname, email, phone, role, subject, blocked, activated FROM users WHERE %s" +
            " = ?";
    private static final String SQL_UPDATE_ACTIVATION = "UPDATE users SET activated = ? WHERE login = ?";
    private static final String SQL_UPDATE_PARAMETERS = "UPDATE users SET %s WHERE login = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";
    private static final String SQL_UPDATE_PHONE = "UPDATE users SET phone = ? WHERE login = ?";
    private static final String SQL_UPDATE_ADDRESS = "UPDATE addresses INNER JOIN" +
            " users ON addresses.user_id_fk = users.user_id SET %s WHERE users.login = ?";
    private static final String SQL_FIND_ADDRESS_BY_LOGIN =
            "SELECT address, city FROM addresses INNER JOIN users ON addresses.user_id_fk = users.user_id WHERE users.login = ?";
    private static final String SQL_PHONE_BY_APP_ID = "SELECT phone FROM users JOIN applications ON " +
            "user_id = user_id_fk WHERE applications.application_id = ?";
    private static final String SQL_FIND_ALL = "SELECT user_id, login, password, name, surname, email, phone, role, " +
            "subject, blocked, activated FROM users WHERE role != 'administrator'";
    private static final String SQL_UPDATE_BLOCK = "UPDATE users SET blocked = ? WHERE login = ?";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(User user, String... password) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, password[0]);
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getEmail());
            statement.setLong(6, user.getPhone());
            statement.setString(7, user.getRoleType().getName());
            statement.setString(8, user.getSubjectType().getSubjectName());
            statement.setBoolean(9, user.isBlocked());
            statement.setBoolean(10, user.isActivated());
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
    }

    @Override
    public boolean remove(long id) {
        throw new UnsupportedOperationException("this class doesn't support this method");
    }

    @Override
    public Optional<User> findById(long userId) throws DaoException {
        String sqlRequest = String.format(SQL_FIND_BY_PARAMETER, ParameterName.USER_ID);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                User user = null;
                if (resultSet.next()) {
                    String login = resultSet.getString(ParameterName.LOGIN);
                    String name = resultSet.getString(ParameterName.NAME);
                    String surname = resultSet.getString(ParameterName.SURNAME);
                    String email = resultSet.getString(ParameterName.EMAIL);
                    long phone = resultSet.getLong(ParameterName.PHONE);
                    RoleType roleType =
                            RoleType.valueOf(resultSet.getString(ParameterName.ROLE_TYPE).toUpperCase());
                    SubjectType subjectType =
                            SubjectType.valueOf(resultSet.getString(ParameterName.SUBJECT_TYPE).toUpperCase());
                    boolean blocked = resultSet.getBoolean(ParameterName.BLOCKED);
                    boolean activated = resultSet.getBoolean(ParameterName.ACTIVATED);

                    user = new UserBuilder()
                            .buildUserId(userId)
                            .buildLogin(login)
                            .buildName(name)
                            .buildSurname(surname)
                            .buildEmail(email)
                            .buildPhone(phone)
                            .buildSubjectType(subjectType)
                            .buildRoleType(roleType)
                            .buildBlocked(blocked)
                            .buildActivated(activated)
                            .buildUser();
                }
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        String sqlRequest = String.format(SQL_FIND_BY_PARAMETER, ParameterName.LOGIN);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                User user = null;
                if (resultSet.next()) {
                    long userId = resultSet.getLong(ParameterName.USER_ID);
                    String name = resultSet.getString(ParameterName.NAME);
                    String surname = resultSet.getString(ParameterName.SURNAME);
                    String email = resultSet.getString(ParameterName.EMAIL);
                    long phone = resultSet.getLong(ParameterName.PHONE);
                    RoleType roleType =
                            RoleType.valueOf(resultSet.getString(ParameterName.ROLE_TYPE).toUpperCase());
                    SubjectType subjectType =
                            SubjectType.valueOf(resultSet.getString(ParameterName.SUBJECT_TYPE).toUpperCase());
                    boolean blocked = resultSet.getBoolean(ParameterName.BLOCKED);
                    boolean activated = resultSet.getBoolean(ParameterName.ACTIVATED);

                    user = new UserBuilder()
                            .buildUserId(userId)
                            .buildLogin(login)
                            .buildName(name)
                            .buildSurname(surname)
                            .buildEmail(email)
                            .buildPhone(phone)
                            .buildSubjectType(subjectType)
                            .buildRoleType(roleType)
                            .buildBlocked(blocked)
                            .buildActivated(activated)
                            .buildUser();
                }
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public String findPassword(String login) throws DaoException {

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_LOGIN)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                String hashedPassword = "";
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString(ParameterName.PASSWORD);
                }
                return hashedPassword;
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                long userId = resultSet.getLong(ParameterName.USER_ID);
                String login = resultSet.getString(ParameterName.LOGIN);
                String name = resultSet.getString(ParameterName.NAME);
                String surname = resultSet.getString(ParameterName.SURNAME);
                String email = resultSet.getString(ParameterName.EMAIL);
                long phone = resultSet.getLong(ParameterName.PHONE);
                RoleType roleType =
                        RoleType.valueOf(resultSet.getString(ParameterName.ROLE_TYPE).toUpperCase());
                SubjectType subjectType =
                        SubjectType.valueOf(resultSet.getString(ParameterName.SUBJECT_TYPE).toUpperCase());
                boolean blocked = resultSet.getBoolean(ParameterName.BLOCKED);
                boolean activated = resultSet.getBoolean(ParameterName.ACTIVATED);

                User user = new UserBuilder()
                        .buildUserId(userId)
                        .buildLogin(login)
                        .buildName(name)
                        .buildSurname(surname)
                        .buildEmail(email)
                        .buildPhone(phone)
                        .buildSubjectType(subjectType)
                        .buildRoleType(roleType)
                        .buildBlocked(blocked)
                        .buildActivated(activated)
                        .buildUser();
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public Optional<Address> findUserAddress(String login) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ADDRESS_BY_LOGIN)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                Address address = null;
                if (resultSet.next()) {
                    String streetHome = resultSet.getString(ParameterName.ADDRESS);
                    String city = resultSet.getString(ParameterName.CITY);

                    address = new AddressBuilder()
                            .buildStreetHome(streetHome)
                            .buildCity(city)
                            .buildAddress();
                }
                return Optional.ofNullable(address);
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public long findPhoneByApplicationId(long applicationId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_PHONE_BY_APP_ID)) {

            statement.setLong(1, applicationId);
            long phone = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    phone = resultSet.getLong(ParameterName.PHONE);
                }
            }
            return phone;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public boolean addAddress(String login, Map<String, String> parameters) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ADDRESS)) {

            statement.setString(1, parameters.get(ParameterName.ADDRESS));
            statement.setString(2, parameters.get(ParameterName.CITY));
            statement.setString(3, login);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
    }

    @Override
    public boolean checkLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_LOGIN)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public boolean checkEmail(String email) throws DaoException {
        String sqlRequest = String.format(SQL_FIND_BY_PARAMETER, ParameterName.EMAIL);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public RoleType findRole(String login) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_LOGIN)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                RoleType role = RoleType.GUEST;
                if (resultSet.next()) {
                    String roleName = resultSet.getString(ParameterName.ROLE_TYPE);
                    role = RoleType.valueOf(roleName.toUpperCase());
                }
                return role;
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public boolean update(String login, Map<String, String> parameters) throws DaoException {
        StringBuilder parametersSQL = new StringBuilder();
        List<String> searchValues = fillParametersSQL(parameters, parametersSQL);
        String sqlRequest = String.format(SQL_UPDATE_PARAMETERS, parametersSQL);

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            for (int i = 0; i < searchValues.size(); i++) {
                String value = searchValues.get(i);
                statement.setString(i + 1, value);
            }
            statement.setString(searchValues.size() + 1, login);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
    }

    @Override
    public boolean updateContactParameters(String login, Map<String, String> parameters) throws DaoException {
        StringBuilder parametersSQL = new StringBuilder();
        List<String> searchValues = fillParametersSQL(parameters, parametersSQL);
        String sqlRequest = String.format(SQL_UPDATE_ADDRESS, parametersSQL);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            for (int i = 0; i < searchValues.size(); i++) {
                String value = searchValues.get(i);
                statement.setString(i + 1, value);
            }
            statement.setString(searchValues.size() + 1, login);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public boolean updatePhone(String login, long phone) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PHONE)) {

            statement.setLong(1, phone);
            statement.setString(2, login);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
    }

    @Override
    public boolean updatePassword(String login, String newPassword) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {

            statement.setString(1, newPassword);
            statement.setString(2, login);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
    }

    @Override
    public boolean updateActivation(String login, int status) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACTIVATION)) {

            statement.setInt(1, status);
            statement.setString(2, login);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
    }

    @Override
    public boolean updateBlock(String login, int status) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BLOCK)) {

            statement.setInt(1, status);
            statement.setString(2, login);
            int result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException e) {
            throw new DaoException("Connection error. " + e, e);
        }
    }
}
