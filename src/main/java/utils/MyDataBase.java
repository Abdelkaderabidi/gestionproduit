package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private static MyDataBase instance;
    private final String URL = "jdbc:mysql://127.0.0.1:3306/projet_web";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private Connection cnx;

    private MyDataBase() {
        try {
            // Initialize the database connection
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            // Print detailed error information
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Get the singleton instance
    public static synchronized MyDataBase getInstance() {
        if (instance == null) {
            instance = new MyDataBase();
        }
        return instance;
    }

    // Get the database connection
    public Connection getCnx() {
        // Check if connection is still valid
        if (cnx != null) {
            try {
                if (cnx.isClosed()) {
                    System.err.println("Connection is closed.");
                    // Re-establish the connection if needed
                    cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                }
            } catch (SQLException e) {
                System.err.println("Error checking connection status: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Connection is null.");
        }
        return cnx;
    }
}
