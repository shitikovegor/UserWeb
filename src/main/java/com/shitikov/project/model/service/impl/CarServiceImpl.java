package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.builder.CarBuilder;
import com.shitikov.project.model.dao.impl.CarDaoImpl;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.CarService;
import com.shitikov.project.util.ParameterName;
import com.shitikov.project.validator.ApplicationValidator;
import com.shitikov.project.validator.CarValidator;
import com.shitikov.project.validator.UserValidator;
import com.shitikov.project.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Car service.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class CarServiceImpl implements CarService {
    private static CarServiceImpl instance;

    private CarServiceImpl() {
    }

    public static CarServiceImpl getInstance() {
        if (instance == null) {
            instance = new CarServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, String> parameters) throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        boolean isAdded = false;
        String carNumber = parameters.get(ParameterName.CAR_NUMBER);
        String carryingWeight = parameters.get(ParameterName.CARRYING_WEIGHT);
        String carryingVolume = parameters.get(ParameterName.CARRYING_VOLUME);
        String passengersNumber = parameters.get(ParameterName.PASSENGERS_NUMBER);
        String login = parameters.get(ParameterName.LOGIN);

        boolean areParametersValid = CarValidator.checkCarNumber(carNumber)
                && ApplicationValidator.checkCargo(carryingWeight)
                && ApplicationValidator.checkCargo(carryingVolume)
                && ApplicationValidator.checkPassenger(passengersNumber)
                && UserValidator.checkLogin(login);

        try {
            if (areParametersValid && !carDao.checkCarNumber(carNumber)) {
                Car carToAdd = new CarBuilder()
                        .buildCarNumber(carNumber)
                        .buildCarryingWeight(Double.parseDouble(carryingWeight))
                        .buildCarryingVolume(Double.parseDouble(carryingVolume))
                        .buildPassengers(Integer.parseInt(passengersNumber))
                        .buildCar();

                isAdded = carDao.add(carToAdd, login);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isAdded;
    }

    @Override
    public boolean remove(String id) throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        try {
            boolean isRemoved = false;
            if (Validator.checkId(id)) {
                isRemoved = carDao.remove(Long.parseLong(id));
            }
            return isRemoved;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Car> findById(String id) throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        Optional<Car> carOptional = Optional.empty();
        try {
            if (Validator.checkId(id)) {
                carOptional = carDao.findById(Long.parseLong(id));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return carOptional;
    }

    @Override
    public List<Car> findByUser(User user) throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        try {
            List<Car> cars = carDao.findByUser(user);
            return cars;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(String id, Map<String, String> parameters) throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        if (!Validator.checkId(id)) {
            return false;
        }
        boolean isUpdated = CarValidator.checkParameters(parameters);
        try {
            if (isUpdated) {
                Map<String, String> paramToUpdate = new HashMap<>(parameters);
                paramToUpdate.entrySet().removeIf(e -> e.getValue().isEmpty());

                if (!paramToUpdate.isEmpty()) {
                    isUpdated = carDao.update(Long.parseLong(id), paramToUpdate);
                }
            }
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeUsed(String id) throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        try {
            boolean isRemoved = false;
            if (Validator.checkId(id)) {
                isRemoved = carDao.removeUsed(Long.parseLong(id));
            }
            return isRemoved;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Car> findAvailableByUser(User user) throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        try {
            List<Car> cars = carDao.findAvailableByUser(user);
            return cars;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
