package s25.cs151.application.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import s25.cs151.application.model.DatabaseHelper;


public class EditAppointmentPage implements Page {
    private Stage stage;
    private AppointmentsTableView.Appointment originalAppointment;

    private TextField studentNameField;
    private DatePicker scheduleDatePicker;
    private ComboBox<String> timeSlotComboBox;
    private ComboBox<String> courseComboBox;
    private TextField reasonField;
    private TextField commentField;
    private Button saveButton;
    private Button backButton;

    public EditAppointmentPage(Stage stage, AppointmentsTableView.Appointment appointment) {
        this.stage = stage;
        this.originalAppointment = appointment;
    }

    @Override
    public Scene getScene() {
        Font istokFont = Font.font("Istok Web", 16);

        Label titleLabel = new Label("Edit Appointment");
        titleLabel.setFont(Font.font("Istok Web", 40));
        titleLabel.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20));

        studentNameField = new TextField(originalAppointment.getStudentName());

        scheduleDatePicker = new DatePicker(LocalDate.parse(originalAppointment.getScheduleDate()));

        timeSlotComboBox = new ComboBox<>();
        courseComboBox = new ComboBox<>();
        loadTimeSlots();
        loadCourses();
        timeSlotComboBox.setValue(originalAppointment.getTimeSlot());
        courseComboBox.setValue(originalAppointment.getCourse());

        reasonField = new TextField(originalAppointment.getReason());
        commentField = new TextField(originalAppointment.getComment());

        VBox formBox = new VBox(10,
                new Label("Student Name:"), studentNameField,
                new Label("Schedule Date:"), scheduleDatePicker,
                new Label("Time Slot:"), timeSlotComboBox,
                new Label("Course:"), courseComboBox,
                new Label("Reason:"), reasonField,
                new Label("Comment:"), commentField
        );
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPrefWidth(400);

        saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        saveButton.setOnAction(e -> handleSave());

        backButton = new Button("Cancel");
        backButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBack());

        HBox buttonBox = new HBox(20, saveButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        VBox layout = new VBox(20, titleBox, formBox, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: rgba(66, 223, 244, 0.40);");

        return new Scene(layout, 900, 600, Color.LIGHTBLUE);
    }

    private void handleSave() {
        if (studentNameField.getText().isEmpty() || scheduleDatePicker.getValue() == null ||
                timeSlotComboBox.getValue() == null || courseComboBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "All fields except Reason and Comment are required.");
            return;
        }

        boolean updated = DatabaseHelper.updateAppointment(
            originalAppointment.getStudentName(),
            originalAppointment.getScheduleDate(),
            originalAppointment.getTimeSlot(),
            originalAppointment.getCourse(),
            studentNameField.getText().trim(),
            scheduleDatePicker.getValue().toString(),
            timeSlotComboBox.getValue(),
            courseComboBox.getValue(),
            reasonField.getText().trim(),
            commentField.getText().trim()
        );
        

        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Appointment updated successfully.");
            goBack();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update appointment.");
        }
    }

    private void goBack() {
        SearchAndEditOfficeHoursPage searchPage = new SearchAndEditOfficeHoursPage(stage);
        Scene scene = searchPage.getScene();
        stage.setScene(scene);
    }
    
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle((type == Alert.AlertType.ERROR) ? "Error" : "Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
}
