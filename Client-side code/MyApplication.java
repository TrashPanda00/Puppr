

import javafx.application.Application;
import javafx.stage.Stage;
import model.LocalModel;
import model.LocalModelManager;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

/**
 * The MyApplication class sets everything up for the application to be started
 *
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class MyApplication extends Application {
    
    private LocalModel model;

    /**
     * A method to start the application
     * @param primaryStage
     *          the first stage that will be opened
     */
    @Override
    public void start(Stage primaryStage) {
        model = new LocalModelManager();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler view = new ViewHandler(viewModelFactory);

        view.start(primaryStage);
    }
}
