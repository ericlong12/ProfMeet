// needed this to have a controller  - FB
//

package s25.cs151.application.controller;

import javafx.stage.Stage;
import javafx.scene.Scene;
import s25.cs151.application.view.*;

/**
 * Central controller for navigating between application views.
 * Implements methods to switch scenes based on user actions.
 */
public class MainController {
    private final Stage stage;

    public MainController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Show the main homepage.
     */
    public void switchToHomepage() {
        Homepage homepage = new Homepage(stage);
        homepage.start(stage);
    }

    /**
     * Show the Office Hours input page.
     */
    public void switchToOfficeHoursPage() {
        OfficeHoursPage officeHoursPage = new OfficeHoursPage(stage);
        Scene scene = officeHoursPage.getScene(stage);
        stage.setScene(scene);
    }

    /**
     * Show the Time Slots input page.
     */
    public void switchToTimeSlotsPage() {
        TimeSlotsPage timeSlotsPage = new TimeSlotsPage(stage);
        Scene scene = timeSlotsPage.getScene(stage);
        stage.setScene(scene);
    }

    /**
     * Show the Courses input page.
     */
    public void switchToCoursesPage() {
        CoursesPage coursesPage = new CoursesPage(stage);
        Scene scene = coursesPage.getScene(stage);
        stage.setScene(scene);
    }

    /**
     * Show the search office hours page.
     */
    public void switchToSearchOfficeHoursPage() {
        SearchOfficeHoursPage searchPage = new SearchOfficeHoursPage(stage);
        Scene scene = searchPage.getScene(stage);
        stage.setScene(scene);
    }

    /**
     * Show the stored Office Hours table view.
     */
    public void switchToOfficeHoursTableView() {
        new OfficeHoursTableView().start(new Stage());
    }

    /**
     * Show the stored Time Slots table view.
     */
    public void switchToTimeSlotsTableView() {
        new TimeSlotsTableView().start(new Stage());
    }

    /**
     * Show the stored Courses table view.
     */
    public void switchToCoursesTableView() {
        new CoursesTableView().start(new Stage());
    }

    /**
     * Show the Office Hours Schedule input page.
     */
    public void switchToOfficeHoursSchedulePage() {
        OfficeHoursSchedulePage schedulePage = new OfficeHoursSchedulePage(stage);
        Scene scene = schedulePage.getScene(stage);
        stage.setScene(scene);
    }

    /**
     * Show the Appointments table view.
     */
    public void switchToAppointmentsTableView() {
        new AppointmentsTableView().start(new Stage());
    }
}