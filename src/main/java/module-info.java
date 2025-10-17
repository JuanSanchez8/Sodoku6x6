module org.example.sodoku6x6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sodoku6x6 to javafx.fxml;
    exports org.example.sodoku6x6;
}