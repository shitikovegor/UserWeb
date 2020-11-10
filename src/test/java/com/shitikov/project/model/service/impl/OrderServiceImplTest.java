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

        Address addressDep = new Address("Minsk", "Gaya, 2-3");
        Address addressArr = new Address("Minsk", "Sedyx, 12-23");
        AddressTimeData data = new AddressTimeData(1606338000000L, addressDep,
                1606338000000L, addressArr);
        application = new CargoApplication(1L, "Title", ApplicationType.CARGO,
                1604384503673L, data, "Description is", 45.5, 56.0);

        orderOptional = Optional.of(new Order(3L, application, 1L, 2L, OrderStatus.COMPLETED));
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
        User user = new User(1L, "Login1", "Name", "Surname", "email@email.com",
                375292511741L, RoleType.CLIENT, SubjectType.INDIVIDUAL);
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
        User user = new User(1L, "Login1", "Name", "Surname", "email@email.com",
                375292511741L, RoleType.CLIENT, SubjectType.INDIVIDUAL);
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