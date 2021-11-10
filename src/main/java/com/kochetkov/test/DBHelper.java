package com.kochetkov.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private final String URL = "jdbc:sqlserver://localhost;database=DB_simbirsoft";
    private final String USERNAME = "new_user";
    private final String PASSWORD = "rootrootroot";

    private Connection connection;

    public DBHelper() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
