/*
 *
 *    Written by Frances Belleza
 *    Edited by Eric Long
 *    Edited by Hari Sowmith Reddy
 *
 */

 package s25.cs151.application.view;

 import javafx.geometry.Pos;
 import javafx.scene.Scene;
 import javafx.scene.control.*;
 import javafx.scene.layout.*;
 import javafx.scene.paint.Color;
 import javafx.scene.text.Font;
 import javafx.stage.Stage;
 import s25.cs151.application.model.DatabaseHelper;
 import s25.cs151.application.model.OfficeHoursSession;
 import s25.cs151.application.model.TimePicker;
 import s25.cs151.application.controller.MainController;

 import java.util.ArrayList;
 import java.util.List;
 import java.time.format.DateTimeFormatter;
 import java.time.LocalTime;
 
 public class TimeSlotsPage {
 
     private Stage stage;
     private Label title, fromTime, toTime;
     private TimePicker fromHour, toHour;
     private Button addSlotButton, finishButton;
     private Button backButton;
     private final MainController controller;


     public TimeSlotsPage(Stage stage) {
         this.stage = stage;
         this.controller = new MainController(stage);
     }
 
     public Scene getScene(Stage stage) {
 
         Font istokFont = Font.font("Istok Web", 16);
 
         title = new Label("Time Slots");
         title.setFont(Font.font("Istok Web", 40));
         title.setStyle("-fx-font-weight: bold;");
 
         HBox titleBox = new HBox(20, title);
         titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

         fromTime = new Label("From:");
         toTime = new Label("To:");
         fromHour = new TimePicker();
         toHour = new TimePicker();
 
         VBox fromHourTimeBox = new VBox(10, fromTime, fromHour);
         VBox toHourTimeBox = new VBox(10, toTime, toHour);
 
         HBox timeSlotBox = new HBox(20, fromHourTimeBox, toHourTimeBox);
         timeSlotBox.setAlignment(Pos.CENTER);
 
         // buttons
         addSlotButton = new Button("Add Time Slot");
         finishButton = new Button("Finish");
         backButton = new Button("Back to Home Page");
 
         addSlotButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
         finishButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
         backButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
 
         addSlotButton.setOnAction(e -> addTimeSlot());
         finishButton.setOnAction(e -> controller.switchToCoursesPage());
         backButton.setOnAction(e -> controller.switchToHomepage());
 
         HBox buttonBox = new HBox(20, addSlotButton, finishButton, backButton);
         buttonBox.setAlignment(Pos.CENTER);
 
         // layout
         VBox layout = new VBox(20, titleBox, timeSlotBox, buttonBox);
         layout.setAlignment(Pos.CENTER);
         layout.setStyle("-fx-padding: 20 20 250 20; -fx-background-color: rgba(66, 223, 244, 0.40);");
         layout.setMaxWidth(500);
         layout.setPrefWidth(200);
 
         return new Scene(layout, 900, 600, Color.LIGHTBLUE);
     }

     private void addTimeSlot() {
         List<String> errors = new ArrayList<>();
         // Validate time inputs as before...
         if (fromHour.getHour() == 0 || fromHour.getMinute() == null ||
                 toHour.getHour() == 0 || toHour.getMinute() == null) {
             errors.add("Please select a valid time slot!");
         }

         String from = fromHour.getFormattedTime();
         String to = toHour.getFormattedTime();
         LocalTime fromTimeParsed = parseTime(from);
         LocalTime toTimeParsed = parseTime(to);

         if (!toTimeParsed.isAfter(fromTimeParsed)) {
             errors.add("End time must be later than start time.");
         }

         if (!errors.isEmpty()) {
             showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
             return;
         }

         String formattedSlot = from + " - " + to;

         // Insert time slot into the time_slots table
         boolean success = DatabaseHelper.insertTimeSlot(OfficeHoursSession.id, formattedSlot);
         if (success) {
             showAlert(Alert.AlertType.INFORMATION, "Time Slot Added!");
         } else {
             showAlert(Alert.AlertType.ERROR, "This time slot already exists!");
         }
     }
 
     private LocalTime parseTime(String timeStr) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
         return LocalTime.parse(timeStr, formatter);
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
 