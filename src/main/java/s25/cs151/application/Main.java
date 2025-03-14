package s25.cs151.application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // PRIMARY STAGE FOR HOMEPAGE
    @Override
    public void start(Stage primaryStage) {
        Homepage homepage = new Homepage();
        homepage.start(primaryStage); // do this to launch homepage first
    }
}
