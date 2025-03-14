/* OfficeHoursPage.java
*
*    Written by Frances Belleza
*    Edited by Hari Sowmith Reddy
*
*/

package s25.cs151.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OfficeHoursPage {
    private Stage stage;
    private Label title, semesterLabel, yearLabel,
            daysLabel, timeSlots, fromTime, toTime, courses;
    private CheckBox mon, tues, wed, thurs, fri;
    private Button submit;


    public OfficeHoursPage(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        Font istokFont = Font.font("Istok Web", 16);

        //title
        title = new Label("Semester's Office Hours");
        title.setFont(Font.font("Istok Web", 30));
        title.setStyle("-fx-font-weight: bold;");


        // semester
            // dropdown, single select, required, default = Spring from list S, Sum, W
        semesterLabel = new Label("Semester:");
        ComboBox<String> semesterDropdown = new ComboBox<>();
        semesterDropdown.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterDropdown.setValue("Spring");

        // year box
            // text field, required, accepted value = 4-digit int
        yearLabel = new Label("Year");
        TextField yearField = new TextField();
        yearField.setPromptText("2025");

        // days
            // 5-check boxes, required, hard code Mon-Fri
        daysLabel = new Label("Days:");
        mon = new CheckBox("Monday");
        tues = new CheckBox("Tuesday");
        wed = new CheckBox("Wednesday");
        thurs = new CheckBox("Thursday");
        fri = new CheckBox("Friday");

        VBox daysBox = new VBox(10, mon, tues, wed, thurs, fri);


        // need to add time slot
            // from hour -> time picker, required
            // to hour -> time picker, required
        fromTime = new Label("From:");
        toTime = new Label("To:");
        timeSlots = new Label("Time Slots:");
        TimePicker fromHour = new TimePicker();
        TimePicker toHour = new TimePicker();

        VBox fromHourTimeBox = new VBox(10, fromTime, fromHour);
        VBox toHourTimeBox = new VBox(10, toTime, toHour);


        // need to add courses
            // course code -> text field, required, strings only
            // course name -> text field, required, strings only
            // section number -> text field, required, string only
        courses = new Label("Courses");
        TextField courseCode = new TextField();
        courseCode.setPromptText("Course Code");
        TextField courseName = new TextField();
        courseName.setPromptText("Course Name");
        TextField courseSection = new TextField();
        courseSection.setPromptText("Course Section");


        // submit button
        // Handles restriction too
        submit = new Button("Submit");
        submit.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        // set on action needed an evenHandler in it
        // needed imports ActionEvent, EventHandler
        // fromHour & toHour not in TimePicker class can't use getValue
        // changed to getHour() [-FB]
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<String> errors = new ArrayList<>();

                if (yearField.getText().isEmpty() || yearField.getText().length() != 4) {
                    errors.add("Invalid year! Please enter a valid 4-digit year!");
                }

                if (!mon.isSelected() && !tues.isSelected() && !wed.isSelected() && !thurs.isSelected() && !fri.isSelected()) {
                    errors.add("Please enter at least one day!");
                }

                if (fromHour.getHour() == null || fromHour.getMinute() == null || fromHour.getAmPm() == null ||
                        toHour.getHour() == null || toHour.getMinute() == null || toHour.getAmPm() == null) {
                    errors.add("Please select a valid time slot!");
                }

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
                    showAlert(Alert.AlertType.ERROR, "Error", String.join("\n", errors));
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Office Hours Submitted!");
                }
            }
        });


        // Layout in vbox ~ what we want to show on scene
        // I spaced it out like this so we can easily see which belong to which -FB
        VBox layout = new VBox(15, title, semesterLabel, semesterDropdown,
                yearLabel, yearField,
                daysLabel, daysBox,
                timeSlots, fromHourTimeBox, toHourTimeBox,
                courses, courseCode, courseName, courseSection,
                submit);
        layout.setStyle("-fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Office Hours Page");
        stage.show();

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
