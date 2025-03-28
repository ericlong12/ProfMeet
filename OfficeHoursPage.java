/* OfficeHoursPage.java
 *
 * Written by Frances Belleza
 * Edited by Hari Sowmith Reddy
 * Edited by Eric Long
 *


package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfficeHoursPage {
    private Label title, semesterLabel, yearLabel, daysLabel;
    private CheckBox mon, tues, wed, thurs, fri;
    private Button submit;
    private Button backButton;

    private TextField yearField;
    private Stage stage;
    private ComboBox<String> semesterDropdown;

    public Scene getScene(Stage stage) {
        this.stage = stage;

        Font istokFont = Font.font("Istok Web", 16);

        title = new Label("Semester's Office Hours");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        semesterLabel = new Label("Semester:");
        semesterLabel.setFont(istokFont);
        semesterLabel.setStyle("-fx-font-weight: bold;");
        semesterDropdown = new ComboBox<>();
        semesterDropdown.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterDropdown.setValue("Spring");

        VBox semesterBox = new VBox(10, semesterLabel, semesterDropdown);
        semesterBox.setMinSize(500, 10);
        semesterBox.setMaxSize(500, 10);
        semesterBox.setAlignment(Pos.CENTER_LEFT);

        yearLabel = new Label("Year:");
        yearLabel.setFont(istokFont);
        yearLabel.setStyle("-fx-font-weight: bold;");
        yearField = new TextField();
        yearField.setPromptText("2025");

        VBox yearBox = new VBox(10, yearLabel, yearField);
        yearBox.setMinSize(500, 80);
        yearBox.setMaxSize(500, 80);
        yearBox.setAlignment(Pos.CENTER_LEFT);

        daysLabel = new Label("Days:");
        daysLabel.setFont(istokFont);
        daysLabel.setStyle("-fx-font-weight: bold;");
        mon = new CheckBox("Monday");
        tues = new CheckBox("Tuesday");
        wed = new CheckBox("Wednesday");
        thurs = new CheckBox("Thursday");
        fri = new CheckBox("Friday");

        VBox daysCheckBox = new VBox(10, mon, tues, wed, thurs, fri);

        VBox daysBox = new VBox(10, daysLabel, daysCheckBox);
        daysBox.setMinSize(500, 100);
        daysBox.setMaxSize(500, 100);
        daysBox.setAlignment(Pos.CENTER_LEFT);

        submit = new Button("Add Time Slots ->");
        submit.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-alignment: right; -fx-text-fill: white;");
        submit.setOnAction(event -> validateForm());

        backButton = new Button("Back to Home Page");
        backButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-alignment: right; -fx-text-fill: white;");
        backButton.setOnAction(event -> switchToHomepage());

        HBox buttonContainer = new HBox(20, submit, backButton);
        buttonContainer.setMinSize(500, 100);
        buttonContainer.setMaxSize(500, 100);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(30, titleBox, semesterBox, yearBox, daysBox, buttonContainer);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(500);
        layout.setPrefWidth(500);
        layout.setPadding(new Insets(30));

        return new Scene(layout, 900, 600, Color.LIGHTBLUE);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void validateForm() {
        List<String> errors = new ArrayList<>();

        if (yearField.getText().isEmpty() || !yearField.getText().matches("\\d{4}")) {
            errors.add("Invalid year! Please enter a valid 4-digit year.");
        }

        List<String> selectedDays = new ArrayList<>();
        if (mon.isSelected()) selectedDays.add("Monday");
        if (tues.isSelected()) selectedDays.add("Tuesday");
        if (wed.isSelected()) selectedDays.add("Wednesday");
        if (thurs.isSelected()) selectedDays.add("Thursday");
        if (fri.isSelected()) selectedDays.add("Friday");

        if (selectedDays.isEmpty()) {
            errors.add("Please select at least one day.");
        }

        if (!errors.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
        } else {
            String semester = semesterDropdown.getValue();
            int year = Integer.parseInt(yearField.getText());
            String daysCSV = String.join(",", selectedDays);

            String timeSlots = "10:00 AM - 11:00 AM";
            String courseCode = "CS151";
            String courseName = "Software Design";

            try {
                boolean success = DatabaseHelper.insertSemester(semester, year, daysCSV, timeSlots, courseCode, courseName);

                if (success) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success!");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Office Hours Submitted!");
                    successAlert.showAndWait().ifPresent(response -> switchToTimeSlotsPage());
                } else {
                    showAlert(Alert.AlertType.ERROR, "This semester and year already exist!");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();

                if (e.getSQLState() != null && e.getSQLState().equals("23000")) {
                    showAlert(Alert.AlertType.ERROR, "This semester and year already exist!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "A database error occurred: " + e.getMessage());
                }
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void switchToTimeSlotsPage() {
        TimeSlotsPage timeSlotsPage = new TimeSlotsPage(stage);
        Scene timeSlotsScene = timeSlotsPage.getScene(stage);
        stage.setScene(timeSlotsScene);
    }

    private void switchToHomepage() {
        Homepage homepage = new Homepage(stage);
        Scene homepageScene = homepage.getScene(stage);
        stage.setScene(homepageScene);
    }
}

*/
/* OfficeHoursPage.java
 *
 * Written by Frances Belleza
 * Edited by Hari Sowmith Reddy
 * Edited by Eric Long
 *
 */

package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfficeHoursPage {
    private Label title, semesterLabel, yearLabel, daysLabel;
    private CheckBox mon, tues, wed, thurs, fri;
    private Button submit;
    private Button backButton;

    private TextField yearField;
    private Stage stage;
    private ComboBox<String> semesterDropdown;

    public Scene getScene(Stage stage) {
        this.stage = stage;

        Font istokFont = Font.font("Istok Web", 16);

        title = new Label("Semester's Office Hours");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        semesterLabel = new Label("Semester:");
        semesterLabel.setFont(istokFont);
        semesterLabel.setStyle("-fx-font-weight: bold;");
        semesterDropdown = new ComboBox<>();
        semesterDropdown.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterDropdown.setValue("Spring");

        VBox semesterBox = new VBox(10, semesterLabel, semesterDropdown);
        semesterBox.setMinSize(500, 10);
        semesterBox.setMaxSize(500, 10);
        semesterBox.setAlignment(Pos.CENTER_LEFT);

        yearLabel = new Label("Year:");
        yearLabel.setFont(istokFont);
        yearLabel.setStyle("-fx-font-weight: bold;");
        yearField = new TextField();
        yearField.setPromptText("2025");

        VBox yearBox = new VBox(10, yearLabel, yearField);
        yearBox.setMinSize(500, 80);
        yearBox.setMaxSize(500, 80);
        yearBox.setAlignment(Pos.CENTER_LEFT);

        daysLabel = new Label("Days:");
        daysLabel.setFont(istokFont);
        daysLabel.setStyle("-fx-font-weight: bold;");
        mon = new CheckBox("Monday");
        tues = new CheckBox("Tuesday");
        wed = new CheckBox("Wednesday");
        thurs = new CheckBox("Thursday");
        fri = new CheckBox("Friday");

        VBox daysCheckBox = new VBox(10, mon, tues, wed, thurs, fri);

        VBox daysBox = new VBox(10, daysLabel, daysCheckBox);
        daysBox.setMinSize(500, 100);
        daysBox.setMaxSize(500, 100);
        daysBox.setAlignment(Pos.CENTER_LEFT);

        submit = new Button("Add Time Slots ->");
        submit.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        submit.setOnAction(event -> validateForm());

        backButton = new Button("Back to Home Page");
        backButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(event -> switchToHomepage());

        HBox buttonContainer = new HBox(20, submit, backButton);
        buttonContainer.setMinSize(500, 100);
        buttonContainer.setMaxSize(500, 100);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(30, titleBox, semesterBox, yearBox, daysBox, buttonContainer);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(500);
        layout.setPrefWidth(500);
        layout.setPadding(new Insets(30));

        return new Scene(layout, 900, 600, Color.LIGHTBLUE);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void validateForm() {
        List<String> errors = new ArrayList<>();

        if (yearField.getText().isEmpty() || !yearField.getText().matches("\\d{4}")) {
            errors.add("Invalid year! Please enter a valid 4-digit year.");
        }

        List<String> selectedDays = new ArrayList<>();
        if (mon.isSelected()) selectedDays.add("Monday");
        if (tues.isSelected()) selectedDays.add("Tuesday");
        if (wed.isSelected()) selectedDays.add("Wednesday");
        if (thurs.isSelected()) selectedDays.add("Thursday");
        if (fri.isSelected()) selectedDays.add("Friday");

        if (selectedDays.isEmpty()) {
            errors.add("Please select at least one day.");
        }

        if (!errors.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
        } else {
            String semester = semesterDropdown.getValue();
            int year = Integer.parseInt(yearField.getText());
            String daysCSV = String.join(",", selectedDays);

            String timeSlots = "10:00 AM - 11:00 AM";
            String courseCode = "CS151";
            String courseName = "Software Design";

            try {
                boolean success = DatabaseHelper.insertSemester(semester, year, daysCSV, timeSlots, courseCode, courseName, "");

                if (success) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success!");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Office Hours Submitted!");
                    successAlert.showAndWait().ifPresent(response -> switchToTimeSlotsPage());
                } else {
                    showAlert(Alert.AlertType.ERROR, "This semester and year already exist!");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage());
            }
        }
    }

    private void switchToTimeSlotsPage() {
        TimeSlotsPage timeSlotsPage = new TimeSlotsPage(stage);
        Scene timeSlotsScene = timeSlotsPage.getScene(stage);
        stage.setScene(timeSlotsScene);
    }

    private void switchToHomepage() {
        Homepage homepage = new Homepage(stage);
        Scene homepageScene = homepage.getScene(stage);
        stage.setScene(homepageScene);
    }
}