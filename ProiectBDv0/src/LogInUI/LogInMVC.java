package LogInUI;

public class LogInMVC {

    /**
     * Constructor of the MVC of the LogInUI
     */
    public LogInMVC() {
        LogInModel model = new LogInModel();
        LogInView view = new LogInView();
        LogInController controller = new LogInController(model, view);
    }
}
