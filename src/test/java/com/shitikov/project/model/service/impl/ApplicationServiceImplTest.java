package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.dao.impl.ApplicationDaoImpl;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.application.AddressTimeData;
import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.PassengerApplication;
import com.shitikov.project.model.entity.type.ApplicationType;
import com.shitikov.project.model.entity.type.OrderStatus;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.shitikov.project.util.ParameterName.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@PrepareForTest({ApplicationDaoImpl.class})
public class ApplicationServiceImplTest {
    ApplicationDaoImpl applicationDao;
    ApplicationServiceImpl applicationService;
    Map<String, String> cargoParameters;
    Map<String, String> passengerParameters;
    Application application;
    Map<Application, OrderStatus> applications;

    @BeforeMethod
    public void setUp() {
        applicationDao = mock(ApplicationDaoImpl.class);
        applicationService = ApplicationServiceImpl.getInstance();
        Whitebox.setInternalState(ApplicationDaoImpl.class, "instance", applicationDao);

        Map<String, String> parameters = new HashMap<>();
        parameters.put(TITLE, "Title");
        parameters.put(DEPARTURE_DATE, "2020-12-06");
        parameters.put(DEPARTURE_ADDRESS, "Goreckogo, 19/2");
        parameters.put(DEPARTURE_CITY, "Minsk");
        parameters.put(ARRIVAL_DATE, "2020-12-07");
        parameters.put(ARRIVAL_ADDRESS, "Kolasa, 51-2");
        parameters.put(ARRIVAL_CITY, "Minsk");
        parameters.put(DESCRIPTION, "Description");

        cargoParameters = new HashMap<>(parameters);
        cargoParameters.put(APPLICATION_TYPE, "cargo");
        cargoParameters.put(CARGO_WEIGHT, "200.5");
        cargoParameters.put(CARGO_VOLUME, "250");

        passengerParameters = new HashMap<>(parameters);
        passengerParameters.put(APPLICATION_TYPE, "passenger");
        passengerParameters.put(PASSENGERS_NUMBER, "3");
    }

    @BeforeClass
    public void setUpParams() {
        application = PassengerApplication.newBuilder()
                .buildPassengersNumber(4)
                .buildApplicationId(1L)
                .buildTitle("Title")
                .buildApplicationType(ApplicationType.CARGO)
                .buildDate(1604384503673L)
                .buildAddressTimeData(AddressTimeData.newBuilder()
                        .buildDepartureDate(1607202000000L)
                        .buildDepartureAddress(Address.newBuilder()
                                .buildCity("Minsk")
                                .buildStreetHome("Goreckogo, 19/2")
                                .buildAddress())
                        .buildArrivalDate(1607288400000L)
                        .buildArrivalAddress(Address.newBuilder()
                                .buildCity("Minsk")
                                .buildStreetHome("Kolasa, 51-2")
                                .buildAddress())
                        .buildAddressTimeData())
                .buildDescription("Description is")
                .buildApplication();;

        applications = new HashMap<>();
        applications.put(application, OrderStatus.ACTIVE);
    }

    @AfterMethod
    public void tearDown() {
        applicationDao = null;
        applicationService = null;
        passengerParameters = null;
        cargoParameters = null;
    }

    @AfterClass
    public void tearDownParams() {
        application = null;
        applications = null;
    }

    @Test
    public void testAddPositive() {
        Map<String, String> params = new HashMap<>(passengerParameters);
        params.put(LOGIN, "Login");
        try {
            when(applicationDao.add(any(Application.class), any())).thenReturn(true);
            boolean condition = applicationService.add(params);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddNegative() {
        Map<String, String> params = new HashMap<>(cargoParameters);
        params.put(LOGIN, "Login");
        try {
            when(applicationDao.add(any(Application.class), any())).thenReturn(false);
            boolean condition = applicationService.add(params);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(applicationDao.add(any(Application.class), any())).thenThrow(exception);
        applicationService.add(cargoParameters);
    }

    @Test
    public void testRemove() {
        try {
            when(applicationDao.remove(anyLong())).thenReturn(true);
            boolean condition = applicationService.remove("1");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRemoveException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(applicationDao.remove(anyLong())).thenThrow(exception);
        applicationService.remove("1");
    }


    @Test
    public void testFindById() {
        Optional<Application> expected = Optional.of(application);
        try {
            when(applicationDao.findById(anyLong())).thenReturn(expected);
            Optional<Application> actual = applicationService.findById("1");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByIdException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(applicationDao.findById(anyLong())).thenThrow(exception);
        applicationService.findById("1");
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
        try {
            when(applicationDao.findByUser(any(User.class))).thenReturn(applications);
            Map<Application, OrderStatus> actual = applicationService.findByUser(user);
            assertEquals(actual, applications);
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
        DaoException exception = new DaoException();
        when(applicationDao.findByUser(any(User.class))).thenThrow(exception);
        applicationService.findByUser(user);
    }

    @Test
    public void testFindAll() {
        try {
            when(applicationDao.findAll()).thenReturn(applications);
            Map<Application, OrderStatus> actual = applicationService.findAll();
            assertEquals(actual, applications);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(applicationDao.findAll()).thenThrow(exception);
        applicationService.findAll();
    }

    @Test
    public void testFindByParameters() {
        try {
            when(applicationDao.findByParameters(any())).thenReturn(applications);
            Map<Application, OrderStatus> actual = applicationService.findByParameters(passengerParameters);
            assertEquals(actual, applications);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByParametersException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(applicationDao.findByParameters(any())).thenThrow(exception);
        applicationService.findByParameters(passengerParameters);
    }

    @Test
    public void testUpdate() {
        try {
            when(applicationDao.update(anyLong(), any())).thenReturn(true);
            boolean condition = applicationService.update("12", passengerParameters);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(applicationDao.update(anyLong(), any())).thenThrow(exception);
        applicationService.update("12", passengerParameters);
    }

    @Test
    public void testFindRecentActiveApps() {
        try {
            when(applicationDao.findRecentActiveApps(anyInt())).thenReturn(applications);
            Map<Application, OrderStatus> actual = applicationService.findRecentActiveApps(1);
            assertEquals(actual, applications);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindRecentActiveAppsException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(applicationDao.findRecentActiveApps(anyInt())).thenThrow(exception);
        applicationService.findRecentActiveApps(1);
    }
}