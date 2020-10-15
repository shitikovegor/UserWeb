package com.shitikov.project.model.pool;

import com.shitikov.project.model.exception.PoolException;
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

public class ConnectionPool {
    private static final int POOL_SIZE = 10;
    private static Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private static boolean isInstanceCreated;
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;

    ConnectionPool() throws PoolException {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();

        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/database.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PoolException("Reading properties error. ", e);
        }

        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "Driver not found error.", e);
            throw new RuntimeException("Driver not found error.", e);
        }

        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.offer(new ProxyConnection(DriverManager.getConnection(
                        properties.getProperty("url"), properties)));
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Connection creating error.", e);
//                throw new RuntimeException("Connection creating error", e); 
// TODO: 23.09.2020  is need to throw exception?!
            }
        }
    }

    public static ConnectionPool getInstance() throws PoolException {
        if (!isInstanceCreated) {
            synchronized (ConnectionPool.class) {
                if (!isInstanceCreated) {
                    instance = new ConnectionPool();
                    isInstanceCreated = true;
                }
            }
        }
        return instance;
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

    public void releaseConnection(Connection connection) throws PoolException {
        if (connection instanceof ProxyConnection && givenAwayConnections.remove(connection)) {
            freeConnections.offer((ProxyConnection) connection);
        } else {
            throw new PoolException("Closing connection is incorrect.");
        }
    }

    public void destroyPool() throws PoolException {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException | InterruptedException e) {
                throw new PoolException("Pool destroy error. ", e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() throws PoolException {
        try {
            while (DriverManager.getDrivers().hasMoreElements()) {
                Driver driver = DriverManager.getDrivers().nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            throw new PoolException("Error in time of deregister driver", e);
        }
    }
}
