package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class Remboursement {

    @FXML
    private Button genererEtiquetteBtn;

    @FXML
    private TextArea raisonTextArea;

    @FXML
    private Button traiterRemboursementBtn;

    @FXML
    private Button backbtn;

    @FXML
    private void genererEtiquette() {
        // Vérifier si le champ de saisie de la raison est vide
        String raison = raisonTextArea.getText().trim();
        if (raison.isEmpty()) {
            showAlert("Veuillez saisir une raison pour le remboursement.");
            return; // Arrêter l'exécution si le champ de saisie est vide
        }

        // Générer l'étiquette de retour avec la raison spécifiée
        String numeroEtiquette = generateRandomNumber();
        String etiquette = "Étiquette de retour pour : " + raison + " - Numéro : " + numeroEtiquette;
        showAlert("Étiquette de retour générée : " + etiquette);
    }

    @FXML
    private void traiterRemboursement() {
        String raison = raisonTextArea.getText().trim();
        if (raison.isEmpty()) {
            showAlert("Veuillez saisir une raison pour le remboursement.");
            return; // Arrêter l'exécution si le champ de saisie est vide
        }
        int traitementTime = generateRandomProcessingTime();
        showAlert("Remboursement en cours de traitement... Cela prendra environ " + traitementTime + " jours.");
    }

    private String generateRandomNumber() {
        // Simule la génération d'un numéro aléatoire pour l'étiquette
        return String.valueOf((int) (Math.random() * 100000));
    }

    private int generateRandomProcessingTime() {
        // Simule le temps de traitement aléatoire entre 1 et 7 jours
        return (int) (Math.random() * 7) + 1;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void backbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLivraisonFront.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
