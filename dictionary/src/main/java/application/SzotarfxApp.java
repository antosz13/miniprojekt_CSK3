package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Szotar;

public class SzotarfxApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Szotar szotar = new Szotar(); // Create an instance of the model
        SzotarController controller = new SzotarController(szotar); // Pass model to controller
        controller.setup(primaryStage); // Start the controller
    }
}