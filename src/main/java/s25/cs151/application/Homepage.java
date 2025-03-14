package s25.cs151.application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class Homepage extends Application {

    private String currentSemester = "Spring"; // Instance variable for current semester
    private int currentYear = 2025; // Instance variable for current year

    @Override
    public void start(Stage primaryStage) {
        // Loading in the Istok Web font
        Font istokFont = Font.font("Istok Web", 16);

        // Button dashboardButton = new Button("Dashboard");
        // Button settingButton = new Button("Setting");
        Button addOfficeHoursButton = new Button("Add Office Hours");
        Button addTimeSlotsButton = new Button("Add Time Slots");
        Button addCourseButton = new Button("Add Course");
        Button addAppointmentButton = new Button("Add Appointment");
        System.out.println("Default JavaFX Font: " + javafx.scene.text.Font.getDefault()); //added this to check font we want to use Istok Web

        // dashboard function will talk about it later
        // dashboardButton.setOnAction(e -> handleDashBoard);

        // setting button function will talk about it later
        // settingButton.setOnAction(e -> handleDashBoard);

        addOfficeHoursButton.setOnAction(e -> handleAddOfficeHours(primaryStage));
        addTimeSlotsButton.setOnAction(e -> handleAddTimeSlotsButton());
        addCourseButton.setOnAction(e -> handleAddCourse());
        addAppointmentButton.setOnAction(e -> handleAddAppointment());

        Text firsTtitle = new Text("Welcome to");
        firsTtitle.setFont(istokFont); // Apply Istok Web font
        firsTtitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Text mainTitle = new Text("ProfMeet");
        mainTitle.setFont(Font.font("Istok Web", 30)); // Bigger title text
        mainTitle.setStyle("-fx-font-weight: bold;");

        // Title Layout
        VBox titleBox = new VBox(10, firsTtitle, mainTitle); // 10 is the spacing between titles
        titleBox.setStyle("-fx-alignment: center;");

        // Labels for current semester and year
        Label semesterLabel = new Label("Current Semester: " + currentSemester);
        Label yearLabel = new Label("Current Year: " + currentYear);

        // Apply Istok Web font to labels
        semesterLabel.setFont(istokFont);
        yearLabel.setFont(istokFont);

        // Box containing the current semester and year
        VBox infoBox = new VBox(10); // Vertical box with 10px spacing between the labels
        infoBox.setStyle("-fx-border-color: #000; -fx-border-width: 2px; -fx-padding: 10px;");
        infoBox.getChildren().addAll(semesterLabel, yearLabel);

        // Buttons layout
        VBox buttonBox = new VBox(10, addOfficeHoursButton, addTimeSlotsButton, addCourseButton, addAppointmentButton);
        buttonBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

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

    // Handlers for the buttons
    private void handleAddOfficeHours(Stage primaryStage) {
        // Open Office Hours Page
        OfficeHoursPage officeHoursPage = new OfficeHoursPage(primaryStage);
        officeHoursPage.show();
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
