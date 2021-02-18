package LogInUI;

import MainUI.MainMVC;
import javax.swing.*;
import java.sql.*;

public class LogInModel {

    /**
     * Make connection if account if valid
     * @param logInFrame grafica de login
     * @param database indica la ce db ne conectam
     * @param username date cont
     * @param password date cont
     */
    public void connect(JFrame logInFrame, String database, String username, char[] password) {
        Connection connection = null;
        MainMVC mainMVC = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + database + "?" +
                    "user=" + username + "&password=" + String.valueOf(password));
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Facem conexiunea la meniul principal si inchidem logIn-ul cand sunt intreoduse datele bune
        if(connection!=null) {
            logInFrame.setVisible(false);
            mainMVC = new MainMVC(connection);
        }
    }
}
