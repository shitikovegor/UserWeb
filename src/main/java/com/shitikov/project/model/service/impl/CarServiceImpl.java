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
import com.shitikov.project.validator.CarValidator;

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
    public boolean add(Map<String, String> parameters, String login) throws ServiceException {
        boolean isAdded = false;
        String carNumber = parameters.get(ParameterName.CAR_NUMBER);
        String carryingWeight = parameters.get(ParameterName.CARRYING_WEIGHT);
        String carryingVolume = parameters.get(ParameterName.CARRYING_VOLUME);
        String passengersNumber = parameters.get(ParameterName.PASSENGERS_NUMBER);

        boolean areParametersValid = CarValidator.checkCarNumber(carNumber)
                && CarValidator.checkCarrying(carryingWeight)
                && CarValidator.checkCarrying(carryingVolume)
                && CarValidator.checkPassenger(passengersNumber);

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
    public boolean remove(String carNumber) throws ServiceException {
        try {
            return carDao.removeByCarNumber(carNumber);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Car> findByNumber(String carNumber) throws ServiceException {
        Optional<Car> carOptional = Optional.empty();
        try {
            if (CarValidator.checkCarNumber(carNumber)) {
                carOptional = carDao.findByNumber(carNumber);
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
    public boolean update(String carNumber, Map<String, String> parameters) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateById(long carId, Map<String, String> parameters) throws ServiceException {
        boolean areParametersValid = true;

        String numberToChange = parameters.get(ParameterName.CAR_NUMBER);
        if (numberToChange != null && !CarValidator.checkCarNumber(numberToChange)) {
            parameters.replace(ParameterName.CAR_NUMBER, "");
            areParametersValid = false;
        }
        String weightToChange = parameters.get(ParameterName.CARRYING_WEIGHT);
        if (weightToChange != null && !CarValidator.checkCarrying(weightToChange)) {
            parameters.replace(ParameterName.CARRYING_WEIGHT, "");
            areParametersValid = false;
        }
        String volumeToChange = parameters.get(ParameterName.CARRYING_VOLUME);
        if (volumeToChange != null && !CarValidator.checkCarrying(volumeToChange)) {
            parameters.replace(ParameterName.CARRYING_VOLUME, "");
            areParametersValid = false;
        }
        String passengerToChange = parameters.get(ParameterName.PASSENGERS_NUMBER);
        if (passengerToChange != null && !CarValidator.checkPassenger(passengerToChange)) {
            parameters.replace(ParameterName.PASSENGERS_NUMBER, "");
            areParametersValid = false;
        }

        try {
            Map<String, String> paramToUpdate = new HashMap<>(parameters);

            for (Map.Entry<String, String> entry : paramToUpdate.entrySet()) {
                String element = entry.getValue();
                if (element.isEmpty()) {
                    paramToUpdate.remove(entry.getKey());
                }
            }
            boolean areParametersUpdated = false;
            if (!paramToUpdate.isEmpty() && areParametersValid) {
                areParametersUpdated = carDao.updateById(carId, paramToUpdate);
            }
            return areParametersUpdated;
        } catch (DaoException e) {
            throw new ServiceException("Program error. ", e);
        }
    }
}
