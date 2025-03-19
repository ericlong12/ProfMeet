package s25.cs151.application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OfficeHoursTableView extends Application {

    private TableView<OfficeHours> table;
    private ObservableList<OfficeHours> officeHoursList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        table = new TableView<>();
        officeHoursList = FXCollections.observableArrayList(
            new OfficeHours("Spring", "2025", "Monday", "10:00 AM - 11:00 AM", "CS151", "Software Design"),
            new OfficeHours("Fall", "2024", "Wednesday", "2:00 PM - 3:30 PM", "CS146", "Data Structures")
        );

        table.setEditable(true);
        setupColumns();
        table.setItems(officeHoursList);

        Button addButton = new Button("Add Row");
        addButton.setOnAction(_ -> officeHoursList.add(new OfficeHours("", "", "", "", "", "")));

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(_ -> {
            OfficeHours selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                officeHoursList.remove(selected);
            }
        });

        VBox vbox = new VBox(table, addButton, deleteButton);
        Scene scene = new Scene(vbox, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Office Hours Editor");
        primaryStage.show();
    }

    private void setupColumns() {
        String[] columnNames = {"Semester", "Year", "Days", "Time Slots", "Course Code", "Course Name"};
        String[] propertyNames = {"semester", "year", "days", "timeSlots", "courseCode", "courseName"};

        for (int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
            TableColumn<OfficeHours, String> column = new TableColumn<>(columnNames[columnIndex]);
            column.setCellValueFactory(new PropertyValueFactory<>(propertyNames[columnIndex]));
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            final int colIndex = columnIndex;
            column.setOnEditCommit(event -> {
                OfficeHours row = event.getRowValue();
                switch (propertyNames[colIndex]) {
                    case "semester" -> row.setSemester(event.getNewValue());
                    case "year" -> row.setYear(event.getNewValue());
                    case "days" -> row.setDays(event.getNewValue());
                    case "timeSlots" -> row.setTimeSlots(event.getNewValue());
                    case "courseCode" -> row.setCourseCode(event.getNewValue());
                    case "courseName" -> row.setCourseName(event.getNewValue());
                }
            });
            table.getColumns().add(column);
        }
    }
}
