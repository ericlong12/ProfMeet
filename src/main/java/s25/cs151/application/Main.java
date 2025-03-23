package s25.cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // PRIMARY STAGE FOR HOMEPAGE
    @Override
    public void start(Stage primaryStage) {

        // Open Homepage first instead of OfficeHoursPage
        Homepage homepage = new Homepage(primaryStage);
        homepage.start(primaryStage);

    }

}
