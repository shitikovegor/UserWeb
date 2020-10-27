package com.shitikov.project.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum  ConnectionPool {
    INSTANCE;

    private static final int POOL_SIZE = 10;
    private Logger logger = LogManager.getLogger();
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;

    ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();

        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/database.properties");
        try {
            properties.load(inputStream);
            Class.forName(properties.getProperty("driver"));
            for (int i = 0; i < POOL_SIZE; i++) {
                freeConnections.offer(new ProxyConnection(DriverManager.getConnection(
                        properties.getProperty("url"), properties)));
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.log(Level.ERROR, "Connection creating error.", e);
            throw new RuntimeException("Connection creating error", e); // TODO: 23.09.2020  is need to throw exception?!
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer((ProxyConnection) connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Getting connection error. ", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection && givenAwayConnections.remove(connection)) {
            freeConnections.offer((ProxyConnection) connection);
        } else {
            logger.log(Level.WARN, "Incorrect connection to release.");
        }
    }

    public void destroyPool() {
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                freeConnections.take().reallyClose();
            }
            deregisterDrivers();
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Pool destroy error. ", e);
        }
    }

    private void deregisterDrivers() throws SQLException {
        while (DriverManager.getDrivers().hasMoreElements()) {
            Driver driver = DriverManager.getDrivers().nextElement();
            DriverManager.deregisterDriver(driver);
        }
    }
}
