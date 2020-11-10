package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.builder.UserBuilder;
import com.shitikov.project.model.dao.impl.UserDaoImpl;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.UserService;
import com.shitikov.project.util.ParameterName;
import com.shitikov.project.util.PasswordEncoder;
import com.shitikov.project.validator.AddressDateValidator;
import com.shitikov.project.validator.UserValidator;
import com.shitikov.project.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.shitikov.project.controller.command.AttributeName.EMPTY_LINE;
import static com.shitikov.project.controller.command.AttributeName.XSS_PATTERN;

/**
 * The type User service.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    private static final String EXISTS = "exists";
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
    public boolean add(Map<String, String> parameters) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean isUserAdded = false;
        String login = parameters.get(ParameterName.LOGIN);
        String email = parameters.get(ParameterName.EMAIL);

        if (UserValidator.checkParameters(parameters)) {
            try {
                boolean isUserInBase = userDao.checkLogin(login);
                boolean isEmailInBase = userDao.checkEmail(email);

                if (!isUserInBase && !isEmailInBase) {
                    RoleType roleType = RoleType.valueOf(parameters.get(ParameterName.ROLE_TYPE).toUpperCase());
                    SubjectType subjectType = SubjectType.valueOf(parameters.get(ParameterName.SUBJECT_TYPE).toUpperCase());
                    String name = parameters.get(ParameterName.NAME)
                            .replaceAll(XSS_PATTERN, EMPTY_LINE);
                    String surname = parameters.get(ParameterName.SURNAME)
                            .replaceAll(XSS_PATTERN, EMPTY_LINE);

                    String hashedPassword = PasswordEncoder.hashPassword(parameters.get(ParameterName.PASSWORD));
                    User user = new UserBuilder()
                            .buildLogin(login)
                            .buildName(name)
                            .buildSurname(surname)
                            .buildEmail(email)
                            .buildPhone(Long.parseLong(parameters.get(ParameterName.PHONE)))
                            .buildSubjectType(subjectType)
                            .buildRoleType(roleType)
                            .buildUser();
                    isUserAdded = userDao.add(user, hashedPassword);
                }

                if (isUserInBase) {
                    parameters.replace(ParameterName.LOGIN, EXISTS);
                }
                if (isEmailInBase) {
                    parameters.replace(ParameterName.EMAIL, EXISTS);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isUserAdded;
    }

    @Override
    public boolean addUserAddress(String login, Map<String, String> parameters) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean isUserAddressAdded = false;

        boolean areParametersValid = UserValidator.checkLogin(login)
                && AddressDateValidator.checkAddress(parameters.get(ParameterName.ADDRESS))
                && AddressDateValidator.checkCity(parameters.get(ParameterName.CITY));

        if (areParametersValid) {
            try {
                if (userDao.checkLogin(login)) {
                    isUserAddressAdded = userDao.addAddress(login, parameters);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isUserAddressAdded;
    }

    @Override
    public boolean checkLogin(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean isLoginCorrect = false;
        if (UserValidator.checkLogin(login)) {
            try {
                isLoginCorrect = userDao.checkLogin(login);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isLoginCorrect;
    }

    @Override
    public boolean checkPassword(String login, String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean isPasswordCorrect = false;
        if (UserValidator.checkPassword(password)) {
            try {
                String hashedPassword = userDao.findPassword(login);
                if (!hashedPassword.isEmpty()) {
                    isPasswordCorrect = PasswordEncoder.checkPassword(password, hashedPassword);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isPasswordCorrect;
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public RoleType findRole(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findRole(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Address> findAddress(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findUserAddress(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long findPhoneByApplicationId(String applicationId) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            long phone = 0;
            if (Validator.checkId(applicationId)) {
                phone = userDao.findPhoneByApplicationId(Long.parseLong(applicationId));
            }
            return phone;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(String login, Map<String, String> parameters) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String loginToChange = parameters.get(ParameterName.LOGIN);
        boolean isLoginValid = UserValidator.checkLogin(loginToChange);
        if (!isLoginValid) {
            parameters.replace(ParameterName.LOGIN, EMPTY_LINE);
        }
        String nameToChange = parameters.get(ParameterName.NAME);
        if (!UserValidator.checkName(nameToChange)) {
            parameters.replace(ParameterName.NAME, EMPTY_LINE);
        }
        String surnameToChange = parameters.get(ParameterName.SURNAME);
        if (!UserValidator.checkName(surnameToChange)) {
            parameters.replace(ParameterName.SURNAME, EMPTY_LINE);
        }
        String emailToChange = parameters.get(ParameterName.EMAIL);
        boolean isEmailValid = UserValidator.checkEmail(emailToChange);
        if (!isEmailValid) {
            parameters.replace(ParameterName.EMAIL, EMPTY_LINE);
        }

        try {
            if (isLoginValid && userDao.checkLogin(loginToChange)) {
                parameters.replace(ParameterName.LOGIN, EXISTS);
            }
            if (isEmailValid && userDao.checkEmail(emailToChange)) {
                parameters.replace(ParameterName.EMAIL, EXISTS);
            }

            Map<String, String> paramToUpdate = new HashMap<>(parameters);
            paramToUpdate.entrySet().removeIf(
                    e -> (e.getValue().isEmpty() || e.getValue().equals(EXISTS)));
            boolean areParametersUpdated = false;
            if (!paramToUpdate.isEmpty()) {
                areParametersUpdated = userDao.update(login, paramToUpdate);
            }
            return areParametersUpdated;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateContactParameters(String login, Map<String, String> parameters) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean areParametersCorrect = true;
        String streetHomeToChange = parameters.get(ParameterName.ADDRESS);
        if (streetHomeToChange != null && !AddressDateValidator.checkAddress(streetHomeToChange)) {
            parameters.replace(ParameterName.ADDRESS, EMPTY_LINE);
            areParametersCorrect = false;
        }
        String cityToChange = parameters.get(ParameterName.CITY);
        if (cityToChange != null && !AddressDateValidator.checkCity(cityToChange)) {
            parameters.replace(ParameterName.CITY, EMPTY_LINE);
            areParametersCorrect = false;
        }

        try {
            boolean isUpdated = false;
            if (areParametersCorrect) {
                Map<String, String> paramToUpdate = new HashMap<>(parameters);
                paramToUpdate.entrySet().removeIf(
                        e -> (e.getValue().isEmpty() || e.getValue().equals(EXISTS)));
                if (!paramToUpdate.isEmpty()) {
                    isUpdated = userDao.updateContactParameters(login, paramToUpdate);
                }
            }
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updatePhone(String login, String phone) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean isUpdated = false;
        if (UserValidator.checkPhone(phone)) {
            long phoneNumber = Long.parseLong(phone);
            try {
                isUpdated = userDao.updatePhone(login, phoneNumber);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isUpdated;
    }

    @Override
    public boolean updatePassword(String login, String newPassword) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean isUpdated = false;
        if (UserValidator.checkPassword(newPassword)) {
            String hashedPassword = PasswordEncoder.hashPassword(newPassword);
            try {
                isUpdated = userDao.updatePassword(login, hashedPassword);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isUpdated;
    }

    @Override
    public boolean activate(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.updateActivation(login, 1);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean block(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.updateBlock(login, 1);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean unblock(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.updateBlock(login, 0);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
