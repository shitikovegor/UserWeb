package com.shitikov.project.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The type Password encoder.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public class PasswordEncoder {

    private PasswordEncoder() {
    }

    /**
     * Hash password string.
     *
     * @param password the password
     * @return the string
     */
    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(10);
        String hashPassword = BCrypt.hashpw(password, salt);
        return hashPassword;
    }

    /**
     * Check password boolean.
     *
     * @param password       the password
     * @param hashedPassword the hashed password
     * @return the boolean
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
