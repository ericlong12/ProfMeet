/* s25.cs151.application.OfficeHoursPage.java
    Created by Frances Belleza & Edited by Hari Sowmith Reddy
*
*   GUI & Logic are both here (-Frances)
*
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


public class OfficeHoursPage {
    private Stage stage;

    public OfficeHoursPage(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        Label title = new Label("Semester's Office Hours");
        title.setFont(new Font("Istok Web",20));

        // semester
            // dropdown, single select, required, default = Spring from list S, Sum, W
        Label semesterLabel = new Label("Semester:");
        ComboBox<String> semesterDropdown = new ComboBox<>();
        semesterDropdown.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterDropdown.setValue("Spring");

        // year box
            // text field, required, accepted value = 4-digit int
        Label yearLabel = new Label("Year");

        TextField yearField = new TextField();
        yearField.setPromptText("2025");

        // need to add days click boxes
            // 5-check boxes, required, hard code Mon-Fri
        Label dayLabel = new Label("Days");

        CheckBox mon = new CheckBox("Monday");
        CheckBox tues = new CheckBox("Tuesday");
        CheckBox wed = new CheckBox("Wednesday");
        CheckBox thurs = new CheckBox("Thursday");
        CheckBox fri = new CheckBox("Friday");

        HBox daysBox = new HBox(10, mon, tues, wed, thurs, fri);


        // need to add time slot
            // from hour -> time picker, required
            // to hour -> time picker, required

        // need to add courses
            // course code -> text field, required, strings only
            // course name -> text field, required, strings only
            //section number -> text field, required, string only


        // Layout
        VBox layout = new VBox(15, title, semesterLabel, semesterDropdown, yearLabel, yearField, daysBox);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Office Hours Page");
        stage.show();

    }
}
