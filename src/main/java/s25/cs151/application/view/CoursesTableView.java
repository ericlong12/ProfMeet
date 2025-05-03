/*
*   Written by Frances Belleza
*
* */


package s25.cs151.application.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import s25.cs151.application.model.Courses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static javafx.application.Application.launch;

public class CoursesTableView {
    private TableView<Courses> table;
    private ObservableList<Courses> courseInfo;

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage) {
        table = new TableView<>();
        courseInfo = FXCollections.observableArrayList();

        loadCoursesFromDatabase();

        table.setEditable(false);
        setupColumns();
        table.setItems(courseInfo);

        VBox vbox = new VBox();
        vbox.getChildren().add(table);

        Scene scene = new Scene(vbox, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stored Courses");
        primaryStage.show();
    }

    private void loadCoursesFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:office_hours.db");
             Statement state = conn.createStatement();
             ResultSet results = state.executeQuery(
                     "SELECT courseCode, courseName, courseSection FROM courses ORDER BY LOWER(courseCode) DESC")) {

            while (results.next()) {
                Courses course = new Courses(
                        results.getString("courseCode"),
                        results.getString("courseName"),
                        results.getString("courseSection")
                );
                courseInfo.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupColumns() {
        TableColumn<Courses, String> columnCourseCode = new TableColumn<>("Course Code");
        columnCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        columnCourseCode.setSortable(true);
        columnCourseCode.setSortType(TableColumn.SortType.DESCENDING);

        TableColumn<Courses, String> columnCourseName = new TableColumn<>("Course Name");
        columnCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Courses, String> columnCourseSection = new TableColumn<>("Course Section");
        columnCourseSection.setCellValueFactory(new PropertyValueFactory<>("courseSection"));

        table.getColumns().addAll(columnCourseCode, columnCourseName, columnCourseSection);

        table.getSortOrder().add(columnCourseCode);
    }

}