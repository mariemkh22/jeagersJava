package controllers;

import entities.Notification;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.ServiceNotification;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterNotification {
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
private final ServiceNotification serviceNotification=new ServiceNotification();
    String username = "mariemkhelifi3@gmail.com";
    String password = "nhec ijjy wsdp uhgx";

    @FXML
    void AjouterNotif(ActionEvent event) {
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
            serviceNotification.ajouter(new Notification(dateTF.getText(),sujetTF.getText(),contenueTF.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification Added");
            alert.setHeaderText(null);
            alert.setContentText("Notification successfully added!");
            alert.showAndWait();
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        Image image=new Image("noti1.png");
        image.widthProperty();

        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Hello i have a good news for you SwapCraze have a new product check it !");
        notifications.title("New Notification !!");
        notifications.hideAfter(Duration.seconds(6));
        notifications.darkStyle();
        notifications.show();

    }

    public void AfficherNotif(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            sujetTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void updateAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/editNotification.fxml"));
            sujetTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void deletenotifAction(ActionEvent actionEvent) {

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
