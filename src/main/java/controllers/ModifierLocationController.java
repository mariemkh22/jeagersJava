package controllers;

import entities.LocalisationGeographique;
import javafx.scene.input.MouseEvent;
import services.Servicelocation;
import entities.LocalisationGeographique;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierLocationController {

    @FXML
    private Label deliveryB;

    @FXML
    private Label productB;

    @FXML
    private Label userB;

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

    private final Servicelocation sl = new Servicelocation();

    private LocalisationGeographique selectedLocation; // Variable pour stocker la localisation sélectionnée

    // Méthode pour initialiser les données de localisation sélectionnées dans la fenêtre de modification
    public void initData(LocalisationGeographique selectedLocation) {
        this.selectedLocation = selectedLocation;
        // Remplir les champs avec les données de la localisation sélectionnée
        tfaddress.setText(selectedLocation.getAdresse());
        tfpostalcode.setText(String.valueOf(selectedLocation.getCodepostal()));
        tfregion.setText(selectedLocation.getRegion());
    }

    @FXML
    void SavelocationBtn(ActionEvent event) {
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

        if (!isValid) {
            // Afficher une alerte pour informer l'utilisateur qu'un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        try {
            // Mettre à jour les informations de la localisation sélectionnée avec les nouvelles valeurs des champs
            selectedLocation.setRegion(tfregion.getText());
            selectedLocation.setCodepostal(Integer.parseInt(tfpostalcode.getText()));
            selectedLocation.setAdresse(tfaddress.getText());

            // Appeler la méthode de mise à jour dans le service de localisation pour mettre à jour la localisation dans la base de données
            sl.modifierLocation(selectedLocation);

            // Afficher une alerte de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("La localisation a été mise à jour avec succès.");
            alert.showAndWait();

        } catch (SQLException e) {
            // Gérer les exceptions liées à la mise à jour de la localisation
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la mise à jour de la localisation : " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void ShowlocationlistBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLocation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Obtenir la scène de l'événement actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Remplacer la scène actuelle par la nouvelle scène
            stage.setScene(scene);
            stage.show();
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

}
