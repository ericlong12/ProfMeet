/* OfficeHoursPage.java
 *
 *    Written by Frances Belleza
 *
 */

package s25.cs151.application;


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

public class CoursesPage {
    private Stage stage;
    private Label courses, title;
    private TextField courseCode, courseName, courseSection;
    private Button submit;

//    public CoursesPage(Stage stage) {
//        this.stage = stage;
//    }

    public Scene getScene(Stage stage){
        Font istokFont = Font.font("Istok Web", 16);

        title = new Label("Courses");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // need to add courses
        // course code -> text field, required, strings only
        // course name -> text field, required, strings only
        // section number -> text field, required, string only
        courses = new Label("Courses:");
        courses.setFont(Font.font("Istok Web", 16));
        courses.setStyle("-fx-font-weight: bold;");
        courseCode = new TextField();
        courseCode.setPromptText("Course Code");
        courseName = new TextField();
        courseName.setPromptText("Course Name");
        courseSection = new TextField();
        courseSection.setPromptText("Course Section");

        VBox coursesBox = new VBox(10, courses, courseCode, courseName, courseSection);
        coursesBox.setMinSize(500, 100); // Fixed size, so that it doesn't follow the page expanding
        coursesBox.setMaxSize(500, 100);
        coursesBox.setAlignment(Pos.CENTER_LEFT);

        // submit button
        submit = new Button("Submit");
        submit.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-alignment: right; -fx-text-fill: white;");
        // Handles restriction
        submit.setOnAction(event -> validateForm());
        HBox buttonContainer = new HBox(submit);
        buttonContainer.setAlignment(Pos.CENTER); // Align to the bottom right
        HBox.setMargin(buttonContainer, new Insets(20, 0, 0, 0)); // 20px space from time slots

        //layout
        VBox layout = new VBox(10, titleBox, coursesBox, buttonContainer);
        VBox.setMargin(buttonContainer, new Insets(30, 0, 0, 0)); // 20px space from time slots
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20 20 250 20; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(500);
        layout.setPrefWidth(200);

        return new Scene(layout, 900, 600, Color.LIGHTBLUE);

//        Scene scene = new Scene(layout, 900, 600, Color.LIGHTBLUE);
//        stage.setScene(scene); //sets UI elems to the stage
//        stage.setTitle("Time Slots");
//        stage.show(); //used to display window

    }

    public void validateForm() {
        List<String> errors = new ArrayList<>();

        if (courseCode.getText().isEmpty()) {
            errors.add("Please enter a valid Course Code!");
        }

        if (courseName.getText().isEmpty()) {
            errors.add("Course Name is required!");
        }

        if (courseSection.getText().isEmpty()) {
            errors.add("Course Section is required!");
        }

        if (!errors.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
        } else {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success!");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Office Hours Submitted!");

            successAlert.showAndWait().ifPresent(response -> switchToHomepage());
        }

    }

    private void showAlert(Alert.AlertType type, String message) {
        String title = (type == Alert.AlertType.ERROR) ? "Error" : "Notification";

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToHomepage() {
        Stage homepageStage = new Stage(); // Create a new stage
        Homepage homepage = new Homepage(homepageStage);
        homepage.start(homepageStage);

        stage.close();
    }

}


