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

    @Override
    public void start(Stage secondaryStage) {

        OfficeHoursPage officeHoursPage = new OfficeHoursPage(secondaryStage);
        officeHoursPage.show();

    }

}