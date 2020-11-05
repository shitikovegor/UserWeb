package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.builder.OrderBuilder;
import com.shitikov.project.model.dao.OrderDao;
import com.shitikov.project.model.dao.impl.OrderDaoImpl;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.CargoApplication;
import com.shitikov.project.model.entity.application.PassengerApplication;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.model.service.OrderService;
import com.shitikov.project.util.ParameterName;
import com.shitikov.project.util.validator.OrderValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final String EMPTY_PARAMETER = "";
    private static OrderServiceImpl instance;
    private final OrderDao orderDao = new OrderDaoImpl();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> parameters) throws ServiceException {
        try {
            Order order = new OrderBuilder()
                    .buildApplication((Application) parameters.get(ParameterName.APPLICATION))
                    .buildCarId((Long) parameters.get(ParameterName.CAR_ID))
                    .buildUserId((Long) parameters.get(ParameterName.USER_ID))
                    .buildStatus(OrderStatus.CONFIRMED)
                    .buildOrder();

            return orderDao.add(order, EMPTY_PARAMETER); // TODO: 02.11.2020 may be with null parameter?
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean remove(String id) throws ServiceException {
        try {
            return orderDao.remove(Long.parseLong(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Order> findById(String id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Map<Order, Long> findByUser(User user) throws ServiceException {
        try {
            Map<Order, Long> orders = orderDao.findByUser(user);
            return orders;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Order> findByAppId(String applicationId) throws ServiceException {
        try {
            Optional<Order> order = Optional.empty();
            if (OrderValidator.checkId(applicationId)) {
                order = orderDao.findByAppId(Long.parseLong(applicationId));
            }
            return order;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Car> checkUserForApp(User user, Application application) throws ServiceException {
        List<Car> cars = CarServiceImpl.getInstance().findByUser(user);
        boolean isCarForHelp;
        Car car = null;
        if (application instanceof CargoApplication) {
            isCarForHelp = checkCarrying(cars, (CargoApplication) application);
        } else {
            isCarForHelp = checkPassengerNumber(cars, (PassengerApplication) application);
        }
        if (isCarForHelp) {
            car = cars.get(0);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public boolean update(String id, Map<String, String> parameters) throws ServiceException {
        if (!OrderValidator.checkId(id)) {
            return false;
        }
        try {
            boolean isUpdated = orderDao.update(Long.parseLong(id), parameters);
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException("Program error. ", e);
        }
    }

    private boolean checkCarrying(List<Car> cars, CargoApplication application) {
        int index = 0;
        double appWeight = application.getCargoWeight();
        double appVolume = application.getCargoVolume();
        while (!cars.isEmpty() && index < cars.size()) {
            Car car = cars.get(index);
            if (car.getCarryingWeight() < appWeight
                    && car.getCarryingVolume() < appVolume) {
                cars.remove(car);
            } else {
                index++;
            }
        }
        return !cars.isEmpty();
    }

    private boolean checkPassengerNumber(List<Car> cars, PassengerApplication application) {
        int index = 0;
        int appPassengersNumber = application.getPassengersNumber();
        while (!cars.isEmpty() && index < cars.size()) {
            Car car = cars.get(index);
            if (car.getPassengers() < appPassengersNumber) {
                cars.remove(car);
            } else {
                index++;
            }
        }
        return !cars.isEmpty();
    }
}
