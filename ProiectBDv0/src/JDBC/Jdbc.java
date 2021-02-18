package JDBC;

import java.sql.*;

public class Jdbc {
    Connection connection;

    public Jdbc(String database, String username, char[] password) {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+database+"?" +
                    "user="+username + "&password=" + String.valueOf(password));
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }


}
