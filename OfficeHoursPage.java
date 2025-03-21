/* OfficeHoursPage.java
 *
 *    Written by Frances Belleza
 *    Edited by Hari Sowmith Reddy
 *
 */

package s25cs151.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import s25cs151.application.TimeSlotsPage;

public class OfficeHoursPage {
    private Label title, semesterLabel, yearLabel,
            daysLabel, courses;
    private CheckBox mon, tues, wed, thurs, fri;
    private Button submit;

    private TextField yearField;
    private Stage stage; // Store the Stage
    private ListView<String> officeHoursListView;



//    public OfficeHoursPage(Stage stage) {
//         this.stage = stage;
//    }

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
        ComboBox<String> semesterDropdown = new ComboBox<>();
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

        HBox buttonContainer = new HBox(submit);
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

        officeHoursListView = new ListView<>();
        updateOfficeHoursList();

        VBox officeHoursListBox = new VBox(10, officeHoursListView);

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

        if (yearField.getText().isEmpty() || yearField.getText().length() != 4) {
            errors.add("Invalid year! Please enter a valid 4-digit year!");
        }

        if (!mon.isSelected() && !tues.isSelected() && !wed.isSelected() && !thurs.isSelected() && !fri.isSelected()) {
            errors.add("Please enter at least one day!");
        }

        if (!errors.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
        } else {

            StringBuilder selectedDays = new StringBuilder();
            if (mon.isSelected()) selectedDays.append("Monday ");
            if (tues.isSelected()) selectedDays.append("Tuesday ");
            if (wed.isSelected()) selectedDays.append("Wednesday ");
            if (thurs.isSelected()) selectedDays.append("Thursday ");
            if (fri.isSelected()) selectedDays.append("Friday ");

            // Saves to database
            OfficeHoursDataHandling dbHelper = new OfficeHoursDataHandling();
            dbHelper.addOfficeHours("Spring", Integer.parseInt(yearField.getText()), selectedDays.toString().trim());

            showAlert(Alert.AlertType.INFORMATION, "Office Hours added successfully!");
            switchToTimeSlotsPage();
        }
    }

    private void switchToTimeSlotsPage() {
        TimeSlotsPage timeSlotsPage = new TimeSlotsPage(stage);
        Scene timeSlotsScene = timeSlotsPage.getScene(stage);
        stage.setScene(timeSlotsScene);
    }

    private void updateOfficeHoursList() {
        OfficeHoursDataHandling dbHelper = new OfficeHoursDataHandling();
        officeHoursListView.getItems().clear();

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM office_hours")) {

            while (rs.next()) {
                String officeHours = rs.getString("semester") + " " + rs.getInt("year") + " - " + rs.getString("days");
                officeHoursListView.getItems().add(officeHours);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
