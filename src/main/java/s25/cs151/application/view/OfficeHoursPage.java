/* OfficeHoursPage.java
*
*    Written by Frances Belleza
*    Edited by Hari Sowmith Reddy
*    Edited by Eric Long
*
*/

package s25.cs151.application.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.List;

import s25.cs151.application.controller.MainController;
import s25.cs151.application.model.DatabaseHelper;
import s25.cs151.application.model.OfficeHoursSession;


public class OfficeHoursPage {
    private final MainController controller;
    private Label title, semesterLabel, yearLabel,
            daysLabel, courses;
    private CheckBox mon, tues, wed, thurs, fri;
    private Button submit;
    private Button backButton;

    private TextField yearField;
    private Stage stage; // Store the Stage
    private ComboBox<String> semesterDropdown;


    public OfficeHoursPage(Stage stage) {
        this.stage = stage;
        this.controller = new MainController(stage);
    }

    public Scene getScene(Stage stage) {
        this.stage= stage;

        Font istokFont = Font.font("Istok Web", 16);

        //title
        title = new Label("Semester's Office Hours");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // semester
            // dropdown, single select, required, default = Spring from list S, Sum, W
        semesterLabel = new Label("Semester:");
        semesterLabel.setFont(Font.font("Istok Web", 16));
        semesterLabel.setStyle("-fx-font-weight: bold;");
        semesterDropdown = new ComboBox<>();
        semesterDropdown.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterDropdown.setValue("Spring");

        VBox semesterBpx = new VBox(10, semesterLabel, semesterDropdown);
        semesterBpx.setMinSize(500, 10); // Fixed size, so that it doesn't follow the page expanding
        semesterBpx.setMaxSize(500, 10);
        semesterBpx.setAlignment(Pos.CENTER_LEFT);

        // year box
            // text field, required, accepted value = 4-digit int
        yearLabel = new Label("Year:");
        yearLabel.setFont(Font.font("Istok Web", 16));
        yearLabel.setStyle("-fx-font-weight: bold;");
        yearField = new TextField();
        yearField.setPromptText("2025");

        VBox yearBox = new VBox(10, yearLabel, yearField);
        yearBox.setMinSize(500, 80); // Fixed size, so that it doesn't follow the page expanding
        yearBox.setMaxSize(500, 80);
        yearBox.setAlignment(Pos.CENTER_LEFT);

        // days
            // 5-check boxes, required, hard code Mon-Fri
        daysLabel = new Label("Days:");
        daysLabel.setFont(Font.font("Istok Web", 16));
        daysLabel.setStyle("-fx-font-weight: bold;");
        mon = new CheckBox("Monday");
        tues = new CheckBox("Tuesday");
        wed = new CheckBox("Wednesday");
        thurs = new CheckBox("Thursday");
        fri = new CheckBox("Friday");

        VBox daysCheckBox = new VBox(10, mon, tues, wed, thurs, fri);

        VBox daysBox = new VBox(10,daysLabel, daysCheckBox);
        daysBox.setMinSize(500, 100); // Fixed size, so that it doesn't follow the page expanding
        daysBox.setMaxSize(500, 100);
        daysBox.setAlignment(Pos.CENTER_LEFT);

        // submit button
        submit = new Button("Add Time Slots ->");
        submit.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-alignment: right; -fx-text-fill: white;");
        // Handles restriction
        submit.setOnAction(event -> validateForm());

        // back to home page button
        backButton = new Button("Back to Home Page");
        backButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        // sets action for back button
        backButton.setOnAction(event -> controller.switchToHomepage());

        HBox buttonContainer = new HBox(20, submit, backButton);
        buttonContainer.setMinSize(500, 100); // Fixed size, so that it doesn't follow the page expanding
        buttonContainer.setMaxSize(500, 100);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT); // Align to the bottom right


        // Layout in vbox ~ what we want to show on scene
        VBox layout = new VBox(30, titleBox,
                semesterBpx,
                yearBox,
                daysBox,
                buttonContainer);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(500); // Adjust the width of the VBox
        layout.setPrefWidth(500);
        layout.setPadding(new Insets(30));

        return new Scene(layout, 900, 600, Color.LIGHTBLUE);
//        stage.setScene(scene); //sets UI elems to the stage
//        stage.setTitle("Office Hours Page");
//        stage.show(); //used to display window

    }

    private void showAlert(Alert.AlertType type, String message) {
        String title = (type == Alert.AlertType.ERROR) ? "Error" : "Notification";

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void validateForm() {
        List<String> errors = new ArrayList<>();

        // Validate year & days as before...
        if (yearField.getText().isEmpty() || !yearField.getText().matches("\\d{4}")) {
            errors.add("Please enter a valid 4-digit year!");
        }
        List<String> selectedDays = new ArrayList<>();
        if (mon.isSelected()) selectedDays.add("Monday");
        if (tues.isSelected()) selectedDays.add("Tuesday");
        if (wed.isSelected()) selectedDays.add("Wednesday");
        if (thurs.isSelected()) selectedDays.add("Thursday");
        if (fri.isSelected()) selectedDays.add("Friday");
        if (selectedDays.isEmpty()) {
            errors.add("Please select at least one day!");
        }

        if (!errors.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
            return;
        }

        // Insert Office Hours into the database
        int officeHourId = DatabaseHelper.insertOfficeHours(
                semesterDropdown.getValue(),
                Integer.parseInt(yearField.getText()),
                String.join(", ", selectedDays)
        );

        if (officeHourId != -1) {
            // Save the officeHourId in a session class for later use
            OfficeHoursSession.id = officeHourId;
            // Switch to the next page, e.g., TimeSlotsPage
            controller.switchToTimeSlotsPage();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to save office hours!");
        }
    }


}
