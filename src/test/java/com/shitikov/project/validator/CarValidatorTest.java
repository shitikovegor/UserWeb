package com.shitikov.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CarValidatorTest {

    @DataProvider(name = "numberPositive")
    public Object[] createCarNumberDataPositive() {
        return new Object[]{"1235AB1", "5627OP2", "6666BB3",
                "1452PO4", "5678PB5", "6856BA6", "1234AA7"};
    }

    @Test(dataProvider = "numberPositive")
    public void testCheckCarNumberPositive(String carNumber) {
        assertTrue(CarValidator.checkCarNumber(carNumber));
    }

    @DataProvider(name = "numberNegative")
    public Object[] createCarNumberDataNegative() {
        return new Object[]{"123AB1", "AC78542", "1234ЖД7",
                "1452AB8", "45A5BB5"};
    }

    @Test(dataProvider = "numberNegative")
    public void testCheckCarNumberNegative(String carNumber) {
        assertFalse(CarValidator.checkCarNumber(carNumber));
    }
}