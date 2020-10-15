package com.shitikov.project.model.dao.impl;

import com.shitikov.project.model.builder.UserBuilder;
import com.shitikov.project.model.dao.UserDao;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.PoolException;
import com.shitikov.project.model.pool.ConnectionPool;
import com.shitikov.project.util.ParameterName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final String SQL_INSERT
            = "INSERT INTO users(login, password, name, surname, email, phone, role, subject, blocked, active)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_CHECK_BY_LOGIN
            = "SELECT userID, login, password, role FROM users WHERE login = ?";

    private static final String SQL_FIND_BY_PARAMETER
            = "SELECT userID, login, name, surname, email, phone, role, subject, blocked, active FROM users WHERE %s = ?";

    private static final String SQL_ACTIVATE_ACCOUNT
            = "UPDATE users SET active = 1 WHERE login = ?";

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
    public boolean checkLogin(String login) throws DaoException {
        if (login == null) {
            throw new DaoException("Login is null.");
        }
        boolean loginExists = false;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_LOGIN)) {

            statement.setString(1, login);
            loginExists = statement.execute();
        } catch (PoolException | SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return loginExists;
    }

    @Override
    public String findPassword(String login, String password) throws DaoException {
        if (login == null || password == null) {
            throw new DaoException("Login or password is null.");
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
        } catch (PoolException | SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return hashedPassword;
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
        } catch (PoolException | SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return role;
    }

    @Override
    public boolean updatePassword(String newPassword) throws DaoException {
        return false;
    }

    @Override
    public boolean add(User user, String password) throws DaoException {
        boolean isUserAdded = false;
        if (user == null) {
            throw new DaoException("User is null.");
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {

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
        } catch (PoolException | SQLException se) {
            throw new DaoException("Connection error. ", se);
        }
        return isUserAdded;
    }

    @Override
    public boolean remove(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean remove(Long id) throws DaoException {
        return false;
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        return Optional.empty();
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
        } catch (PoolException | SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean isUserInBase(User user) throws DaoException {
        boolean isUserInBase;
        if (user == null) {
            throw new DaoException("User is null.");
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHECK_BY_LOGIN)) {

            statement.setString(1, user.getLogin());
            try (ResultSet resultSet = statement.executeQuery()) {
                isUserInBase = resultSet.next();
            }
        } catch (PoolException | SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isUserInBase;
    }

    @Override
    public boolean isEmailInBase(String email) throws DaoException {
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
        } catch (PoolException | SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isEmailInBase;
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
        } catch (PoolException | SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isActivated;
    }
}
