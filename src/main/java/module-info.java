module com.example.foodshope_j {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    opens com.example.foodshope_j to javafx.fxml;
    exports com.example.foodshope_j;
}