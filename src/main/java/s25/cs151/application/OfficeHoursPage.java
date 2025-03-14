/* s25.cs151.application.OfficeHoursPage
*
*    Written by Frances Belleza
*    Edited by Hari Sowmith Reddy
*
*   GUI & Logic are both here (-Frances)
*   Still need to:
*       [] add exceptions for all instances to make them "required"
*
*/

package s25.cs151.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.time.LocalTime;

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

        title = new Label("Semester's Office Hours");
        title.setFont(new Font("Istok Web",30));

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


        // submit button -> then pop up comes "Confirmed."
        submit = new Button("Submit");



        // Layout in vbox ~ what we want to show on scene
        // I spaced it out like this so we can easily see which belong to which -FB
        VBox layout = new VBox(15, title, semesterLabel, semesterDropdown,
                yearLabel, yearField,
                daysLabel, daysBox,
                timeSlots, fromHourTimeBox, toHourTimeBox,
                courses, courseCode, courseName, courseSection);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Office Hours Page");
        stage.show();

    }
}
