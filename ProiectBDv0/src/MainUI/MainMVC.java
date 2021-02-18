package MainUI;

import com.mysql.cj.conf.ConnectionUrlParser;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

public class MainMVC {

    /**
     * Contructor of the MVC of the MainUI
     * @param connection conexiunea la care ne conectam
     */
    public MainMVC(Connection connection) {
        MainModel model = new MainModel(connection);

        ConnectionUrlParser.Pair<Vector<String>, HashMap<String, Vector<String>>> accessibleTables = model.getAccessibleTables();

        MainView view = new MainView(accessibleTables, model.getAccessibleColumns(accessibleTables.left));
        MainController controller = new MainController(model, view);
    }
}
