package controllers;

import entities.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.ServiceNotification;

import java.io.IOException;
import java.sql.SQLException;

public class EditNotification {
    @FXML
    private TextField contenueTF;

    @FXML
    private TextField dateTF;
    @FXML
    private Label erreurContenueLabel;

    @FXML
    private Label erreurDateLabel;

    @FXML
    private Label erreurSujetLabel;

    @FXML
    private TextField sujetTF;
    private final ServiceNotification serviceNotification = new ServiceNotification();
    private Notification notification;

    public void initData(Notification notification) {
        this.notification = notification;
        if (notification != null) {
            sujetTF.setText(notification.getSujet());
            contenueTF.setText(notification.getContenue());
            dateTF.setText(notification.getDate_envoie());
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
        String date_envoie = dateTF.getText();
        String sujet = sujetTF.getText();
        String contenue = contenueTF.getText();
        boolean erreur = false;
        if (date_envoie.isEmpty()) {
            erreurDateLabel.setText("Please enter the date !.");
            erreur = true;
        } else {
            erreurDateLabel.setText(""); // Effacer le message d'erreur
        }
        if (sujet.isEmpty()) {
            erreurSujetLabel.setText("The Title name is missing !.");
            erreur = true;
        } else {
            erreurSujetLabel.setText(""); // Effacer le message d'erreur
        }
        if (contenue.isEmpty()) {
            erreurContenueLabel.setText("The content is missing !.");
            erreur = true;
        } else {
            erreurContenueLabel.setText(""); // Effacer le message d'erreur
        }
        if (erreur) {
            return;
        }




            try {
                serviceNotification.modifier(notification);
            } catch (SQLException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }


        }
        @FXML
        public void notiflistAction (ActionEvent actionEvent){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
                sujetTF.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }

        }


    }

