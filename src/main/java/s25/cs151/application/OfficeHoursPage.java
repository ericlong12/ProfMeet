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
import javafx.geometry.Pos;
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

    private TextField yearField, courseCode, courseName, courseSection;
    private TimePicker fromHour, toHour;



    public OfficeHoursPage(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        Font istokFont = Font.font("Istok Web", 16);

        //title
        title = new Label("Semester's Office Hours");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

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


        // need to add time slot
            // from hour -> time picker, required
            // to hour -> time picker, required
        timeSlots = new Label("Time Slots:");
        timeSlots.setFont(Font.font("Istok Web", 16));
        timeSlots.setStyle("-fx-font-weight: bold;");
        fromTime = new Label("From:");
        toTime = new Label("To:");
        fromHour = new TimePicker();
        toHour = new TimePicker();

        VBox fromHourTimeBox = new VBox(10, fromTime, fromHour);
        VBox toHourTimeBox = new VBox(10, toTime, toHour);

        HBox timePickerContainer = new HBox(20, fromHourTimeBox, toHourTimeBox);
        timePickerContainer.setAlignment(Pos.CENTER_LEFT);

        VBox timeSlotBox = new VBox(10, timeSlots, fromHourTimeBox, toHourTimeBox);
        timeSlotBox.setMinSize(500, 180); // Fixed size, so that it doesn't follow the page expanding
        timeSlotBox.setMaxSize(500, 180);
        timeSlotBox.setAlignment(Pos.CENTER_LEFT); // Centers the entire time slot section



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
        coursesBox.setMinSize(500, 80); // Fixed size, so that it doesn't follow the page expanding
        coursesBox.setMaxSize(500, 80);
        coursesBox.setAlignment(Pos.CENTER_LEFT);


        // submit button
        submit = new Button("Submit");
        submit.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-alignment: right; -fx-text-fill: white;");
        // Handles restriction
        submit.setOnAction(event -> validateForm());

        HBox buttonContainer = new HBox(submit);
        buttonContainer.setMinSize(500, 100); // Fixed size, so that it doesn't follow the page expanding
        buttonContainer.setMaxSize(500, 100);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT); // Align to the bottom right


        // Layout in vbox ~ what we want to show on scene
        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        VBox layout = new VBox(30, titleBox,
                semesterBpx,
                yearBox,
                daysBox,
                timeSlotBox,
                coursesBox,
                buttonContainer);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(500); // Adjust the width of the VBox
        layout.setPrefWidth(500);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 900, 700);
        stage.setScene(scene); //sets UI elems to the stage
        stage.setTitle("Office Hours Page");
        stage.show(); //used to display window

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

        if (fromHour.getHour() == 0 || fromHour.getMinute() == null || fromHour.getAmPm() == null ||
                toHour.getHour() == 0 || toHour.getMinute() == null || toHour.getAmPm() == null) {
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
            showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Office Hours Submitted!");
        }
    }

}
