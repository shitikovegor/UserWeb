package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.dao.impl.OrderDaoImpl;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.Order;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.AddressTimeData;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.CargoApplication;
import com.shitikov.project.model.entity.type.ApplicationType;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.util.ParameterName;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@PrepareForTest({OrderDaoImpl.class})
public class OrderServiceImplTest {
    OrderDaoImpl orderDao;
    OrderServiceImpl orderService;
    Application application;
    Optional<Order> orderOptional;

    @BeforeMethod
    public void setUp() {
        orderDao = mock(OrderDaoImpl.class);
        orderService = OrderServiceImpl.getInstance();
        Whitebox.setInternalState(OrderDaoImpl.class, "instance", orderDao);

        Address addressDep = Address.newBuilder()
                .buildCity("Minsk")
                .buildStreetHome("Gaya, 2-3")
                .buildAddress();
        Address addressArr = Address.newBuilder()
                .buildCity("Minsk")
                .buildStreetHome("Sedyx, 12-23")
                .buildAddress();
        AddressTimeData data = AddressTimeData.newBuilder()
                .buildDepartureDate(1606338000000L)
                .buildDepartureAddress(addressDep)
                .buildArrivalDate(1606338000000L)
                .buildArrivalAddress(addressArr)
                .buildAddressTimeData();

        application = CargoApplication.newBuilder()
                .buildCargoWeight(45.5)
                .buildCargoVolume(56.0)
        .buildApplicationId(1L)
        .buildTitle("Title")
        .buildApplicationType(ApplicationType.CARGO)
        .buildDate(1604384503673L)
        .buildAddressTimeData(data)
        .buildDescription("Description is")
        .buildApplication();

        Order order = Order.newBuilder()
                .buildOrderId(3L)
                .buildApplication(application)
                .buildCarId(1L)
                .buildUserId(2L)
                .buildStatus(OrderStatus.COMPLETED)
                .buildOrder();

        orderOptional = Optional.of(order);
    }

    @AfterMethod
    public void tearDown() {
        orderDao = null;
        orderService = null;
        application = null;
        orderOptional = Optional.empty();
    }

    @Test
    public void testAdd() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ParameterName.APPLICATION, application);
        parameters.put(ParameterName.CAR_ID, 1L);
        parameters.put(ParameterName.USER_ID, 1L);

        try {
            when(orderDao.add(any(Order.class))).thenReturn(true);
            boolean condition = orderService.add(parameters);
            assertTrue(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddException() throws ServiceException, DaoException {


        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ParameterName.APPLICATION, application);
        parameters.put(ParameterName.CAR_ID, 1L);
        parameters.put(ParameterName.USER_ID, 1L);

        when(orderDao.add(any())).thenThrow(new DaoException());
        orderService.add(parameters);
    }

    @Test
    public void testRemove() {
        try {
            when(orderDao.remove(anyLong())).thenReturn(true);
            boolean condition = orderService.remove("43");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRemoveException() throws DaoException, ServiceException {
        when(orderDao.remove(anyLong())).thenThrow(new DaoException());
        orderService.remove("43");
    }

    @Test
    public void testFindByUser() {
        User user = User.newBuilder()
                .buildUserId(1L)
                .buildLogin("Login1")
                .buildName("Name")
                .buildSurname("Surname")
                .buildEmail("email@email.com")
                .buildPhone(375292511741L)
                .buildRoleType(RoleType.CLIENT)
                .buildSubjectType(SubjectType.INDIVIDUAL)
                .buildUser();
        Order order = orderOptional.get();
        Map<Order, Long> expected = new HashMap<>();
        expected.put(order, 375292511741L);
        try {
            when(orderDao.findByUser(any(User.class))).thenReturn(expected);
            Map<Order, Long> actual = orderService.findByUser(user);
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByUserException() throws DaoException, ServiceException {
        User user = User.newBuilder()
                .buildUserId(1L)
                .buildLogin("Login1")
                .buildName("Name")
                .buildSurname("Surname")
                .buildEmail("email@email.com")
                .buildPhone(375292511741L)
                .buildRoleType(RoleType.CLIENT)
                .buildSubjectType(SubjectType.INDIVIDUAL)
                .buildUser();
        Order order = orderOptional.get();
        Map<Order, Long> expected = new HashMap<>();
        expected.put(order, 375292511741L);
        when(orderDao.findByUser(any(User.class))).thenThrow(new DaoException());
        orderService.findByUser(user);
    }

    @Test
    public void testFindByAppId() {
        try {
            when(orderDao.findByAppId(anyLong())).thenReturn(orderOptional);
            Optional<Order> actual = orderService.findByAppId("23");
            assertEquals(actual, orderOptional);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByAppIdException() throws DaoException, ServiceException {
        when(orderDao.findByAppId(anyLong())).thenThrow(new DaoException());
        orderService.findByAppId("23");
    }

    @Test
    public void testUpdatePositive() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(ParameterName.STATUS, "completed");
        try {
            when(orderDao.update(anyLong(), any())).thenReturn(true);
            boolean condition = orderService.update("45", parameters);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @DataProvider(name = "updateDataNegative")
    public Object[][] createUpdateData() {
        return new Object[][]{{"45", false}, {"-45", true}, {"sd", true}};
    }

    @Test(dataProvider = "updateDataNegative")
    public void testUpdateNegative(String id, boolean daoResp) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(ParameterName.STATUS, "completed");
        try {
            when(orderDao.update(anyLong(), any())).thenReturn(daoResp);
            boolean condition = orderService.update(id, parameters);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateException() throws DaoException, ServiceException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(ParameterName.STATUS, "completed");
        when(orderDao.update(anyLong(), anyMap())).thenThrow(new DaoException());
        orderService.update("45", parameters);
    }
}