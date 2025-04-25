package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
public class SearchOfficeHoursPage {
    private Stage stage;
    private TextField searchBox;
    private Label title;
    private Button searchButton, homepageButton;
    public SearchOfficeHoursPage(Stage stage) {
        this.stage = stage;
    }

    //two buttons needed "Search" & "Back to Homepage"
    public Scene getScene(Stage stage) {
        Font istokFont = Font.font("Istok Web", 16);

        title = new Label("Search Office Hours");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        searchBox = new TextField();
        searchBox.setPromptText("Search Office Hours Schedules");

        VBox searchVBox = new VBox(10, searchBox);
        searchVBox.setMinSize(500, 100); // Fixed size, so that it doesn't follow the page expanding
        searchVBox.setMaxSize(500, 100);
        searchVBox.setAlignment(Pos.CENTER_LEFT);

        searchButton = new Button("Search");
        searchButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        searchButton.setAlignment(Pos.CENTER);

        homepageButton = new Button("Back to Homepage");
        homepageButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        homepageButton.setOnAction(event -> {
            switchToHomepage(event);
        });

        HBox forButtons = new HBox(20, searchButton, homepageButton);
        forButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, titleBox, searchVBox, forButtons);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20 20 250 20; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(500);
        layout.setPrefWidth(100);

        return new Scene(layout, 900, 600, Color.LIGHTBLUE);
    }

    private void switchToHomepage(ActionEvent event) {
        Homepage homepage = new Homepage(stage);
        homepage.start(stage);
    }
}
