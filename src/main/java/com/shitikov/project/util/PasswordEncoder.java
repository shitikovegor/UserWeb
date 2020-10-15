package com.shitikov.project.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public PasswordEncoder() {
    }

    public String hashPassword(String password) {
        String salt = BCrypt.gensalt(10);
        String hashPassword = BCrypt.hashpw(password, salt);
        return hashPassword;
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
