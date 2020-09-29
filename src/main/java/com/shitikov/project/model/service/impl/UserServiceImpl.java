package com.shitikov.project.model.service.impl;

import com.shitikov.project.controller.command.impl.ParameterName;
import com.shitikov.project.model.builder.UserBuilder;
import com.shitikov.project.model.dao.UserDao;
import com.shitikov.project.model.dao.impl.UserDaoImpl;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.util.PasswordCrypt;
import com.shitikov.project.validator.UserValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    // TODO: 28.09.2020 is correct method of check logi and password?
    @Override
    public boolean checkLogin(String login, String password) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        boolean isLoginCorrect = false;
        if (UserValidator.getInstance().checkLogin(login)
                && UserValidator.getInstance().checkPassword(password)) {
            try {
                if (userDao.checkLogin(login)) {
                    String hashedPassword = userDao.getPassword(login, password);
                    if (!hashedPassword.isEmpty()) {
                        isLoginCorrect = new PasswordCrypt().checkPassword(password, hashedPassword);
                    }
                }
            } catch (DaoException e) {
                throw new ServiceException("Program exception. ", e);
            }
        }
        return isLoginCorrect;
    }

    @Override
    public RoleType getRole(String login) throws ServiceException {
        try {
            return UserDaoImpl.getInstance().getRole(login);
        } catch (DaoException e) {
            throw new ServiceException("Getting role error. ", e);
        }
    }

    @Override
    public boolean add(Map<String, String> parameters) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        UserValidator validator = UserValidator.getInstance();
        boolean isUserAdded = false;

        String login = parameters.get(ParameterName.LOGIN);
        String password = parameters.get(ParameterName.PASSWORD);
        String email = parameters.get(ParameterName.EMAIL);
        String phone = parameters.get(ParameterName.PHONE);
        Optional<RoleType> roleTypeOptional = getRoleType(parameters.get(ParameterName.ROLE_TYPE));
        Optional<SubjectType> subjectTypeOptional = getSubjectType(parameters.get(ParameterName.SUBJECT_TYPE));
        String name = parameters.get(ParameterName.NAME).replaceAll("</?script>", "");
        String surname = parameters.get(ParameterName.SURNAME).replaceAll("</?script>", "");

        if (validator.checkParameters(login, password, email, phone)
                && roleTypeOptional.isPresent()
                && subjectTypeOptional.isPresent()) {

            String hashedPassword = new PasswordCrypt().hashPassword(password);
            User user = new UserBuilder()
                    .buildLogin(login)
                    .buildName(name)
                    .buildSurname(surname)
                    .buildEmail(email)
                    .buildPhone(Long.parseLong(phone))
                    .buildSubjectType(subjectTypeOptional.get())
                    .buildRoleType(roleTypeOptional.get())
                    .buildUser();
            try {
                isUserAdded = userDao.add(user, hashedPassword);
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
    public Optional<User> findByLogin(String login) throws ServiceException  {
        return Optional.empty();
    }

    @Override
    public boolean updatePassword(String newPassword) {
        return false;
    }


    private Optional<RoleType> getRoleType(String roleTypeInput) {
        Optional<RoleType> roleTypeOptional = Arrays.stream(RoleType.values())
                .filter(roleType -> roleType.toString()
                        .equalsIgnoreCase(roleTypeInput.toUpperCase()))
                .findFirst();

        return roleTypeOptional;
    }

    private Optional<SubjectType> getSubjectType(String subjectTypeInput) {
        Optional<SubjectType> subjectTypeOptional = Arrays.stream(SubjectType.values())
                .filter(subjectType -> subjectType.toString()
                        .equalsIgnoreCase(subjectTypeInput.toUpperCase()))
                .findFirst();

        return subjectTypeOptional;
    }
}
