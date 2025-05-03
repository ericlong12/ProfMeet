package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class SearchAndEditOfficeHoursPage implements Page {
    private Stage stage;
    private TextField searchBox;
    private TableView<AppointmentsTableView.Appointment> table;
    private ObservableList<AppointmentsTableView.Appointment> searchResults;

    public SearchAndEditOfficeHoursPage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene(Stage stage) {
        Font istokFont = Font.font("Istok Web", 16);

        Label title = new Label("Edit Office Hours Schedule");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        searchBox = new TextField();
        searchBox.setPromptText("Search by Student Name (leave blank to view all)");

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        searchButton.setOnAction(e -> handleSearch());

        Button editButton = new Button("Edit Selected");
        editButton.setStyle("-fx-padding: 10; -fx-background-color: orange; -fx-text-fill: white;");
        editButton.setOnAction(e -> handleEdit());

        // delete button 
        Button deleteButton = new Button("Delete Selected");
        deleteButton.setStyle("-fx-padding: 10; -fx-background-color: red; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> handleDelete());

        Button homepageButton = new Button("Back to Homepage");
        homepageButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        homepageButton.setOnAction(this::switchToHomepage);

        HBox controls = new HBox(15, searchBox, searchButton, editButton, deleteButton, homepageButton);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        table = new TableView<>();
        table.setPrefHeight(300);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        searchResults = FXCollections.observableArrayList();
        setupTable();

        VBox layout = new VBox(20, titleBox, controls, table);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20 20 250 20; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(1000);

        return new Scene(layout, 1020, 640, Color.LIGHTBLUE);
    }

    private void setupTable() {
        TableColumn<AppointmentsTableView.Appointment, String> colStudentName = new TableColumn<>("Student Name");
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        TableColumn<AppointmentsTableView.Appointment, String> colScheduleDate = new TableColumn<>("Schedule Date");
        colScheduleDate.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));

        TableColumn<AppointmentsTableView.Appointment, String> colTimeSlot = new TableColumn<>("Time Slot");
        colTimeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));

        TableColumn<AppointmentsTableView.Appointment, String> colCourse = new TableColumn<>("Course");
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));

        TableColumn<AppointmentsTableView.Appointment, String> colReason = new TableColumn<>("Reason");
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));

        TableColumn<AppointmentsTableView.Appointment, String> colComment = new TableColumn<>("Comment");
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        table.getColumns().addAll(colStudentName, colScheduleDate, colTimeSlot, colCourse, colReason, colComment);
        table.setItems(searchResults);
    }

    private void handleSearch() {
        String query = searchBox.getText().trim();
        List<AppointmentsTableView.Appointment> results = query.isEmpty()
                ? DatabaseHelper.searchAppointmentsByStudentName("")
                : DatabaseHelper.searchAppointmentsByStudentName(query);
        searchResults.setAll(results);
    }

    private void handleEdit() {
        AppointmentsTableView.Appointment selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Please select an appointment to edit.");
            return;
        }

        EditAppointmentPage editPage = new EditAppointmentPage(stage, selected);
        Scene editScene = editPage.getScene();
        stage.setScene(editScene);
    }

    private void handleDelete() {
        AppointmentsTableView.Appointment selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Please select an appointment to delete.");
            return;
        }

        boolean deleted = DatabaseHelper.deleteAppointment(
                selected.getStudentName(),
                selected.getScheduleDate(),
                selected.getTimeSlot(),
                selected.getCourse()
        );

        if (deleted) {
            showAlert(Alert.AlertType.INFORMATION, "Appointment deleted.");
            searchResults.remove(selected);
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to delete appointment.");
        }
    }

    private void switchToHomepage(ActionEvent e) {
        Homepage homepage = new Homepage(stage);
        homepage.start(stage);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle((type == Alert.AlertType.ERROR) ? "Error" : "Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
    Button editOfficeHoursScheduleButton = new Button("Edit Office Hours Schedule");
    editOfficeHoursScheduleButton.setOnAction(e -> switchToEditOfficeHoursSchedulePage());
    */
}
