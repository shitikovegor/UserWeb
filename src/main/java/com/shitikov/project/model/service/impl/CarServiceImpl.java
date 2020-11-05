package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.builder.CarBuilder;
import com.shitikov.project.model.dao.CarDao;
import com.shitikov.project.model.dao.impl.CarDaoImpl;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.CarService;
import com.shitikov.project.util.ParameterName;
import com.shitikov.project.util.validator.CarValidator;
import com.shitikov.project.util.validator.UserValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CarServiceImpl implements CarService {
    private static CarServiceImpl instance;
    private CarDao carDao = new CarDaoImpl();

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
        boolean isAdded = false;
        String carNumber = parameters.get(ParameterName.CAR_NUMBER);
        String carryingWeight = parameters.get(ParameterName.CARRYING_WEIGHT);
        String carryingVolume = parameters.get(ParameterName.CARRYING_VOLUME);
        String passengersNumber = parameters.get(ParameterName.PASSENGERS_NUMBER);
        String login = parameters.get(ParameterName.LOGIN);

        boolean areParametersValid = CarValidator.checkCarNumber(carNumber)
                && CarValidator.checkCarrying(carryingWeight)
                && CarValidator.checkCarrying(carryingVolume)
                && CarValidator.checkPassenger(passengersNumber)
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
            throw new ServiceException("Program error. ", e);
        }
        return isAdded;
    }

    @Override
    public boolean remove(String id) throws ServiceException {
        try {
            boolean isRemoved = false;
            if (CarValidator.checkId(id)) {
                isRemoved = carDao.remove(Long.parseLong(id));
            }
            return isRemoved;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Car> findById(String id) throws ServiceException {
        Optional<Car> carOptional = Optional.empty();
        try {
            if (CarValidator.checkId(id)) {
                carOptional = carDao.findById(Long.parseLong(id));
            }
        } catch (DaoException e) {
            throw new ServiceException("Program error. ", e);
        }
        return carOptional;
    }

    @Override
    public List<Car> findByUser(User user) throws ServiceException {
        try {
            List<Car> cars = carDao.findByUser(user);
            return cars;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Car> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(String id, Map<String, String> parameters) throws ServiceException {
        if (!CarValidator.checkId(id)) {
            return false;
        }
        boolean isUpdated = CarValidator.checkParameters(parameters);
        try {
            if (isUpdated) {
                Map<String, String> paramToUpdate = new HashMap<>(parameters);

                for (Map.Entry<String, String> entry : paramToUpdate.entrySet()) {
                    String element = entry.getValue();
                    if (element.isEmpty()) {
                        paramToUpdate.remove(entry.getKey());
                    }
                }
                if (!paramToUpdate.isEmpty()) {
                    isUpdated = carDao.update(Long.parseLong(id), paramToUpdate);
                }
            }
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException("Program error. ", e);
        }
    }
}
