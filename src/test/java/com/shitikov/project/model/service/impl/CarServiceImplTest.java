package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.dao.impl.CarDaoImpl;
import com.shitikov.project.model.entity.Car;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import com.shitikov.project.util.ParameterName;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.*;

import java.util.Optional;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@PrepareForTest({CarDaoImpl.class})
public class CarServiceImplTest {
    CarDaoImpl carDao;
    CarServiceImpl carService;
    Map<String, String> parameters;
    Car car;
    User user;

    @BeforeClass
    public void setUpClass() {
        car = Car.newBuilder()
                .buildCarId(12L)
                .buildCarNumber("1234AB5")
                .buildCarryingWeight(1500)
                .buildCarryingVolume(2000)
                .buildPassengers(4)
                .buildCar();
        user = User.newBuilder()
                .buildUserId(1L)
                .buildLogin("Login1")
                .buildName("Name")
                .buildSurname("Surname")
                .buildEmail("email@email.com")
                .buildPhone(375292511741L)
                .buildRoleType(RoleType.CLIENT)
                .buildSubjectType(SubjectType.INDIVIDUAL)
                .buildUser();
    }

    @BeforeMethod
    public void setUp() {
        carDao = mock(CarDaoImpl.class);
        carService = CarServiceImpl.getInstance();
        Whitebox.setInternalState(CarDaoImpl.class, "instance", carDao);

        parameters = new HashMap<>();
        parameters.put(ParameterName.CAR_NUMBER, "1234AB5");
        parameters.put(ParameterName.CARRYING_WEIGHT, "1500.5");
        parameters.put(ParameterName.CARRYING_VOLUME, "2000");
        parameters.put(ParameterName.PASSENGERS_NUMBER, "4");
    }

    @AfterMethod
    public void tearDown() {
        carDao = null;
        carService = null;
        parameters = null;
    }

    @AfterClass
    public void tearDownClass() {
        car = null;
        user = null;
    }

    @Test
    public void testAddPositive() {
        parameters.put(ParameterName.LOGIN, "Login");
        try {
            when(carDao.checkCarNumber(any())).thenReturn(false);
            when(carDao.add(any(), any())).thenReturn(true);
            boolean condition = carService.add(parameters);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddNegative() {
        parameters.put(ParameterName.LOGIN, "Login");
        try {
            when(carDao.checkCarNumber(any())).thenReturn(true);
            when(carDao.add(any(), any())).thenReturn(false);
            boolean condition = carService.add(parameters);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddException() throws DaoException, ServiceException {
        parameters.put(ParameterName.LOGIN, "Login");
        DaoException exception = new DaoException();
        when(carDao.checkCarNumber(any())).thenThrow(exception);
        when(carDao.add(any())).thenThrow(exception);
        carService.add(parameters);
    }

    @Test
    public void testRemovePositive() {
        try {
            when(carDao.remove(anyLong())).thenReturn(true);
            boolean condition = carService.remove("45");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @DataProvider(name = "removeData")
    public Object[][] createPhoneData() {
        return new Object[][]{{"-45", true}, {"25", false}};
    }

    @Test(dataProvider = "removeData")
    public void testRemoveNegative(String id, boolean daoResp) {
        try {
            when(carDao.remove(anyLong())).thenReturn(daoResp);
            boolean condition = carService.remove(id);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRemoveException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(carDao.remove(anyLong())).thenThrow(exception);
        carService.remove("45");
    }

    @Test
    public void testFindByIdPositive() {
        Optional<Car> expected = Optional.of(car);
        try {
            when(carDao.findById(anyLong())).thenReturn(expected);
            Optional<Car> actual = carService.findById("45");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByIdNegative() {
        Optional<Car> expected = Optional.of(car);
        try {
            when(carDao.findById(anyLong())).thenReturn(Optional.empty());
            Optional<Car> actual = carService.findById("45");
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByIdException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(carDao.findById(anyLong())).thenThrow(exception);
        carService.findById("45");
    }

    @Test
    public void testFindByUserPositive() {
        List<Car> expected = new ArrayList<>();
        expected.add(car);
        try {
            when(carDao.findByUser(any(User.class))).thenReturn(expected);
            List<Car> actual = carService.findByUser(user);
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByUserNegative() {
        List<Car> expected = new ArrayList<>();
        expected.add(car);
        try {
            when(carDao.findByUser(any(User.class))).thenReturn(new ArrayList<>());
            List<Car> actual = carService.findByUser(user);
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByUserException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(carDao.findByUser(any(User.class))).thenThrow(exception);
        carService.findByUser(user);
    }

    @Test
    public void testUpdatePositive() {
        try {
            when(carDao.update(anyLong(), any())).thenReturn(true);
            boolean condition = carService.update("41", parameters);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateNegative() {
        try {
            when(carDao.update(anyLong(), any())).thenReturn(false);
            boolean condition = carService.update("41", parameters);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(carDao.update(anyLong(), any())).thenThrow(exception);
        carService.update("41", parameters);
    }

    @Test
    public void testRemoveUsedPositive() {
        try {
            when(carDao.removeUsed(anyLong())).thenReturn(true);
            boolean condition = carService.removeUsed("41");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveUsedNegative() {
        try {
            when(carDao.removeUsed(anyLong())).thenReturn(false);
            boolean condition = carService.removeUsed("41");
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRemoveUsedException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(carDao.removeUsed(anyLong())).thenThrow(exception);
        carService.removeUsed("41");
    }

    @Test
    public void testFindAvailableByUserPositive() {
        List<Car> expected = new ArrayList<>();
        expected.add(car);
        try {
            when(carDao.findAvailableByUser(any(User.class))).thenReturn(expected);
            List<Car> actual = carService.findAvailableByUser(user);
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAvailableByUserNegative() {
        List<Car> expected = new ArrayList<>();
        expected.add(car);
        try {
            when(carDao.findAvailableByUser(any(User.class))).thenReturn(new ArrayList<>());
            List<Car> actual = carService.findAvailableByUser(user);
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAvailableByUserException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(carDao.findAvailableByUser(any(User.class))).thenThrow(exception);
        carService.findAvailableByUser(user);
    }
}