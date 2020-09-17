package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.builder.UserBuilder;
import com.shitikov.project.model.dao.UserDao;
import com.shitikov.project.model.dao.impl.UserDaoImpl;
import com.shitikov.project.model.entity.RoleType;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.validator.UserValidator;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean checkLogin(String login, String password) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        boolean isLoginCorrect = false;
        if (UserValidator.getInstance().checkLoginPassword(login, password)) {
            try {
                String hashedPassword = userDao.checkLogin(login, password);
                isLoginCorrect = BCrypt.checkpw(password, hashedPassword);
            } catch (DaoException e) {
                throw new ServiceException("Program exception. ", e);
            }
        }
        return isLoginCorrect;
    }

    @Override
    public boolean add(String login, String password, RoleType roleType) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        boolean isUserAdded = false;

        if (UserValidator.getInstance().checkLoginPassword(login, password)) {
            String hashedPassword = hashPassword(password);
            User user = new UserBuilder()
                    .buildLogin(login)
                    .buildPassword(hashedPassword)
                    .buildRoleType(roleType)
                    .buildUser();
            try {
                isUserAdded = userDao.add(user);
            } catch (DaoException e) {
                throw new ServiceException("Program error of adding user. ", e);
            }
        }
        return isUserAdded;
    }

    @Override
    public boolean remove(String login) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean updatePassword(String newPassword) {
        return false;
    }

    private String hashPassword(String password) {
        String salt = BCrypt.gensalt(10);
        String hashPassword = BCrypt.hashpw(password, salt);
        return hashPassword;
    }
}
