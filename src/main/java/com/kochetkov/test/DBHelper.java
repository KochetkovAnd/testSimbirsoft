package com.kochetkov.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBHelper {
    private final Logger logger = LoggerFactory.getLogger(DBHelper.class);

    private final String URL = "jdbc:sqlserver://localhost;database=DB_simbirsoft";
    private final String USERNAME = "new_user";
    private final String PASSWORD = "rootrootroot";

    private Connection connection;

    public DBHelper() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e) {
            logger.error("unable to connect to database by URL {}", URL, e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
