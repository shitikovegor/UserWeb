package com.shitikov.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OrderValidatorTest {

    @DataProvider(name = "statusPositive")
    public Object[] createStatusDataPositive() {
        return new Object[]{"active", "confirmed", "completed",
        "canceled", "ACTIVE", "Confirmed"};
    }

    @Test(dataProvider = "statusPositive")
    public void testCheckStatusPositive(String status) {
        assertTrue(OrderValidator.checkStatus(status));
    }

    @DataProvider(name = "statusNegative")
    public Object[] createStatusDataNegative() {
        return new Object[]{"Activated", "complete", "Cancel"};
    }

    @Test(dataProvider = "statusNegative")
    public void testCheckStatusNegative(String status) {
        assertFalse(OrderValidator.checkStatus(status));
    }
}