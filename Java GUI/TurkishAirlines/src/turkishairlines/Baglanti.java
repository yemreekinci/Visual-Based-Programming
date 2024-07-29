package turkishairlines;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Database uyelik giris
public class Baglanti {
    private String dbUserName = "root";
    private String dbPassword = "";
    private String dbUrl = "jdbc:mysql://localhost:3306/system";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
    }
    
    
    
    
    
    
}