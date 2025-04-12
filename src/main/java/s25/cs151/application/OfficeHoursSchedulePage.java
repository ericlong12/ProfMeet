package s25.cs151.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OfficeHoursSchedulePage {
    private Stage stage; 
    private Label titleLabel;
    private TextField studentNameField;
    private DatePicker scheduleDatePicker;
    private ComboBox<String> timeSlotComboBox;
    private ComboBox<String> courseComboBox;
    private TextField reasonField;
    private TextField commentField;
    private Button submitButton;
    private Button backButton;

    public OfficeHoursSchedulePage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene(Stage stage) {
        this.stage = stage;

        Font istokFont = Font.font("Istok Web", 16);

        titleLabel = new Label("Office Hours Schedule");
        titleLabel.setFont(Font.font("Istok Web", 40));
        titleLabel.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20));

        // Create form fields
        studentNameField = new TextField();
        studentNameField.setPromptText("Student Full Name");

        scheduleDatePicker = new DatePicker();
        scheduleDatePicker.setValue(LocalDate.now()); // Default = today

        timeSlotComboBox = new ComboBox<>();
        loadTimeSlots(); // Load from database
        if (!timeSlotComboBox.getItems().isEmpty()) {
            timeSlotComboBox.getSelectionModel().selectFirst();
        }

        courseComboBox = new ComboBox<>();
        loadCourses(); // Load from database
        if (!courseComboBox.getItems().isEmpty()) {
            courseComboBox.getSelectionModel().selectFirst();
        }

        reasonField = new TextField();
        reasonField.setPromptText("Reason (Optional)");

        commentField = new TextField();
        commentField.setPromptText("Comment (Optional)");

        // Create layout for form
        VBox formBox = new VBox(10,
                new Label("Student Full Name:"), studentNameField,
                new Label("Schedule Date:"), scheduleDatePicker,
                new Label("Time Slot:"), timeSlotComboBox,
                new Label("Course:"), courseComboBox,
                new Label("Reason:"), reasonField,
                new Label("Comment:"), commentField
        );
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPrefWidth(400);

        // Buttons
        submitButton = new Button("Submit");
        submitButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        submitButton.setOnAction(e -> handleSubmit());

        backButton = new Button("Back to Home Page");
        backButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> switchToHomepage());

        HBox buttonBox = new HBox(20, submitButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        VBox layout = new VBox(20, titleBox, formBox, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: rgba(66, 223, 244, 0.40);");

        return new Scene(layout, 900, 600, Color.LIGHTBLUE);
    }

    private void loadTimeSlots() {
        ObservableList<String> timeSlots = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:office_hours.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT timeSlot FROM time_slots")) {
            while (rs.next()) {
                timeSlots.add(rs.getString("timeSlot"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        timeSlotComboBox.setItems(timeSlots);
    }

    private void loadCourses() {
        ObservableList<String> courses = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:office_hours.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT courseCode FROM courses")) {
            while (rs.next()) {
                courses.add(rs.getString("courseCode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        courseComboBox.setItems(courses);
    }

    private void handleSubmit() {
        List<String> errors = new ArrayList<>();

        if (studentNameField.getText().isEmpty()) {
            errors.add("Student Name is required.");
        }
        if (scheduleDatePicker.getValue() == null) {
            errors.add("Schedule Date is required.");
        }
        if (timeSlotComboBox.getSelectionModel().isEmpty()) {
            errors.add("Time Slot is required.");
        }
        if (courseComboBox.getSelectionModel().isEmpty()) {
            errors.add("Course is required.");
        }

        if (!errors.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, String.join("\n", errors));
            return;
        }

        // Save the appointment
        boolean success = DatabaseHelper.insertAppointment(
                studentNameField.getText().trim(),
                scheduleDatePicker.getValue().toString(),
                timeSlotComboBox.getValue(),
                courseComboBox.getValue(),
                reasonField.getText().trim(),
                commentField.getText().trim()
        );

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Appointment Saved Successfully!");
            clearForm();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to save appointment.");
        }
    }

    private void clearForm() {
        studentNameField.clear();
        scheduleDatePicker.setValue(LocalDate.now());
        if (!timeSlotComboBox.getItems().isEmpty()) timeSlotComboBox.getSelectionModel().selectFirst();
        if (!courseComboBox.getItems().isEmpty()) courseComboBox.getSelectionModel().selectFirst();
        reasonField.clear();
        commentField.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle((type == Alert.AlertType.ERROR) ? "Error" : "Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToHomepage() {
        Homepage homepage = new Homepage(stage);
        homepage.start(stage);
    }
}
