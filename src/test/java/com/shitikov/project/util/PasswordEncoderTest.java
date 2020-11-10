package com.shitikov.project.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PasswordEncoderTest {

    @DataProvider(name = "hashPassPositive")
    public Object[][] createHashDataPositive() {
        return new Object[][] {{"Client2", "$2a$10$WiG6Hg5SPlz1U./FoSa33.V5O.y6QRYt2d2SveIUr64zkmmjnzNeK"},
                {"Hospital4", "$2a$10$OUY4SwR0D6v2ctXQdHJjnu.7KZjVtdIaIrDrWBl7aO.klz0JWahu2"},
                {"Driver1", "$2a$10$Y4785HIJYAmnSAHzYDkXP.M/2pnmvQKJldkvPecMsIP69cmw/tXNm"}};
    }

    @Test(dataProvider = "hashPassPositive")
    public void testCheckPasswordPositive(String password, String hashedPassword) {
        assertTrue(PasswordEncoder.checkPassword(password, hashedPassword));
    }

    @DataProvider(name = "hashPassNegative")
    public Object[][] createHashDataNegative() {
        return new Object[][] {{"Client2", "$2a$10$WiG6Hg5SPlz1UO.y6QRYt2d2SveIUr64zkmmjnzNeK"},
                {"Hospital4", "$2a$10$OUY4SwR0D6v2ctX.7KZjVtdIaIrDrWBl7aO.klz0JWahu2"},
                {"Driver1", "$2a$10$Y4785HIJYAmnSAHzYDkXP.MldkvPecMsIP69cmw/tXNm"}};
    }

    @Test(dataProvider = "hashPassNegative")
    public void testCheckPasswordNegative(String password, String hashedPassword) {
        assertFalse(PasswordEncoder.checkPassword(password, hashedPassword));
    }
}