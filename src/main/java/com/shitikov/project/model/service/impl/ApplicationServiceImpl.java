package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.dao.ApplicationDao;
import com.shitikov.project.model.dao.impl.ApplicationDaoImpl;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.AddressTimeData;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.CargoApplication;
import com.shitikov.project.model.entity.application.PassengerApplication;
import com.shitikov.project.model.entity.type.ApplicationType;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.ApplicationService;
import com.shitikov.project.validator.AddressDateValidator;
import com.shitikov.project.validator.ApplicationValidator;
import com.shitikov.project.validator.OrderValidator;
import com.shitikov.project.validator.Validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.shitikov.project.controller.command.AttributeName.EMPTY_LINE;
import static com.shitikov.project.util.ParameterName.*;

/**
 * The type Application service.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class ApplicationServiceImpl implements ApplicationService {
    private static ApplicationServiceImpl instance;

    private ApplicationServiceImpl() {
    }

    public static ApplicationServiceImpl getInstance() {
        if (instance == null) {
            instance = new ApplicationServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, String> parameters) throws ServiceException {
        ApplicationDao applicationDao = ApplicationDaoImpl.getInstance();
        boolean isAdded = false;
        String login = parameters.get(LOGIN);

        boolean areTextDateFieldsValid = ApplicationValidator.checkParameters(parameters);
        if (parameters.containsKey(DEPARTURE_DATE)) {
            String departureDate = parameters.get(DEPARTURE_DATE);
            parameters.replace(DEPARTURE_DATE, dateToLong(departureDate).toString());
        }
        if (parameters.containsKey(ARRIVAL_DATE)) {
            String departureDate = parameters.get(ARRIVAL_DATE);
            parameters.replace(ARRIVAL_DATE, dateToLong(departureDate).toString());
        }

        try {
            if (areTextDateFieldsValid) {
                if (ApplicationType.valueOf(parameters.get(APPLICATION_TYPE)
                        .toUpperCase()) == ApplicationType.CARGO) {

                    boolean isCargoValid = true;
                    if (!ApplicationValidator.checkCargo(parameters.get(CARGO_WEIGHT))) {
                        isCargoValid = false;
                        parameters.replace(CARGO_WEIGHT, EMPTY_LINE);
                    }
                    if (!ApplicationValidator.checkCargo(parameters.get(CARGO_VOLUME))) {
                        isCargoValid = false;
                        parameters.replace(CARGO_VOLUME, EMPTY_LINE);
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
                        parameters.replace(PASSENGERS_NUMBER, EMPTY_LINE);
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
        ApplicationDao applicationDao = ApplicationDaoImpl.getInstance();
        boolean isRemoved = false;
        try {
            if (Validator.checkId(id)) {
                isRemoved = applicationDao.remove(Long.parseLong(id));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isRemoved;
    }

    @Override
    public Optional<Application> findById(String id) throws ServiceException {
        ApplicationDao applicationDao = ApplicationDaoImpl.getInstance();
        if (Validator.checkId(id)) {
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
        ApplicationDao applicationDao = ApplicationDaoImpl.getInstance();
        try {
            Map<Application, OrderStatus> applications = applicationDao.findByUser(user);
            return applications;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Application, OrderStatus> findAll() throws ServiceException {
        ApplicationDao applicationDao = ApplicationDaoImpl.getInstance();
        try {
            Map<Application, OrderStatus> applications = applicationDao.findAll();
            return applications;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Application, OrderStatus> findByParameters(Map<String, String> parameters) throws ServiceException {
        ApplicationDao applicationDao = ApplicationDaoImpl.getInstance();
        try {
            Map<String, Object> validParameters = fillValidParameters(parameters);
            Map<Application, OrderStatus> applications = applicationDao.findByParameters(validParameters);
            return applications;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(String id, Map<String, String> parameters) throws ServiceException {
        ApplicationDao applicationDao = ApplicationDaoImpl.getInstance();
        if (!Validator.checkId(id)) {
            return false;
        }
        boolean isUpdated;
        if (parameters.get(APPLICATION_TYPE).equals(CARGO)) {
            isUpdated = ApplicationValidator.checkCargoAppParameters(parameters);
        } else {
            isUpdated = ApplicationValidator.checkPassengerAppParameters(parameters);
        }
        try {
            if (isUpdated) {
                Map<String, String> paramToUpdate = new HashMap<>(parameters);
                paramToUpdate.entrySet().removeIf(e -> e.getValue().isEmpty());
                if (!paramToUpdate.isEmpty()) {
                    if (paramToUpdate.containsKey(DEPARTURE_DATE)) {
                        String departureDate = paramToUpdate.get(DEPARTURE_DATE);
                        paramToUpdate.replace(DEPARTURE_DATE, dateToLong(departureDate).toString());

                    }
                    if (paramToUpdate.containsKey(ARRIVAL_DATE)) {
                        String arrivalDate = paramToUpdate.get(ARRIVAL_DATE);
                        paramToUpdate.replace(ARRIVAL_DATE, dateToLong(arrivalDate).toString());
                    }
                    isUpdated = applicationDao.update(Long.parseLong(id), paramToUpdate);
                }
            }
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException("Program error. ", e);
        }
    }

    @Override
    public Map<Application, OrderStatus> findRecentActiveApps(int numberOfApps) throws ServiceException {
        ApplicationDao applicationDao = ApplicationDaoImpl.getInstance();
        try {
            Map<Application, OrderStatus> applications = applicationDao.findRecentActiveApps(numberOfApps);
            return applications;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private CargoApplication buildCargoApplication(Map<String, String> parameters) {
        CargoApplication application = CargoApplication.newBuilder()
                .buildCargoWeight(Double.parseDouble(parameters.get(CARGO_WEIGHT)))
                .buildCargoVolume(Double.parseDouble(parameters.get(CARGO_VOLUME)))
                .buildTitle(parameters.get(TITLE))
                .buildApplicationType(ApplicationType.CARGO)
                .buildDate(System.currentTimeMillis())
                .buildAddressTimeData(AddressTimeData.newBuilder()
                        .buildDepartureDate(Long.parseLong(parameters.get(DEPARTURE_DATE)))
                        .buildDepartureAddress(Address.newBuilder()
                                .buildStreetHome(parameters.get(DEPARTURE_ADDRESS))
                                .buildCity(parameters.get(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(Long.parseLong(parameters.get(ARRIVAL_DATE)))
                        .buildArrivalAddress(Address.newBuilder()
                                .buildStreetHome(parameters.get(ARRIVAL_ADDRESS))
                                .buildCity(parameters.get(ARRIVAL_CITY))
                                .buildAddress())
                        .buildAddressTimeData())
                .buildDescription(parameters.get(DESCRIPTION))
                .buildApplication();

        return application;
    }

    private PassengerApplication buildPassengerApplication(Map<String, String> parameters) {
        PassengerApplication application = PassengerApplication.newBuilder()
                .buildPassengersNumber(Integer.parseInt(parameters.get(PASSENGERS_NUMBER)))
                .buildTitle(parameters.get(TITLE))
                .buildApplicationType(ApplicationType.PASSENGER)
                .buildDate(System.currentTimeMillis())
                .buildAddressTimeData(AddressTimeData.newBuilder()
                        .buildDepartureDate(Long.parseLong(parameters.get(DEPARTURE_DATE)))
                        .buildDepartureAddress(Address.newBuilder()
                                .buildStreetHome(parameters.get(DEPARTURE_ADDRESS))
                                .buildCity(parameters.get(DEPARTURE_CITY))
                                .buildAddress())
                        .buildArrivalDate(Long.parseLong(parameters.get(ARRIVAL_DATE)))
                        .buildArrivalAddress(Address.newBuilder()
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

    private Map<String, Object> fillValidParameters(Map<String, String> parameters) {
        String typeCargo = parameters.get(CARGO);
        String typePassenger = parameters.get(PASSENGER);
        String departureDateFrom = parameters.get(DEPARTURE_DATE_FROM);
        String departureDateTo = parameters.get(DEPARTURE_DATE_TO);
        String passengerNumberFrom = parameters.get(PASSENGER_NUMBER_FROM);
        String passengerNumberTo = parameters.get(PASSENGER_NUMBER_TO);
        String cargoWeightFrom = parameters.get(CARGO_WEIGHT_FROM);
        String cargoWeightTo = parameters.get(CARGO_WEIGHT_TO);
        String cargoVolumeFrom = parameters.get(CARGO_VOLUME_FROM);
        String cargoVolumeTo = parameters.get(CARGO_VOLUME_TO);
        String city = parameters.get(CITY);
        String statusActive = parameters.get(ACTIVE);
        String statusConfirmed = parameters.get(CONFIRMED);
        String statusCompleted = parameters.get(COMPLETED);
        String statusCanceled = parameters.get(CANCELED);

        Map<String, Object> validParameters = new HashMap<>();
        if (ApplicationValidator.checkType(typeCargo)) {
            validParameters.put(CARGO, typeCargo);
        }
        if (ApplicationValidator.checkType(typePassenger)) {
            validParameters.put(PASSENGER, typePassenger);
        }
        if (AddressDateValidator.checkDate(departureDateFrom) && AddressDateValidator.checkDate(departureDateTo)) {
            long from = dateToLong(departureDateFrom);
            long to = dateToLong(departureDateTo);
            if (from <= to) {
                validParameters.put(DEPARTURE_DATE_FROM, from);
                validParameters.put(DEPARTURE_DATE_TO, to);
            }
        }
        if (ApplicationValidator.checkPassenger(passengerNumberFrom)
                && ApplicationValidator.checkPassenger(passengerNumberTo)) {
            int from = Integer.parseInt(passengerNumberFrom);
            int to = Integer.parseInt(passengerNumberTo);
            if (from <= to) {
                validParameters.put(PASSENGER_NUMBER_FROM, from);
                validParameters.put(PASSENGER_NUMBER_TO, to);
            }
        }
        if (ApplicationValidator.checkCargo(cargoWeightFrom)
                && ApplicationValidator.checkCargo(cargoWeightTo)) {
            double from = Double.parseDouble(cargoWeightFrom);
            double to = Double.parseDouble(cargoWeightTo);
            if (from <= to) {
                validParameters.put(CARGO_WEIGHT_FROM, from);
                validParameters.put(CARGO_WEIGHT_TO, to);
            }
        }
        if (ApplicationValidator.checkCargo(cargoVolumeFrom)
                && ApplicationValidator.checkCargo(cargoVolumeTo)) {
            double from = Double.parseDouble(cargoVolumeFrom);
            double to = Double.parseDouble(cargoVolumeTo);
            if (from <= to) {
                validParameters.put(CARGO_VOLUME_FROM, from);
                validParameters.put(CARGO_VOLUME_TO, to);
            }
        }
        if (AddressDateValidator.checkCity(city)) {
            validParameters.put(CITY, city);
        }
        if (OrderValidator.checkStatus(statusActive)) {
            validParameters.put(ACTIVE, statusActive);
        }
        if (OrderValidator.checkStatus(statusConfirmed)) {
            validParameters.put(CONFIRMED, statusConfirmed);
        }
        if (OrderValidator.checkStatus(statusCompleted)) {
            validParameters.put(COMPLETED, statusCompleted);
        }
        if (OrderValidator.checkStatus(statusCanceled)) {
            validParameters.put(CANCELED, statusCanceled);
        }
        return validParameters;
    }
}
