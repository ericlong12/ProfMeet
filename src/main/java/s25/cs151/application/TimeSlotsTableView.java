package s25.cs151.application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSlotsTableView extends Application {

    private TableView<OfficeHours> table;
    private ObservableList<OfficeHours> timeSlotList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        table = new TableView<>();
        timeSlotList = FXCollections.observableArrayList();

        // grab info from db
        loadDataFromDatabase();

        table.setEditable(false);
        setupColumns();           
        table.setItems(timeSlotList); 

        // layout setup
        VBox vbox = new VBox();
        vbox.setPrefHeight(Region.USE_COMPUTED_SIZE); 
        vbox.setFillWidth(true);                     
        VBox.setVgrow(table, Priority.ALWAYS);       
        vbox.getChildren().add(table);

        // display in scene
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Saved Time Slots");
        primaryStage.show();
    }

    private void loadDataFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:office_hours.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT oh.semester, oh.year, oh.days, ts.timeSlot " +
                             "FROM time_slots ts " +
                             "JOIN office_hours oh ON ts.office_hour_id = oh.id"
             )) {

            while (rs.next()) {
                String semester = rs.getString("semester");
                String year = String.valueOf(rs.getInt("year"));
                String days = rs.getString("days");
                String timeSlot = rs.getString("timeSlot");  // Note: column name must be 'timeSlot'

                // Create an OfficeHours object (or a custom object for time slots if preferred)
                timeSlotList.add(new OfficeHours(semester, year, days, timeSlot));
            }

            // Sorting code (if needed)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            timeSlotList.sort((a, b) -> {
                try {
                    String fromA = a.getTimeSlots().split("-")[0].trim();
                    String fromB = b.getTimeSlots().split("-")[0].trim();
                    LocalTime timeA = LocalTime.parse(fromA, formatter);
                    LocalTime timeB = LocalTime.parse(fromB, formatter);
                    return timeA.compareTo(timeB); // ascending order
                } catch (Exception e) {
                    return 0;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupColumns() {
        // make columns 

        TableColumn<OfficeHours, String> colSemester = new TableColumn<>("Semester");
        colSemester.setCellValueFactory(new PropertyValueFactory<>("semester"));

        TableColumn<OfficeHours, String> colYear = new TableColumn<>("Year");
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<OfficeHours, String> colDays = new TableColumn<>("Days");
        colDays.setCellValueFactory(new PropertyValueFactory<>("days"));

        TableColumn<OfficeHours, String> colTimeSlot = new TableColumn<>("Time Slot");
        colTimeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlots"));

        // add the columns to the table
        table.getColumns().addAll(colSemester, colYear, colDays, colTimeSlot);
    }
}
