package com.shitikov.project.model.service.impl;

import com.shitikov.project.model.dao.impl.UserDaoImpl;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.User;
import com.shitikov.project.model.entity.type.RoleType;
import com.shitikov.project.model.entity.type.SubjectType;
import com.shitikov.project.model.exception.DaoException;
import com.shitikov.project.model.exception.ServiceException;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static com.shitikov.project.util.ParameterName.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@PrepareForTest({UserDaoImpl.class})
public class UserServiceImplTest {
    UserDaoImpl userDao;
    UserServiceImpl userService;
    Map<String, String> parameters;
    Map<String, String> addressParameters;
    User user;

    @BeforeMethod
    public void setUp() {
        userDao = mock(UserDaoImpl.class);
        userService = UserServiceImpl.getInstance();
        Whitebox.setInternalState(UserDaoImpl.class, "instance", userDao);

        parameters = new HashMap<>();
        parameters.put(LOGIN, "Login1");
        parameters.put(NAME, "Name");
        parameters.put(SURNAME, "Surname");
        parameters.put(EMAIL, "email@email.com");
        parameters.put(PHONE, "375292511741");
        parameters.put(ROLE_TYPE, "client");
        parameters.put(SUBJECT_TYPE, "individual");
        parameters.put(PASSWORD, "Password1");

        addressParameters = new HashMap<>();
        addressParameters.put(ADDRESS, "Goreckogo, 19-15");
        addressParameters.put(CITY, "Minsk");

        user = new User(1L, "Login1", "Name", "Surname",
                "email@email.com", 375292511741L, RoleType.CLIENT, SubjectType.INDIVIDUAL);
    }

    @AfterMethod
    public void tearDown() {
        userDao = null;
        userService = null;
        parameters = null;
        addressParameters = null;
        user = null;
    }

    @Test
    public void testAddPositive() {
        try {
            when(userDao.checkLogin(any())).thenReturn(false);
            when(userDao.checkEmail(any())).thenReturn(false);
            when(userDao.add(any(User.class), any())).thenReturn(true);
            boolean condition = userService.add(parameters);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddNegative() {
        try {
            when(userDao.checkLogin(any())).thenReturn(true);
            when(userDao.checkEmail(any())).thenReturn(true);
            when(userDao.add(any(User.class), any())).thenReturn(false);
            boolean condition = userService.add(parameters);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.checkLogin(any())).thenThrow(exception);
        when(userDao.checkEmail(any())).thenThrow(exception);
        when(userDao.add(any(User.class), any())).thenThrow(exception);
        userService.add(parameters);
    }

    @Test
    public void testAddUserAddressPositive() {
        try {
            when(userDao.checkLogin(any())).thenReturn(true);
            when(userDao.addAddress(any(), any())).thenReturn(true);
            boolean condition = userService.addUserAddress("Login1", addressParameters);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddUserAddressNegative() {
        try {
            when(userDao.checkLogin(any())).thenReturn(false);
            when(userDao.addAddress(any(), any())).thenReturn(false);
            boolean condition = userService.addUserAddress("Login1", addressParameters);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddUserAddressException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.checkLogin(any())).thenThrow(exception);
        when(userDao.addAddress(any(), any())).thenThrow(exception);
        userService.addUserAddress("Login1", addressParameters);
    }

    @Test
    public void testCheckLoginPositive() {
        try {
            when(userDao.checkLogin(any())).thenReturn(true);
            boolean condition = userService.checkLogin("Login1");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @DataProvider(name = "loginDataNegative")
    public Object[][] createUpdateData() {
        return new Object[][]{{"Login1", false}, {"!Adm", true}, {"sd", true}};
    }

    @Test(dataProvider = "loginDataNegative")
    public void testCheckLoginNegative(String login, boolean daoResp) {
        try {
            when(userDao.checkLogin(any())).thenReturn(daoResp);
            boolean condition = userService.checkLogin(login);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCheckLoginException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.checkLogin(any())).thenThrow(exception);
        userService.checkLogin("Login1");
    }

    @Test
    public void testCheckPasswordPositive() {
        String hashedPassword = "$2a$10$Y4785HIJYAmnSAHzYDkXP.M/2pnmvQKJldkvPecMsIP69cmw/tXNm";
        try {
            when(userDao.findPassword(any())).thenReturn(hashedPassword);
            boolean condition = userService.checkPassword("Login", "Driver1");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCheckPasswordNegative() {
        String hashedPassword = "$2a$10$U.O/dV9mreLpbZKUaelaSe6uJtfSiMOaHgyP5a5ZxdIEXenwUNnjy";
        try {
            when(userDao.findPassword(any())).thenReturn(hashedPassword);
            boolean condition = userService.checkPassword("Login", "Driver1");
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCheckPasswordException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.findPassword(any())).thenThrow(exception);
        userService.checkPassword("Login", "Driver1");
    }

    @Test
    public void testFindByLoginPositive() {
        Optional<User> expected = Optional.of(user);
        try {
            when(userDao.findByLogin(any())).thenReturn(expected);
            Optional<User> actual = userService.findByLogin("Login1");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindByLoginNegative() {
        Optional<User> expected = Optional.of(user);
        try {
            when(userDao.findByLogin(any())).thenReturn(Optional.empty());
            Optional<User> actual = userService.findByLogin("Login1");
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByLoginException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.findByLogin(any())).thenThrow(exception);
        userService.findByLogin("Login");
    }

    @Test
    public void testFindAllPositive() {
        List<User> expected = new ArrayList<>();
        expected.add(user);
        try {
            when(userDao.findAll()).thenReturn(expected);
            List<User> actual = userService.findAll();
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllNegative() {
        List<User> expected = new ArrayList<>();
        expected.add(user);
        try {
            when(userDao.findAll()).thenReturn(new ArrayList<>());
            List<User> actual = userService.findAll();
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.findAll()).thenThrow(exception);
        userService.findAll();
    }

    @Test
    public void testFindRolePositive() {
        try {
            when(userDao.findRole(any())).thenReturn(RoleType.CLIENT);
            RoleType actual = userService.findRole("Login");
            assertEquals(actual, RoleType.CLIENT);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindRoleNegative() {
        try {
            when(userDao.findRole(any())).thenReturn(RoleType.CLIENT);
            RoleType actual = userService.findRole("Login");
            assertNotEquals(actual, RoleType.DRIVER);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindRoleException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.findRole(any())).thenThrow(exception);
        userService.findRole("Login");
    }

    @Test
    public void testFindAddressPositive() {
        Optional<Address> expected = Optional.of(new Address(
                addressParameters.get(ADDRESS),
                addressParameters.get(CITY)));
        try {
            when(userDao.findUserAddress(any())).thenReturn(expected);
            Optional<Address> actual = userService.findAddress("Login");
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAddressNegative() {
        Optional<Address> expected = Optional.of(new Address(
                addressParameters.get(ADDRESS),
                addressParameters.get(CITY)));
        try {
            when(userDao.findUserAddress(any())).thenReturn(Optional.empty());
            Optional<Address> actual = userService.findAddress("Login");
            assertNotEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAddressException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.findUserAddress(any())).thenThrow(exception);
        userService.findAddress("Login");
    }

    @DataProvider(name = "phoneData")
    public Object[][] createPhoneData() {
        return new Object[][]{{"-45", 0L}, {"25", 375292511741L}};
    }

    @Test(dataProvider = "phoneData")
    public void testFindPhoneByApplicationId(String appId, long expected) {
        try {
            when(userDao.findPhoneByApplicationId(anyLong())).thenReturn(expected);
            long actual = userService.findPhoneByApplicationId(appId);
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindPhoneByApplicationIdException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.findPhoneByApplicationId(anyLong())).thenThrow(exception);
        userService.findPhoneByApplicationId("36");
    }

    @Test
    public void testUpdatePositive() {
        Map<String, String> userParameters = new HashMap<>(parameters);
        userParameters.remove(ROLE_TYPE);
        userParameters.remove(SUBJECT_TYPE);
        userParameters.remove(PHONE);
        userParameters.remove(PASSWORD);
        try {
            when(userDao.checkLogin(any())).thenReturn(true);
            when(userDao.checkEmail(any())).thenReturn(true);
            when(userDao.update(any(), any())).thenReturn(true);
            boolean condition = userService.update("Login", userParameters);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateNegative() {
        try {
            when(userDao.checkLogin(any())).thenReturn(true);
            when(userDao.checkEmail(any())).thenReturn(false);
            when(userDao.update(any(), any())).thenReturn(false);
            boolean condition = userService.update("Login", parameters);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.checkLogin(any())).thenThrow(exception);
        when(userDao.checkEmail(any())).thenThrow(exception);
        when(userDao.update(any(), any())).thenThrow(exception);
        userService.update("Login", parameters);
    }

    @Test
    public void testUpdateContactParametersPositive() {
        try {
            when(userDao.updateContactParameters(any(), any())).thenReturn(true);
            boolean condition = userService.updateContactParameters("Login", addressParameters);
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateContactParametersNegative() {
        try {
            when(userDao.updateContactParameters(any(), any())).thenReturn(false);
            boolean condition = userService.updateContactParameters("Login", addressParameters);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateContactParametersException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.updateContactParameters(any(), any())).thenThrow(exception);
        userService.updateContactParameters("Login", parameters);
    }

    @Test
    public void testUpdatePhonePositive() {
        try {
            when(userDao.updatePhone(any(), anyLong())).thenReturn(true);
            boolean condition = userService.updatePhone("Login", "375292511755");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @DataProvider(name = "updatePhoneData")
    public Object[][] createUpdatePhoneData() {
        return new Object[][]{{"-45", true}, {"thosd", true},
                {"375292511755", false}};
    }

    @Test(dataProvider = "updatePhoneData")
    public void testUpdatePhoneNegative(String phoneNumber, boolean daoResp) {
        try {
            when(userDao.updatePhone(any(), anyLong())).thenReturn(daoResp);
            boolean condition = userService.updatePhone("Login", phoneNumber);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdatePhoneException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.updatePhone(any(), anyLong())).thenThrow(exception);
        userService.updatePhone("Login", "375295658855");
    }

    @Test
    public void testUpdatePasswordPositive() {
        try {
            when(userDao.updatePassword(any(), any())).thenReturn(true);
            boolean condition = userService.updatePassword("Login", "Password1");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @DataProvider(name = "updatePasswordData")
    public Object[][] createUpdatePasswordData() {
        return new Object[][]{{"password", true}, {"Qwerty", true},
                {"Password1", false}};
    }

    @Test(dataProvider = "updatePasswordData")
    public void testUpdatePasswordNegative(String password, boolean daoResp) {
        try {
            when(userDao.updatePassword(any(), any())).thenReturn(daoResp);
            boolean condition = userService.updatePassword("Login", password);
            assertFalse(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdatePasswordException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.updatePassword(any(), any())).thenThrow(exception);
        userService.updatePassword("Login", "Password1");
    }

    @Test
    public void testActivate() {
        try {
            when(userDao.updateActivation(any(), anyInt())).thenReturn(true);
            boolean condition = userService.activate("Login");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testActivateException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.updateActivation(any(), anyInt())).thenThrow(exception);
        userService.activate("Login");
    }

    @Test
    public void testBlock() {
        try {
            when(userDao.updateBlock(any(), anyInt())).thenReturn(true);
            boolean condition = userService.block("Login");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testBlockException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.updateBlock(any(), anyInt())).thenThrow(exception);
        userService.block("Login");
    }

    @Test
    public void testUnblock() {
        try {
            when(userDao.updateBlock(any(), anyInt())).thenReturn(true);
            boolean condition = userService.unblock("Login");
            assertTrue(condition);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUnblockException() throws DaoException, ServiceException {
        DaoException exception = new DaoException();
        when(userDao.updateBlock(any(), anyInt())).thenThrow(exception);
        userService.unblock("Login");
    }
}