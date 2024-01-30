module com.example.page {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.coursework to javafx.fxml;
    exports com.example.coursework;
}