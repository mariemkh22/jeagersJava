module Swapcraze {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.j;
    requires jbcrypt;


    opens org.example;
    opens controllers to javafx.fxml;
    opens entities;
}