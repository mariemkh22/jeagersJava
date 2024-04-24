package controllers;

import entities.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceNotification;

import java.sql.SQLException;

public class EditNotification {
    @FXML
    private TextField contenueTF;

    @FXML
    private TextField dateTF;

    @FXML
    private TextField sujetTF;
    private final ServiceNotification serviceNotification=new ServiceNotification();
    @FXML
    void updateAction(ActionEvent event) {
        try {
            serviceNotification.modifier(new Notification(dateTF.getText(),sujetTF.getText(),contenueTF.getText()));
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }
}
