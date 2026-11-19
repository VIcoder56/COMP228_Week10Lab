package org.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final static Dotenv dotenv = Dotenv.load();

    private static String user = dotenv.get("DB_USER");
    private static String password = dotenv.get("DB_PASSWORD");
    private static String URL = "jdbc:mysql://localhost:3306/applications_db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, password);
    }
}
