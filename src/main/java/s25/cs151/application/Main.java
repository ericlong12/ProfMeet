package s25.cs151.application;

import javafx.application.Application;
import javafx.stage.Stage;
import s25.cs151.application.model.DatabaseHelper;
import s25.cs151.application.view.Homepage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // Homepage is here
    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseHelper.initializeDatabase();
    
        Homepage homepage = new Homepage(primaryStage);
        homepage.start(primaryStage);
    }

}