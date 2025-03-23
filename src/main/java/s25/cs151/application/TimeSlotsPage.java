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

public class TimeSlotsPage {

    private Stage stage;
    private Label title, fromTime, toTime;
    private TimePicker fromHour, toHour;
    private Button submit;

    public TimeSlotsPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene(Stage stage) {

        Font istokFont = Font.font("Istok Web", 16);

        title = new Label("Time Slots");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // need to add time slot
        // from hour -> time picker, required
        // to hour -> time picker, required
        fromTime = new Label("From:");
        toTime = new Label("To:");
        fromHour = new TimePicker();
        toHour = new TimePicker();

        VBox fromHourTimeBox = new VBox(10, fromTime, fromHour);
        VBox toHourTimeBox = new VBox(10, toTime, toHour);

        HBox timePickerContainer = new HBox(20, fromHourTimeBox, toHourTimeBox);
        timePickerContainer.setAlignment(Pos.CENTER);
        VBox timeSlotBox = new VBox(10, fromHourTimeBox, toHourTimeBox);
        timeSlotBox.setMinSize(200, 100); // Fixed size, so that it doesn't follow the page expanding
        timeSlotBox.setMaxSize(200, 100);
        timeSlotBox.setAlignment(Pos.CENTER); // Centers the entire time slot section

        // submit button
        submit = new Button("Add Courses ->");
        submit.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-alignment: right; -fx-text-fill: white;");
        // Handles restriction
        submit.setOnAction(event -> validateForm());

        HBox buttonContainer = new HBox(submit);
        buttonContainer.setAlignment(Pos.CENTER); // Align to the bottom right
        HBox.setMargin(buttonContainer, new Insets(20, 0, 0, 0)); // 20px space from time slots


        //layout
        VBox layout = new VBox(10, titleBox, timeSlotBox, buttonContainer);
        VBox.setMargin(buttonContainer, new Insets(30, 0, 0, 0)); // 20px space from time slots
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20 20 250 20; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(500);
        layout.setPrefWidth(200);

        return new Scene(layout, 900, 600, Color.LIGHTBLUE);

//        stage.setScene(scene); //sets UI elems to the stage
//        stage.setTitle("Time Slots");
//        stage.show(); //used to display window

    }

    private void validateForm() {
        List<String> errors = new ArrayList<>();

        if (fromHour.getHour() == 0 || fromHour.getMinute() == null || fromHour.getAmPm() == null ||
                toHour.getHour() == 0 || toHour.getMinute() == null || toHour.getAmPm() == null) {
            errors.add("Please select a valid time slot!");
        }

        if (!errors.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
        } else {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success!");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Office Hours Submitted!");

            successAlert.showAndWait().ifPresent(response -> switchToCoursesPage());
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

    private void switchToCoursesPage() {
        //Open TimeSlots Page in the same window
        CoursesPage coursesPage = new CoursesPage();
        Scene coursesScene = coursesPage.getScene(stage);
        stage.setScene(coursesScene);
    }

}


