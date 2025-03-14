import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Homepage extends Application {

    private String currentSemester = "Spring"; // Instance variable for current semester
    private int currentYear = 2025; // Instance variable for current year

    @Override
    public void start(Stage primaryStage) {
        // Button dashboardButton = new Button("Dashboard");
        // Button settingButton = new Button("Setting");
        Button addOfficeHoursButton = new Button("Add Office Hours");
        Button addTimeSlotsButton = new Button("Add Time Slots");
        Button addCourseButton = new Button("Add Course");
        Button addAppointmentButton = new Button("Add Appointment");

        // dashboard function will talk about it later
        // dashboardButton.setOnAction(e -> handleDashBoard);

        // setting button function will talk about it later
        // settingButton.setOnAction(e -> handleDashBoard);

        addOfficeHoursButton.setOnAction(e -> handleAddOfficeHours());
        addTimeSlotsButton.setOnAction(e -> handleAddTimeSlotsButton());
        addCourseButton.setOnAction(e -> handleAddCourse());
        addAppointmentButton.setOnAction(e -> handleAddAppointment());

        Text firsTtitle = new Text("Welcome to");
        firsTtitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Text mainTitle = new Text("ProfMeet");
        mainTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        // Title Layout
        VBox titleBox = new VBox(10, firsTtitle, mainTitle); // 10 is the spacing between titles
        titleBox.setStyle("-fx-alignment: center;");

        // Buttons layout
        VBox buttonBox = new VBox(10, addOfficeHoursButton, addTimeSlotsButton, addCourseButton, addAppointmentButton);
        buttonBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Box containing the current semester and year
        VBox infoBox = new VBox(10); // Vertical box with 10px spacing between the labels
        infoBox.setStyle("-fx-border-color: #000; -fx-border-width: 2px; -fx-padding: 10px;");

        // Labels for current semester and year
        Label semesterLabel = new Label("Current Semester: " + currentSemester);
        Label yearLabel = new Label("Current Year: " + currentYear);

        // Add labels to the infoBox
        infoBox.getChildren().addAll(semesterLabel, yearLabel);

        // Create a VBox to hold the main title and the box below it
        VBox vbox = new VBox(20); // 20px spacing between the components
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        vbox.getChildren().addAll(titleBox, infoBox, buttonBox);

        // Create a Scene
        Scene scene = new Scene(vbox, 400, 250, Color.LIGHTBLUE);

        // Set the Stage (window)
        primaryStage.setTitle("Student Advisor Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Handlers for the buttons (Example implementations)
    private void handleAddOfficeHours() {
        System.out.println("Add Office Hours clicked");
    }

    private void handleAddTimeSlotsButton() {
        System.out.println("Add Time Slots clicked");
    }

    private void handleAddCourse() {
        System.out.println("Add Course clicked");
    }

    private void handleAddAppointment() {
        System.out.println("Add Appointment clicked");
    }

    public static void main(String[] args) {
        launch(args);

    }
}
