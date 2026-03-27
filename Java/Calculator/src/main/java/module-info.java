module com.example.calc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.calc to javafx.fxml;
    exports com.example.calc;
}