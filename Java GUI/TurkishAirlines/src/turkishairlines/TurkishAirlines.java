package turkishairlines;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Database giris

public class TurkishAirlines {
    private static final String dbUserName = "root";
    private static final String dbPassword = "";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/system";
    static boolean isLoggedIn = false;

    
    // Flight id ve yolcu sayısı kayit
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
    }


    
    // Bilet bilgileri kayit
    public static void insertFlightInfo(String flightId, String fName, String fSurname, String classChoice) {
    try (Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
        String query = "INSERT INTO tickets (Flight_id, Ticket_Name, Ticket_Surname, Ticket_Class) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, flightId);
            statement.setString(2, fName);
            statement.setString(3, fSurname);
            statement.setString(4, classChoice);
            statement.executeUpdate();
            System.out.println("Ucus bilgileri basariyla kaydedildi.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    // Uyelik kayit
    public static void saveUser(String name, String surname, String email, String password) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            String query = "INSERT INTO users (name, surname, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, email);
                statement.setString(4, password);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Uyelik giris
    public static boolean checkUser(String email, String password) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    isLoggedIn = resultSet.next(); // Kullanıcı varsa true, yoksa false döndürür
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isLoggedIn;
    }

    public static boolean login(String email, String password) {
        isLoggedIn = checkUser(email, password);
        return isLoggedIn;
    }
}
