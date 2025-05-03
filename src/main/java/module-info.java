module s25.cs151.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics;

    requires com.dlsc.formsfx;

    requires java.sql;

    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
    exports s25.cs151.application.model;
    opens s25.cs151.application.model to javafx.fxml;
    exports s25.cs151.application.view;
    opens s25.cs151.application.view to javafx.fxml;
    exports s25.cs151.application.controller;
    opens s25.cs151.application.controller to javafx.fxml;
}
