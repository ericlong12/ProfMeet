/*
 *    Written by Frances Belleza
 *    Edited by Hari Sowmith Reddy
 *
 *      (3.27) FB - What do i need to do?
 *              [x] add coursesTableView
 *              [x] add method in CoursesPage to handle data
 *              [x] add button in homepage to view coursesTable
 *              [x] seperate courses class
 *              [x] add courses info into database helper
 *              [x] add courseSection into OfficeHours
 *              [x] fix bug from courses to homepage
 *              [] order table by course code
 *
 */

 package s25.cs151.application.view;


 import javafx.event.ActionEvent;
 import javafx.geometry.Insets;
 import javafx.geometry.Pos;
 import javafx.scene.Scene;
 import javafx.scene.control.*;
 import javafx.scene.layout.*;
 import javafx.scene.paint.Color;
 import javafx.scene.text.Font;
 import javafx.stage.Stage;
 import java.util.ArrayList;
 import java.util.List;

 import s25.cs151.application.model.DatabaseHelper;
 import s25.cs151.application.model.OfficeHoursSession;
 import s25.cs151.application.controller.MainController;


public class CoursesPage {
    private final MainController controller;
     private Stage stage; // Store the stage
     private Label courses, title;
     private TextField courseCode, courseName, courseSection;
     private Button submit;
     private Button backButton;
 
     public CoursesPage(Stage stage) { // Constructor to receive the stage
         this.stage = stage;
         this.controller = new MainController(stage);
     }
 
     public Scene getScene(Stage stage){
         Font istokFont = Font.font("Istok Web", 16);
 
         title = new Label("Courses");
         title.setFont(Font.font("Istok Web", 40));
         title.setStyle("-fx-font-weight: bold;");
 
         HBox titleBox = new HBox(20, title);
         titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
 
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
         coursesBox.setMinSize(500, 100); // Fixed size, so that it doesn't follow the page expanding
         coursesBox.setMaxSize(500, 100);
         coursesBox.setAlignment(Pos.CENTER_LEFT);
 
         // buttons
         submit = new Button("Submit");
         submit.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
         submit.setAlignment(Pos.CENTER);
 
         submit.setOnAction(event -> addCourse());
         //finish.setOnAction(event ->addCourse());
 
         // back to home page button
         backButton = new Button("Back To Home Page");
         backButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
 
         backButton.setAlignment(Pos.CENTER);
 
         backButton.setOnAction(event -> {
             controller.switchToHomepage(); // Pass the ActionEvent
         });
 
 
         HBox buttonContainer = new HBox(20, submit, backButton);
         buttonContainer.setAlignment(Pos.CENTER); // Align to the bottom right
         HBox.setMargin(buttonContainer, new Insets(20, 0, 0, 0)); // 20px space from time slots
 
         //layout
         VBox layout = new VBox(10, titleBox, coursesBox, buttonContainer);
         VBox.setMargin(buttonContainer, new Insets(30, 0, 0, 0)); // 20px space from time slots
         layout.setAlignment(Pos.CENTER);
         layout.setStyle("-fx-padding: 20 20 250 20; -fx-background-color: rgba(66, 223, 244, 0.40);");
         layout.setMaxWidth(500);
         layout.setPrefWidth(200);
 
         return new Scene(layout, 900, 600, Color.LIGHTBLUE);
 
         //        Scene scene = new Scene(layout, 900, 600, Color.LIGHTBLUE);
         //        stage.setScene(scene); //sets UI elems to the stage
         //        stage.setTitle("Time Slots");
         //        stage.show(); //used to display window
 
     }

     private void addCourse() {
         List<String> errors = new ArrayList<>();

         // Validate course fields
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
             return;
         }

         // Insert course data into the courses table
         boolean success = DatabaseHelper.insertCourse(
                 OfficeHoursSession.id,
                 courseCode.getText().trim(),
                 courseName.getText().trim(),
                 courseSection.getText().trim()
         );

         if (success) {
             showAlert(Alert.AlertType.INFORMATION, "Course Added!");
         } else {
             showAlert(Alert.AlertType.ERROR, "Failed to add course. It might already exist.");
         }
     }

     private void showAlert(Alert.AlertType type, String message) {
         String title = (type == Alert.AlertType.ERROR) ? "Error" : "Notification";
 
         Alert alert = new Alert(type);
         alert.setTitle(title);
         alert.setHeaderText(null);
         alert.setContentText(message);
         alert.showAndWait();
     }

 
 }