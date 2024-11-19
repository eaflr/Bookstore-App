package projects.bookstore;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/bookstore_db";
    private static final String USER = "root";        
    private static final String PASSWORD = "Van.3illa"; 
    
    // Establishes a connection to the database
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}