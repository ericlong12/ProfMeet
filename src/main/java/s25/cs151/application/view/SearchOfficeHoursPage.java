package s25.cs151.application.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import s25.cs151.application.model.DatabaseHelper;
import s25.cs151.application.controller.MainController;

import java.util.List;

public class SearchOfficeHoursPage {
    private final MainController controller;
    private Stage stage;
    private TextField searchBox;
    private Label title;
    private Button searchButton, homepageButton, deleteButton;
    private TableView<AppointmentsTableView.Appointment> table;
    private ObservableList<AppointmentsTableView.Appointment> searchResults;

    public SearchOfficeHoursPage(Stage stage) {
        this.stage = stage;
        this.controller = new MainController(stage);
    }

    //two buttons needed "Search" & "Back to Homepage"
    public Scene getScene(Stage stage) {
        Font istokFont = Font.font("Istok Web", 16);

        title = new Label("Search Office Hours");
        title.setFont(Font.font("Istok Web", 40));
        title.setStyle("-fx-font-weight: bold;");

        HBox titleBox = new HBox(20, title);
        titleBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        searchBox = new TextField();
        searchBox.setPromptText("Search Office Hours Schedules");

        VBox searchVBox = new VBox(10, searchBox);
        searchVBox.setMinSize(500, 100); // Fixed size, so that it doesn't follow the page expanding
        searchVBox.setMaxSize(500, 100);
        searchVBox.setAlignment(Pos.CENTER_LEFT);

        searchButton = new Button("Search");
        searchButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        searchButton.setAlignment(Pos.CENTER);
        searchButton.setOnAction(e -> handleSearch());

        deleteButton = new Button("Delete Selected Appointment");
        deleteButton.setStyle("-fx-padding: 10; -fx-background-color: red; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> handleDelete());

        homepageButton = new Button("Back to Homepage");
        homepageButton.setStyle("-fx-padding: 10; -fx-background-color: black; -fx-text-fill: white;");
        homepageButton.setOnAction(event -> {
            controller.switchToHomepage();
        });

        table = new TableView<>();
        table.setPrefHeight(300);
        table.setPrefWidth(980);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Optional: evenly distribute columns
        searchResults = FXCollections.observableArrayList();
        setupTable();

        HBox forButtons = new HBox(20, searchButton, deleteButton, homepageButton);
        forButtons.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-padding: 10;");

        VBox layout = new VBox(10, titleBox, searchVBox, forButtons, scrollPane);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20 20 250 20; -fx-background-color: rgba(66, 223, 244, 0.40);");
        layout.setMaxWidth(1000);
        layout.setPrefWidth(1000);

        return new Scene(layout, 1020, 640, Color.LIGHTBLUE);
    }

    private void setupTable() {
        TableColumn<AppointmentsTableView.Appointment, String> colStudentName = new TableColumn<>("Student Name");
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStudentName.setPrefWidth(150);

        TableColumn<AppointmentsTableView.Appointment, String> colScheduleDate = new TableColumn<>("Schedule Date");
        colScheduleDate.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        colScheduleDate.setPrefWidth(120);

        TableColumn<AppointmentsTableView.Appointment, String> colTimeSlot = new TableColumn<>("Time Slot");
        colTimeSlot.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        colTimeSlot.setPrefWidth(140);

        TableColumn<AppointmentsTableView.Appointment, String> colCourse = new TableColumn<>("Course");
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colCourse.setPrefWidth(100);

        TableColumn<AppointmentsTableView.Appointment, String> colReason = new TableColumn<>("Reason");
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colReason.setPrefWidth(200);

        TableColumn<AppointmentsTableView.Appointment, String> colComment = new TableColumn<>("Comment");
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        colComment.setPrefWidth(200);

        table.getColumns().addAll(colStudentName, colScheduleDate, colTimeSlot, colCourse, colReason, colComment);
        table.setItems(searchResults);
    }

    private void handleSearch() {
        String query = searchBox.getText().trim();
        if (query.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter a search term.");
            return;
        }

        List<AppointmentsTableView.Appointment> results = DatabaseHelper.searchAppointmentsByStudentName(query);
        searchResults.setAll(results);
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

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle((type == Alert.AlertType.ERROR) ? "Error" : "Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
