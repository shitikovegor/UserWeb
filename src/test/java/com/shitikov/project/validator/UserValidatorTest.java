package com.shitikov.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserValidatorTest {

    @DataProvider(name = "loginPositive")
    public Object[] createLoginDataPositive() {
        return new Object[]{"Egor", "User1", "new-user", "name_21", "egor.shitikov"};
    }

    @Test(dataProvider = "loginPositive")
    public void testCheckLoginPositive(String login) {
        assertTrue(UserValidator.checkLogin(login));
    }

    @DataProvider(name = "loginNegative")
    public Object[] createLoginDataNegative() {
        return new Object[]{"1Egor", "User$", "-new-user", "name_", "egor.",
                "99", "nam", "Ndhshebvhksyrbsrbshad", "Юзер123"};
    }

    @Test(dataProvider = "loginNegative")
    public void testCheckLoginNegative(String login) {
        assertFalse(UserValidator.checkLogin(login));
    }

    @DataProvider(name = "passwordPositive")
    public Object[] createPasswordDataPositive() {
        return new Object[]{"Passw1", "Password1!", "passW1!", "name21E", "Qwer&8*"};
    }

    @Test(dataProvider = "passwordPositive")
    public void testCheckPasswordPositive(String password) {
        assertTrue(UserValidator.checkPassword(password));
    }

    @DataProvider(name = "passwordNegative")
    public Object[] createPasswordDataNegative() {
        return new Object[]{"1Egor", "user$sd", "user126", "PASSWORD2", "password#$@",
                "qwerty", "12345678", "Ndhshebvhksyrbsrbshad", "QWERTY", "Пароль123"};
    }

    @Test(dataProvider = "passwordNegative")
    public void testCheckPasswordNegative(String password) {
        assertFalse(UserValidator.checkPassword(password));
    }

    @DataProvider(name = "namePositive")
    public Object[] createNameDataPositive() {
        return new Object[]{"Egor", "Егор", "Anna Maria", "Салтыков-Щедрин", "Z"};
    }

    @Test(dataProvider = "namePositive")
    public void testCheckNamePositive(String name) {
        assertTrue(UserValidator.checkName(name));
    }

    @DataProvider(name = "nameNegative")
    public Object[] createNameDataNegative() {
        return new Object[]{"1Egor", "Юрий$", "Виталий-", "Иванов1", "egor.",
                "99", "NdhshebvhksyrbsrbshadNdhshebvhksyrbsrbshadsdsdsdsdw"};
    }

    @Test(dataProvider = "nameNegative")
    public void testCheckNameNegative(String name) {
        assertFalse(UserValidator.checkName(name));
    }

    @DataProvider(name = "emailPositive")
    public Object[] createEmailDataPositive() {
        return new Object[]{"name@mail.ru", "shitikov.egor@gmail.com", "user1@tut.by",
                "user.1@mail.uk", "name-user@mail.ru"};
    }

    @Test(dataProvider = "emailPositive")
    public void testCheckEmailPositive(String email) {
        assertTrue(UserValidator.checkEmail(email));
    }

    @DataProvider(name = "emailNegative")
    public Object[] createEmailDataNegative() {
        return new Object[]{"name_user@mail.ru", "shitikov@gmail.c", "user1tut.by",
                "юзер@mail.uk", "1!@tut.by"};
    }

    @Test(dataProvider = "emailNegative")
    public void testCheckEmailNegative(String email) {
        assertFalse(UserValidator.checkEmail(email));
    }

    @DataProvider(name = "phonePositive")
    public Object[] createPhoneDataPositive() {
        return new Object[]{"+375292511741", "375292511741", "375252356585",
                "+375173451411", "375225480865"};
    }

    @Test(dataProvider = "phonePositive")
    public void testCheckPhonePositive(String phone) {
        assertTrue(UserValidator.checkPhone(phone));
    }

    @DataProvider(name = "phoneNegative")
    public Object[] createPhoneDataNegative() {
        return new Object[]{"292511741", "++375292511741", "+37525123654",
                "3754456748022", "2045235"};
    }

    @Test(dataProvider = "phoneNegative")
    public void testCheckPhoneNegative(String phone) {
        assertFalse(UserValidator.checkPhone(phone));
    }
}