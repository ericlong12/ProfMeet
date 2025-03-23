package s25.cs151.application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;

public class OfficeHoursTableView extends Application {

    private TableView<OfficeHours> table;
    private ObservableList<OfficeHours> officeHoursList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        table = new TableView<>();
        officeHoursList = FXCollections.observableArrayList();

        loadDataFromDatabase();

        table.setEditable(false);
        setupColumns();
        table.setItems(officeHoursList);

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Saved Semester Office Hours");
        primaryStage.show();
    }

    private void loadDataFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:office_hours.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT semester, year, days FROM semester_office_hours")) {

            while (rs.next()) {
                String semester = rs.getString("semester");
                String year = String.valueOf(rs.getInt("year"));
                String days = rs.getString("days");

                officeHoursList.add(new OfficeHours(semester, year, days, "", "", ""));
            }

            // Sort after loading
            List<String> semesterOrder = List.of("Spring", "Summer", "Fall", "Winter");

            FXCollections.sort(officeHoursList, new Comparator<OfficeHours>() {
                @Override
                public int compare(OfficeHours a, OfficeHours b) {
                    int aIndex = semesterOrder.indexOf(a.getSemester());
                    int bIndex = semesterOrder.indexOf(b.getSemester());

                    if (aIndex != bIndex) {
                        return Integer.compare(aIndex, bIndex); // sort by semester first
                    } else {
                        return Integer.compare(Integer.parseInt(b.getYear()), Integer.parseInt(a.getYear())); // then go by the year decending order
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupColumns() {
        TableColumn<OfficeHours, String> colSemester = new TableColumn<>("Semester");
        colSemester.setCellValueFactory(new PropertyValueFactory<>("semester"));

        TableColumn<OfficeHours, String> colYear = new TableColumn<>("Year");
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<OfficeHours, String> colDays = new TableColumn<>("Days");
        colDays.setCellValueFactory(new PropertyValueFactory<>("days"));

        table.getColumns().addAll(colSemester, colYear, colDays);
    }
}
