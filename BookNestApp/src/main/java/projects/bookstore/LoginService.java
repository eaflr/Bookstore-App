package projects.bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {
    // validate user details
    public static boolean validateCredentials(String username, String password) {
        // select user based on username and password
        String query = "SELECT * FROM login_details WHERE Username = ? AND Password = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            //set parameters in the sql query
            ps.setString(1, username);
            ps.setString(2, password);
            /*
            ps. setString () binds the values to the sql placeholder (?)
            prevents sql injection - 'sanitizes' the parameters preventing sql query alteration.
            
            */
            
            ResultSet rs = ps.executeQuery(); // Execute query

            return rs.next();
            } catch (Exception e) {
                System.err.println("Database connection error: " + e.getMessage());
                e.printStackTrace(); // Print any database connection errors
                return false ;
            }
    }
}

