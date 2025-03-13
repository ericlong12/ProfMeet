/* s25.cs151.application.OfficeHoursPage - Created by Frances Belleza
*
*   GUI & Logic are both here, we can split the files
*   but I just put it here for simplicity (-Frances)
*
*
*/

package s25.cs151.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class OfficeHoursPage {
    private Stage stage;

    public OfficeHoursPage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label title = new Label("Semester's Office Hours");
        //need to set title Style

        //need to add Semester drop down
        //need to add year box
        //need to add days click boxes

        //need to link to timeslots GUI
    }
}
