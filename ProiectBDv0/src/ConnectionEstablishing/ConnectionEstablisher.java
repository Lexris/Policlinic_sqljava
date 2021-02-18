package ConnectionEstablishing;

import LogInUI.LogInMVC;

public class ConnectionEstablisher {
    public static LogInMVC connection;

    public static void establishConnection() {
        connection = new LogInMVC();
    }

    public static void main(String[] args) {
        connection = new LogInMVC();
    }
}
