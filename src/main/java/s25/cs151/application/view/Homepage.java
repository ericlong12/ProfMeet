/*
*       Written by Eric Long & Thao Nguyen
*       Edited by Frances Belleza
*
*/

package s25.cs151.application.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import s25.cs151.application.controller.MainController;

public class Homepage extends Application {
    private final MainController controller;

    private String currentSemester = "Spring"; // Instance variable for current semester
    private int currentYear = 2025; // Instance variable for current year
    private Stage stage;

    public Homepage(Stage primaryStage) {
        this.stage = primaryStage;
        this.controller = new MainController(primaryStage);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage= primaryStage;

        // Loading in the Istok Web font
        Font istokFont = Font.font("Istok Web", 16);

        // Button dashboardButton = new Button("Dashboard");
        // Button settingButton = new Button("Setting");
        Button addOfficeHoursButton = new Button("Add Office Hours");
        Button addTimeSlotsButton = new Button("Add Time Slots");
        Button addCourseButton = new Button("Add Course");
        Button viewSemesterButton = new Button("View Semester Office Hours");
        Button viewTimeSlotsButton = new Button("View Time Slots"); // 👈 New Button
        Button viewCoursesButton = new Button("View Courses");
        Button addOfficeHoursScheduleButton = new Button("Add Office Hours Schedule"); // 👈 New Button
        Button viewAppointmentsButton = new Button("View Appointments"); // 👈 New Button
        Button viewSearchOfficeHoursPage = new Button("Search Office Hours");
        System.out.println("Default JavaFX Font: " + javafx.scene.text.Font.getDefault()); //added this to check font we want to use (Istok Web)

        // dashboard function will talk about it later
        // dashboardButton.setOnAction(e -> handleDashBoard); //from frances -> add to MainController too

        // setting button function will talk about it later
        // settingButton.setOnAction(e -> handleDashBoard); //from frances -> add to MainController too

        addOfficeHoursButton.setOnAction(e -> controller.switchToOfficeHoursPage());
        addTimeSlotsButton.setOnAction(e -> controller.switchToTimeSlotsPage());
        addCourseButton.setOnAction(e -> controller.switchToCoursesPage());
        viewSemesterButton.setOnAction(e -> controller.switchToOfficeHoursTableView());
        viewTimeSlotsButton.setOnAction(e -> controller.switchToTimeSlotsTableView());
        viewCoursesButton.setOnAction(e -> controller.switchToCoursesTableView());
        addOfficeHoursScheduleButton.setOnAction(e -> controller.switchToOfficeHoursSchedulePage());
        viewAppointmentsButton.setOnAction(e -> controller.switchToAppointmentsTableView());
        viewSearchOfficeHoursPage.setOnAction (e -> controller.switchToSearchOfficeHoursPage());


        Text firsTtitle = new Text("Welcome to");
        firsTtitle.setFont(istokFont); // Apply Istok Web font
        firsTtitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // ProfMeet Title 📅
        Text emoji = new Text("\uD83D\uDCC5"); // The Unicode I used for 📅
        emoji.setFont(Font.font("Istok Web", 40));

        Text mainTitle = new Text("ProfMeet");
        mainTitle.setFont(Font.font("Istok Web", 50)); // tile text size
        mainTitle.setStyle("-fx-font-weight: bold;");

        // Add the Emoji
        HBox titleWithEmoji = new HBox(10, emoji, mainTitle); // 10px spacing between emoji and text
        titleWithEmoji.setStyle("-fx-alignment: center;");

        // Title Layout
        VBox titleBox = new VBox(10, firsTtitle, titleWithEmoji); // 10 is the spacing between titles
        titleBox.setStyle("-fx-alignment: center;");

        // Labels for current semester and year
        Label semesterLabel = new Label("Current Semester: " + currentSemester);
        Label yearLabel = new Label("Current Year: " + currentYear);

        // Apply Istok Web font to labels
        semesterLabel.setFont(istokFont);
        yearLabel.setFont(istokFont);

        // Box containing the current semester and year
        VBox infoBox = new VBox(10, semesterLabel, yearLabel);
        infoBox.setMinSize(300, 80); // Fixed size, so that it doesn't follow the page expanding
        infoBox.setMaxSize(300, 80);
        infoBox.setStyle("-fx-border-color: #000; -fx-border-width: 2px; -fx-padding: 10px; -fx-alignment: center;");

        // Center the text inside the box
        StackPane centeredBox = new StackPane(infoBox);
        centeredBox.setStyle("-fx-alignment: center;");

        // Buttons layout
        VBox buttonBox = new VBox(10, addOfficeHoursButton,
                addTimeSlotsButton,
                addCourseButton,
                viewSemesterButton,
                viewTimeSlotsButton,
                viewCoursesButton,
                addOfficeHoursScheduleButton,
                viewAppointmentsButton,
                viewSearchOfficeHoursPage);
        buttonBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Create a VBox to hold the main title and the box below it
        VBox vbox = new VBox(20, titleBox, centeredBox, buttonBox);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: rgba(66, 223, 244, 0.40);");

        // Create a Scene with a bigger window size
        Scene scene = new Scene(vbox, 900, 600, Color.LIGHTBLUE);

        // Set the Stage (window)
        primaryStage.setTitle("Student Advisor Registration");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);  // picks the width
        primaryStage.setMinHeight(600); // picks the height
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
