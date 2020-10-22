package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.builder.CarBuilder;
import com.shitikov.project.model.dao.CarDao;
import com.shitikov.project.model.dao.impl.CarDaoImpl;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.CarService;
import com.shitikov.project.util.ParameterName;
import com.shitikov.project.validator.CarValidator;

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
        double carryingWeight = Double.parseDouble(parameters.get(ParameterName.CARRYING_WEIGHT));
        double carryingVolume = Double.parseDouble(parameters.get(ParameterName.CARRYING_VOLUME));
        int passengersNumber = Integer.parseInt(parameters.get(ParameterName.PASSENGERS_NUMBER));

        try {
            if (CarValidator.checkCarNumber(carNumber) && !carDao.checkCarNumber(carNumber)) {
                Car carToAdd = new CarBuilder()
                        .buildCarNumber(carNumber)
                        .buildCarryingWeight(carryingWeight)
                        .buildCarryingVolume(carryingVolume)
                        .buildPassengers(passengersNumber)
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
        return false;
    }

    @Override
    public Optional<Car> findByNumber(String carNumber) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean updateParameters(String carNumber, Map<String, String> parameters) throws ServiceException {
        return false;
    }
}
