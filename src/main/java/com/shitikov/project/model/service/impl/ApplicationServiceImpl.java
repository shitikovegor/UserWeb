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
import com.shitikov.project.util.validator.AddressDateValidator;
import com.shitikov.project.util.validator.ApplicationValidator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
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
        boolean areTextDateFieldsValid = true;

        if (!ApplicationValidator.checkTitle(parameters.get(TITLE))) {
            areTextDateFieldsValid = false;
            parameters.replace(TITLE, "");
        }
        if (!ApplicationValidator.checkDescription(parameters.get(DESCRIPTION))) {
            areTextDateFieldsValid = false;
            parameters.replace(DESCRIPTION, "");
        }
        if (!AddressDateValidator.checkDate(parameters.get(DEPARTURE_DATE))) {
            areTextDateFieldsValid = false;
            parameters.replace(DEPARTURE_DATE, "");
        }
        if (!AddressDateValidator.checkDate(parameters.get(ARRIVAL_DATE))) {
            areTextDateFieldsValid = false;
            parameters.replace(ARRIVAL_DATE, "");
        }

        try {
            if (areTextDateFieldsValid) {
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
    public boolean remove(String id) throws ServiceException {
        boolean isRemoved = false;
        try {
            if (ApplicationValidator.checkId(id)) {
                isRemoved = applicationDao.remove(Long.parseLong(id));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isRemoved;
    }

    @Override
    public Optional<Application> findById(String id) throws ServiceException {
        if (ApplicationValidator.checkId(id)) {
            try {
                Optional<Application> application = applicationDao.findById(Long.parseLong(id));
                return application;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Map<Application, OrderStatus> findByUser(User user) throws ServiceException {
        try {
            Map<Application, OrderStatus> applications = applicationDao.findByUser(user);
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
    public boolean update(String id, Map<String, String> parameters) throws ServiceException {
        if (!ApplicationValidator.checkId(id)) {
            return false;
        }
        boolean isUpdated = ApplicationValidator.checkParameters(parameters);
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
                    String departureDate = paramToUpdate.get(DEPARTURE_DATE);
                    paramToUpdate.replace(DEPARTURE_DATE, dateToLong(departureDate).toString());
                    String arrivalDate = paramToUpdate.get(ARRIVAL_DATE);
                    paramToUpdate.replace(ARRIVAL_DATE, dateToLong(arrivalDate).toString());

                    isUpdated = applicationDao.update(Long.parseLong(id), paramToUpdate);
                }
            }
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException("Program error. ", e);
        }
    }

    private CargoApplication buildCargoApplication(Map<String, String> parameters) {
        CargoApplication application = new CargoApplicationBuilder()
                .buildCargoWeight(Double.parseDouble(parameters.get(CARGO_WEIGHT)))
                .buildCargoVolume(Double.parseDouble(parameters.get(CARGO_VOLUME)))
                .buildTitle(parameters.get(TITLE))
                .buildApplicationType(ApplicationType.CARGO)
                .buildDate(System.currentTimeMillis())
                .buildAddressTimeData(new AddressTimeDataBuilder()
                        .buildDepartureDate(dateToLong(parameters.get(DEPARTURE_DATE)))
                        .buildDepartureAddress(new AddressBuilder()
                                .buildStreetHome(parameters.get(DEPARTURE_ADDRESS))
                                .buildCity(parameters.get(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(dateToLong(parameters.get(ARRIVAL_DATE)))
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
                LocalDate.parse(parameters.get(DEPARTURE_DATE)).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long arrivalDate =
                LocalDate.parse(parameters.get(ARRIVAL_DATE)).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
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

    private Long dateToLong(String date) {
        return LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
