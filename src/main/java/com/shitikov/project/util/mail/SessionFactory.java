package com.shitikov.project.util.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SessionFactory {
    private static final String USER = "mail.user.name";
    private static final String PASSWORD = "mail.user.password";

    public static Session createSession(Properties properties) {
        String userName = properties.getProperty(USER);
        String userPassword = properties.getProperty(PASSWORD);

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });

        return session;
    }
}
