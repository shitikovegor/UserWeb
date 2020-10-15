package com.shitikov.project.model.service.impl;

import com.shitikov.project.util.ParameterName;
import com.shitikov.project.model.builder.UserBuilder;
import com.shitikov.project.model.dao.UserDao;
import com.shitikov.project.model.dao.impl.UserDaoImpl;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.util.PasswordEncoder;
import com.shitikov.project.validator.UserValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private static final String EXISTS = "exists";

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    // TODO: 28.09.2020 is correct method of check login and password?
    @Override
    public boolean checkLogin(String login) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        boolean isLoginCorrect = false;
        if (UserValidator.getInstance().checkLogin(login)) {
            try {
                isLoginCorrect = userDao.checkLogin(login);
            } catch (DaoException e) {
                throw new ServiceException("Program exception. ", e);
            }
        }
        return isLoginCorrect;
    }

    @Override
    public boolean checkPassword(String login, String password) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        boolean isPasswordCorrect = false;
        if (UserValidator.getInstance().checkPassword(password)) {
            try {
                String hashedPassword = userDao.findPassword(login, password);
                if (!hashedPassword.isEmpty()) {
                    isPasswordCorrect = new PasswordEncoder().checkPassword(password, hashedPassword);
                }
            } catch (DaoException e) {
                throw new ServiceException("Program exception. ", e);
            }
        }
        return isPasswordCorrect;
    }

    @Override
    public RoleType findRole(String login) throws ServiceException {
        try {
            return UserDaoImpl.getInstance().findRole(login);
        } catch (DaoException e) {
            throw new ServiceException("Getting role error. ", e);
        }
    }

    @Override
    public boolean add(Map<String, String> parameters) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        UserValidator validator = UserValidator.getInstance();
        boolean isUserAdded = false;

        if (validator.checkParameters(parameters)) {
            RoleType roleType = RoleType.valueOf(parameters.get(ParameterName.ROLE_TYPE).toUpperCase());
            SubjectType subjectType = SubjectType.valueOf(parameters.get(ParameterName.SUBJECT_TYPE).toUpperCase());
            String name = parameters.get(ParameterName.NAME).replaceAll("</?script>", "");
            String surname = parameters.get(ParameterName.SURNAME).replaceAll("</?script>", "");
            String email = parameters.get(ParameterName.EMAIL);

            String hashedPassword = new PasswordEncoder().hashPassword(parameters.get(ParameterName.PASSWORD));
            User user = new UserBuilder()
                    .buildLogin(parameters.get(ParameterName.LOGIN))
                    .buildName(name)
                    .buildSurname(surname)
                    .buildEmail(email)
                    .buildPhone(Long.parseLong(parameters.get(ParameterName.PHONE)))
                    .buildSubjectType(subjectType)
                    .buildRoleType(roleType)
                    .buildUser();
            try {
                boolean isUserInBase = userDao.isUserInBase(user);
                boolean isEmailInBase = userDao.isEmailInBase(email);

                if (!isUserInBase && !isEmailInBase) {
                    isUserAdded = userDao.add(user, hashedPassword);
                }

                if (isUserInBase) {
                    parameters.replace(ParameterName.LOGIN, EXISTS);
                }
                if (isEmailInBase) {
                    parameters.replace(ParameterName.EMAIL, EXISTS);
                }
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
    public Optional<User> findByLogin(String login) throws ServiceException {
        try {
            return UserDaoImpl.getInstance().findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Program error. ", e);
        }
    }

    @Override
    public boolean updatePassword(String newPassword) {
        return false;
    }

    @Override
    public boolean activate(String login) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            return userDao.activate(login);
        } catch (DaoException e) {
            throw new ServiceException("Program error of user's activation. ", e);
        }
    }
}
