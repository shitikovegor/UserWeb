package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.builder.AddressBuilder;
import com.shitikov.project.model.builder.AddressTimeDataBuilder;
import com.shitikov.project.model.builder.CargoApplicationBuilder;
import com.shitikov.project.model.builder.PassengerApplicationBuilder;
import com.shitikov.project.model.dao.ApplicationDao;
import com.shitikov.project.model.dao.impl.ApplicationDaoImpl;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.ApplicationType;
import com.shitikov.project.model.entity.application.CargoApplication;
import com.shitikov.project.model.entity.application.PassengerApplication;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.validator.ApplicationValidator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.shitikov.project.util.ParameterName.*;

public class ApplicationServiceImpl implements ApplicationService {
    private static ApplicationServiceImpl instance;
    private ApplicationDao applicationDao = new ApplicationDaoImpl();

    private ApplicationServiceImpl() {
    }

    public static ApplicationServiceImpl getInstance() {
        if (instance == null) {
            instance = new ApplicationServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, String> parameters, String login) throws ServiceException {
        boolean isAdded = false;
        boolean areTextFieldsValid = true;

        if (!ApplicationValidator.checkTitle(parameters.get(TITLE))) {
            areTextFieldsValid = false;
            parameters.replace(TITLE, "");
        }

        if (!ApplicationValidator.checkDescription(parameters.get(DESCRIPTION))) {
            areTextFieldsValid = false;
            parameters.replace(DESCRIPTION, "");
        }

        try {
            if (areTextFieldsValid) {
                if (ApplicationType.valueOf(parameters.get(APPLICATION_TYPE)
                        .toUpperCase()) == ApplicationType.CARGO) {

                    boolean isCargoValid = true;
                    if (!ApplicationValidator.checkCargo(parameters.get(CARGO_WEIGHT))) {
                        isCargoValid = false;
                        parameters.replace(CARGO_WEIGHT, "");
                    }
                    if (!ApplicationValidator.checkCargo(parameters.get(CARGO_VOLUME))) {
                        isCargoValid = false;
                        parameters.replace(CARGO_VOLUME, "");
                    }

                    if (isCargoValid) {
                        CargoApplication cargoApplication = buildCargoApplication(parameters);
                        isAdded = applicationDao.add(cargoApplication, login);
                    }
                } else if (ApplicationType.valueOf(parameters.get(APPLICATION_TYPE)
                        .toUpperCase()) == ApplicationType.PASSENGER) {
                    if (ApplicationValidator.checkPassenger(parameters.get(PASSENGERS_NUMBER))) {
                        PassengerApplication passengerApplication = buildPassengerApplication(parameters);
                        isAdded = applicationDao.add(passengerApplication, login);
                    } else {
                        parameters.replace(PASSENGERS_NUMBER, "");
                    }
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Program error. ", e);
        }
        return isAdded;
    }

    @Override
    public boolean remove(long id) throws ServiceException {
        return false;
    }

    @Override
    public Optional<Application> findById(long id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Map<OrderStatus, Application> findByUser(User user) throws ServiceException {
        try {
            Map<OrderStatus, Application> applications = applicationDao.findByUser(user);
            return applications;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Application> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean updateParameters(String carNumber, Map<String, String> parameters) throws ServiceException {
        return false;
    }

    private CargoApplication buildCargoApplication(Map<String, String> parameters) {
        long departureDate =
                LocalDateTime.parse(parameters.get(DEPARTURE_DATE)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long arrivalDate =
                LocalDateTime.parse(parameters.get(ARRIVAL_DATE)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        CargoApplication application = new CargoApplicationBuilder()
                .buildCargoWeight(Double.parseDouble(parameters.get(CARGO_WEIGHT)))
                .buildCargoVolume(Double.parseDouble(parameters.get(CARGO_VOLUME)))
                .buildTitle(parameters.get(TITLE))
                .buildApplicationType(ApplicationType.CARGO)
                .buildDate(System.currentTimeMillis())
                .buildAddressTimeData(new AddressTimeDataBuilder()
                        .buildDepartureDate(departureDate)
                        .buildDepartureAddress(new AddressBuilder()
                                .buildStreetHome(parameters.get(DEPARTURE_ADDRESS))
                                .buildCity(parameters.get(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(arrivalDate)
                        .buildArrivalAddress(new AddressBuilder()
                                .buildStreetHome(parameters.get(ARRIVAL_ADDRESS))
                                .buildCity(parameters.get(ARRIVAL_CITY))
                                .buildAddress())
                        .buildAddressTimeData())
                .buildDescription(parameters.get(DESCRIPTION))
                .buildApplication();

        return application;
    }

    private PassengerApplication buildPassengerApplication(Map<String, String> parameters) {
        long departureDate =
                LocalDateTime.parse(parameters.get(DEPARTURE_DATE)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long arrivalDate =
                LocalDateTime.parse(parameters.get(ARRIVAL_DATE)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        PassengerApplication application = new PassengerApplicationBuilder()
                .buildPassengersNumber(Integer.parseInt(parameters.get(PASSENGERS_NUMBER)))
                .buildTitle(parameters.get(TITLE))
                .buildApplicationType(ApplicationType.PASSENGER)
                .buildDate(System.currentTimeMillis())
                .buildAddressTimeData(new AddressTimeDataBuilder()
                        .buildDepartureDate(departureDate)
                        .buildDepartureAddress(new AddressBuilder()
                                .buildStreetHome(parameters.get(DEPARTURE_ADDRESS))
                                .buildCity(parameters.get(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(arrivalDate)
                        .buildArrivalAddress(new AddressBuilder()
                                .buildStreetHome(parameters.get(ARRIVAL_ADDRESS))
                                .buildCity(parameters.get(ARRIVAL_CITY))
                                .buildAddress())
                        .buildAddressTimeData())
                .buildDescription(parameters.get(DESCRIPTION))
                .buildApplication();

        return application;
    }
}
