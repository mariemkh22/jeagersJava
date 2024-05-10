package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RatingController {

    @FXML
    private ImageView homeB;

    @FXML
    private Label ratingLabel;

    @FXML
    private Button star1Button;

    @FXML
    private Button star2Button;

    @FXML
    private Button star3Button;

    @FXML
    private Button star4Button;

    @FXML
    private Button star5Button;

    @FXML
    private Button backbtn;

    @FXML
    void handleRating(ActionEvent event) {
        if (event.getSource() == star1Button) {
            ratingLabel.setText("Mauvais");
        } else if (event.getSource() == star2Button) {
            ratingLabel.setText("Moyen");
        } else if (event.getSource() == star3Button) {
            ratingLabel.setText("Bon");
        } else if (event.getSource() == star4Button) {
            ratingLabel.setText("Très bon");
        } else if (event.getSource() == star5Button) {
            ratingLabel.setText("Excellent");
        }
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        ratingLabel.setText("");
    }
    @FXML
    void handleRate(ActionEvent event) {
        if (ratingLabel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un rating avant de continuer.");

            ButtonType continueButton = new ButtonType("Continuer");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(continueButton, cancelButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == continueButton) {
                    // Afficher une autre interface ou effectuer toute autre action
                    System.out.println("Continuer l'opération...");
                } else {
                    System.out.println("Opération annulée.");
                }
            });
        } else {
            String rating = ratingLabel.getText();
            // Enregistrer le rating
            System.out.println("Rating enregistré : " + rating);

            // Afficher une alerte de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Rating enregistré avec succès : " + rating);
            confirmationAlert.showAndWait();
        }
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

    @FXML
    void homeButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
