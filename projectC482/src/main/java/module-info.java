module com.example.projectc482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectc482 to javafx.fxml;
    exports com.example.projectc482.controller;
    opens com.example.projectc482.controller to javafx.fxml;
    exports com.example.projectc482.Model;
    opens com.example.projectc482.Model to javafx.fxml;
}