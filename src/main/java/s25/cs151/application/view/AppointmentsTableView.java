package s25.cs151.application.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

public class AppointmentsTableView extends Application {

    private TableView<Appointment> table;
    private ObservableList<Appointment> appointmentList;

    @Override
    public void start(Stage primaryStage) {
        table = new TableView<>();
        appointmentList = FXCollections.observableArrayList();

        loadAppointmentsFromDatabase();

        table.setEditable(false);
        setupColumns();
        table.setItems(appointmentList);

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Saved Appointments");
        primaryStage.show();
    }

    private void loadAppointmentsFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:office_hours.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM appointments")) {

            while (rs.next()) {
                appointmentList.add(new Appointment(
                        rs.getString("studentName"),
                        rs.getString("scheduleDate"),
                        rs.getString("timeSlot"),
                        rs.getString("course"),
                        rs.getString("reason"),
                        rs.getString("comment")
                ));
            }

            // Schedule Date ascending, then Time Slot ascending
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            appointmentList.sort(Comparator
                    .comparing((Appointment a) -> LocalDate.parse(a.getScheduleDate(), dateFormatter))
                    .thenComparing(a -> {
                        try {
                            String startTimeStr = a.getTimeSlot().split("-")[0].trim();
                            return LocalTime.parse(startTimeStr, timeFormatter);
                        } catch (DateTimeParseException e) {
                            return LocalTime.MIDNIGHT;
                        }
                    }));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupColumns() {
        TableColumn<Appointment, String> colStudentName = new TableColumn<>("Student Name");
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        TableColumn<Appointment, String> colScheduleDate = new TableColumn<>("Schedule Date");
        colScheduleDate.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));

        TableColumn<Appointment, String> colTimeSlot = new TableColumn<>("Time Slot");
        colTimeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));

        TableColumn<Appointment, String> colCourse = new TableColumn<>("Course");
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));

        TableColumn<Appointment, String> colReason = new TableColumn<>("Reason");
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));

        TableColumn<Appointment, String> colComment = new TableColumn<>("Comment");
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        table.getColumns().addAll(colStudentName, colScheduleDate, colTimeSlot, colCourse, colReason, colComment);
    }

    // class to represent an Appointment
    public static class Appointment {
        private final String studentName;
        private final String scheduleDate;
        private final String timeSlot;
        private final String course;
        private final String reason;
        private final String comment;

        public Appointment(String studentName, String scheduleDate, String timeSlot, String course, String reason, String comment) {
            this.studentName = studentName;
            this.scheduleDate = scheduleDate;
            this.timeSlot = timeSlot;
            this.course = course;
            this.reason = reason;
            this.comment = comment;
        }

        public String getStudentName() { return studentName; }
        public String getScheduleDate() { return scheduleDate; }
        public String getTimeSlot() { return timeSlot; }
        public String getCourse() { return course; }
        public String getReason() { return reason; }
        public String getComment() { return comment; }
    }
}
