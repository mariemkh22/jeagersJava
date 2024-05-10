package controllers;

import entities.LocalisationGeographique;
import javafx.scene.input.MouseEvent;
import services.Servicelocation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterLocationController {

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
    private TextField tfaddress;

    @FXML
    private TextField tfpostalcode;

    @FXML
    private TextField tfregion;

    @FXML
    private Label Adresstfcont;

    @FXML
    private Label Postalcodetfcont;

    @FXML
    private Label RgiontfCont;

    @FXML
    private Label IntPostalcodetfcont;

    @FXML
    private Button adddeliverybtn;

    private final Servicelocation sl = new Servicelocation();

    @FXML
    void AddlocationBtn(ActionEvent event) throws SQLException {
        String address = tfaddress.getText().trim();
        String postalCode = tfpostalcode.getText().trim();
        String region = tfregion.getText().trim();

        boolean isValid = true;

        if (address.isEmpty()) {
            Adresstfcont.setText("Chose Adresse");
            isValid = false;
        } else {
            Adresstfcont.setText("");
        }

        if (postalCode.isEmpty() || !postalCode.matches("\\d+")) {
            Postalcodetfcont.setText("Code postal invalide");
            isValid = false;
        } else {
            Postalcodetfcont.setText("");
        }


        if (region.isEmpty()) {
            RgiontfCont.setText("Chose Region");
            isValid = false;
        } else {
            RgiontfCont.setText("");
        }

        if (isValid) {
            try {
                int postalCodeInt = Integer.parseInt(postalCode);
                if (postalCodeInt < 0) {
                    throw new NumberFormatException("Le code postal doit être un entier naturel positif.");
                }
                sl.ajouterLocation(new LocalisationGeographique(region, postalCodeInt, address));
                // Afficher une alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("La localisation a été ajoutée avec succès.");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                Postalcodetfcont.setText("Le code postal doit être un entier naturel positif.");
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        else {
            // Afficher une alerte pour informer l'utilisateur qu'il y a des erreurs dans le formulaire
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez corriger les erreurs dans le formulaire.");
            alert.showAndWait();
        }
    }

    @FXML
    void ShowlocationlistBtn(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherLocation.fxml"));
            tfpostalcode.getScene().setRoot(root);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void adddeliverybtn(ActionEvent event) {

        try {
            // Charger le fichier FXML de la fenêtre ajoutLocation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLivraison.fxml"));
            Parent root = loader.load();

            // Récupérer la fenêtre actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Changer la scène de la fenêtre actuelle
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception s'il y a une erreur de chargement du fichier FXML
        }
    }

    @FXML
    void deliveryButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherLivraison.fxml"));
            deliveryB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void productButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherProduit.fxml"));
            productB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void userButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            userB.getScene().setRoot(root);
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
    void messageButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            messageB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
