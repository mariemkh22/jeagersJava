package controllers;

import entities.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceNotification;


import java.io.IOException;
import java.sql.SQLException;

public class EditNotification {
    @FXML
    private Label deliveryB;
    @FXML
    private Label serviceB;
    @FXML
    private Label userB;
    @FXML
    private Label productB;
    @FXML
    private Label messageB;
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


// Mettez à jour la notification dans la base de données
        notification.setDate_envoie(date_envoie);
        notification.setSujet(sujet);
        notification.setContenue(contenue);

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
    public void notiflistAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            sujetTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
    @FXML
    private AfficherNotifications afficherNotificationsController;

    public void setAfficherNotificationsController(AfficherNotifications controller) {
        this.afficherNotificationsController = controller;
    }
    @FXML
    void modifierNotificationAction(ActionEvent event) {
        String date_envoie = dateTF.getText();
        String sujet = sujetTF.getText();
        String contenue = contenueTF.getText();
        boolean erreur = false;
        // Validez les données modifiées (même logique que dans votre code actuel)
        // ...
        if (erreur) {
            return;
        }

        // Mettez à jour la notification dans la base de données
        notification.setDate_envoie(date_envoie);
        notification.setSujet(sujet);
        notification.setContenue(contenue);


        try {
            serviceNotification.modifier(notification);

            // Mettre à jour la TableView dans AfficherNotifications
            afficherNotificationsController.updateNotification(notification);

            // Afficher un message de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Notification modifiée");
            successAlert.setHeaderText(null);
            successAlert.setContentText("La notification a été modifiée avec succès !");
            successAlert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }


    }

    @FXML
    void productButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherProduit.fxml"));
            productB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void messageButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            messageB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void serviceButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherService.fxml"));
            serviceB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void userButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            userB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deliveryButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherLivraison.fxml"));
            deliveryB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
