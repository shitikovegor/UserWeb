package com.shitikov.project.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ApplicationValidatorTest {

    @DataProvider(name = "cargoPositive")
    public Object[] createCargoDataPositive() {
        return new Object[]{"50", "50.5", "4050.852", "1050"};
    }

    @Test(dataProvider = "cargoPositive")
    public void testCheckCargoPositive(String cargo) {
        assertTrue(ApplicationValidator.checkCargo(cargo));
    }

    @DataProvider(name = "cargoNegative")
    public Object[] createCargoDataNegative() {
        return new Object[]{"-35", "56,5", "45I"};
    }

    @Test(dataProvider = "cargoNegative")
    public void testCheckCargoNegative(String cargo) {
        assertFalse(ApplicationValidator.checkCargo(cargo));
    }

    @DataProvider(name = "passengerPositive")
    public Object[] createPassengerDataPositive() {
        return new Object[]{"5", "1", "15", "25"};
    }

    @Test(dataProvider = "passengerPositive")
    public void testCheckPassengerPositive(String passenger) {
        assertTrue(ApplicationValidator.checkPassenger(passenger));
    }

    @DataProvider(name = "passengerNegative")
    public Object[] createPassengerDataNegative() {
        return new Object[]{"-3", "6,5", "5I", "1.5"};
    }

    @Test(dataProvider = "passengerNegative")
    public void testCheckPassengerNegative(String passenger) {
        assertFalse(ApplicationValidator.checkPassenger(passenger));
    }

    @DataProvider(name = "titlePositive")
    public Object[] createTitleDataPositive() {
        return new Object[]{"5", "Cargo", "Отвезти людей",
        "Срочно!", "5 passengers, 1 cat."};
    }

    @Test(dataProvider = "titlePositive")
    public void testCheckTitlePositive(String title) {
        assertTrue(ApplicationValidator.checkTitle(title));
    }

    @DataProvider(name = "titleNegative")
    public Object[] createTitleDataNegative() {
        return new Object[]{"<script>", "</sd>", "(condition)"};
    }

    @Test(dataProvider = "titleNegative")
    public void testCheckTitleNegative(String title) {
        assertFalse(ApplicationValidator.checkTitle(title));
    }

    @DataProvider(name = "descriptionPositive")
    public Object[] createDescriptionDataPositive() {
        return new Object[]{"5", "Cargo", "Отвезти людей (и небольшой груз)",
                "Срочно!", "5 passengers, 1 cat."};
    }

    @Test(dataProvider = "descriptionPositive")
    public void testCheckDescriptionPositive(String description) {
        assertTrue(ApplicationValidator.checkDescription(description));
    }

    @DataProvider(name = "descriptionNegative")
    public Object[] createDescriptionDataNegative() {
        return new Object[]{"<script>", "</sd>"};
    }

    @Test(dataProvider = "descriptionNegative")
    public void testCheckDescriptionNegative(String description) {
        assertFalse(ApplicationValidator.checkDescription(description));
    }
}