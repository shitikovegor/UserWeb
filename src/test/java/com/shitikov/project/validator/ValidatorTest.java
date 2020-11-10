package com.shitikov.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ValidatorTest {

    @DataProvider(name = "idPositive")
    public Object[] createIdDataPositive() {
        return new Object[]{"1", "56", "133541252"};
    }

    @Test(dataProvider = "idPositive")
    public void testCheckIdPositive(String id) {
        assertTrue(Validator.checkId(id));
    }

    @DataProvider(name = "idNegative")
    public Object[] createIdDataNegative() {
        return new Object[]{"-1", "15.5", "1234567890123456789", "012"};
    }

    @Test(dataProvider = "idNegative")
    public void testCheckIdNegative(String id) {
        assertFalse(Validator.checkId(id));
    }
}