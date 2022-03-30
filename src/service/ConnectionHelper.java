package service;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

    public Connection getConnection() throws Exception {
        Class.forName(DatabaseConfig.DRIVER_NAME);
        Connection connection = DriverManager.getConnection(DatabaseConfig.DATABASE_URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
        return connection;
    }

}
