package com.shitikov.project.model.dao.impl;

import com.shitikov.project.model.dao.UserDao;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.PoolException;
import com.shitikov.project.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final String SQL_INSERT
            = "INSERT INTO users(login, password, role) VALUES(?,?,?)";
    private static final String SQL_FIND_BY_LOGIN
            = "SELECT id, login, password, role FROM users WHERE login = ?";
    private static final String SQL_FIND_USER
            = "SELECT id, login, password, role FROM users WHERE login = ? AND role = ?";

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
    public String checkLogin(String login, String password) throws DaoException {
        if (login == null || password == null) {
            throw new DaoException("Login or password is null.");
        }
        String hashedPassword = "";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_LOGIN)){

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString(ColumnName.PASSWORD);
                }
            }
        } catch (PoolException | SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return hashedPassword;
    }

    @Override
    public boolean updatePassword(String newPassword) throws DaoException {
        return false;
    }

    @Override
    public boolean add(User user, String password) throws DaoException {
        boolean isUserAdded = false;
        if (user == null) {
            throw new DaoException("Book is null.");
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

            if (!isUserInDataBase(connection, user)) {
                try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
                    statement.setString(1, user.getLogin());
                    statement.setString(2, password);
                    statement.setString(3, user.getRoleType().getRoleName());
                    int result = statement.executeUpdate();
                    isUserAdded = result != 0;
                }
            }
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
    public List<User> findAll() throws DaoException {
        return null;
    }

    private boolean isUserInDataBase(Connection connection, User user) throws DaoException {
        boolean isBookInLibrary;
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getRoleType().getRoleName());
            try (ResultSet resultSet = statement.executeQuery()) {
                isBookInLibrary = resultSet.next();
            }
        } catch (SQLException e) {
            throw new DaoException("Connection error. ", e);
        }
        return isBookInLibrary;
    }
}
