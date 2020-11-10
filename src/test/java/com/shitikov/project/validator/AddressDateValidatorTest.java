package com.shitikov.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AddressDateValidatorTest {

    @DataProvider(name = "addressPositive")
    public Object[] createAddressDataPositive() {
        return new Object[]{"Суворова, 15", "Gaya, 5-3", "ул. Яроша, д.15, кв.65",
                "пр. Независимости, 154/2"};
    }

    @Test(dataProvider = "addressPositive")
    public void testCheckAddressPositive(String address) {
        assertTrue(AddressDateValidator.checkAddress(address));
    }

    @DataProvider(name = "addressNegative")
    public Object[] createAddressDataNegative() {
        return new Object[]{"Суворова, 15!", "Gaya, 5_3", "ул. Яроша; д.15, кв.65",
                "пр. Независимости, 154/2*", "<script>"};
    }

    @Test(dataProvider = "addressNegative")
    public void testCheckAddressNegative(String address) {
        assertFalse(AddressDateValidator.checkAddress(address));
    }

    @DataProvider(name = "cityPositive")
    public Object[] createCityDataPositive() {
        return new Object[]{"Minsk", "Минск", "Старые дороги", "Барановичи 3",
                "Ново-Старобинск"};
    }

    @Test(dataProvider = "cityPositive")
    public void testCheckCityPositive(String city) {
        assertTrue(AddressDateValidator.checkCity(city));
    }

    @DataProvider(name = "cityNegative")
    public Object[] createCityDataNegative() {
        return new Object[]{"Minsk!", "Минск_2", "Старые.дороги", "Барановичи, 3", "<script>"};
    }

    @Test(dataProvider = "cityNegative")
    public void testCheckCityNegative(String city) {
        assertFalse(AddressDateValidator.checkCity(city));
    }

    @DataProvider(name = "datePositive")
    public Object[] createDateDataPositive() {
        return new Object[]{"5.01.1991", "06.11.2020", "31.12.2019", "12.05.1984"};
    }

    @Test(dataProvider = "datePositive")
    public void testCheckDatePositive(String date) {
        assertTrue(AddressDateValidator.checkDate(date));
    }

    @DataProvider(name = "dateNegative")
    public Object[] createDateDataNegative() {
        return new Object[]{"0.12.2020", "52.10.2020", "12.00.2015", "15.13.1956",
                "12.05.123", "12.06.20200"};
    }

    @Test(dataProvider = "dateNegative")
    public void testCheckDateNegative(String date) {
        assertFalse(AddressDateValidator.checkDate(date));
    }
}