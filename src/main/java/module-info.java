module s25.cs151.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics; 

    requires com.dlsc.formsfx;

    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
}
