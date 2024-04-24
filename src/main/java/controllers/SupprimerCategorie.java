package controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.categorie_service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import services.ServiceCategorie;


public class SupprimerCategorie implements Initializable {

    ServiceCategorie sc = new ServiceCategorie();
    private categorie_service selectedCategorie;

    public void setSelectedCategorie(categorie_service c) {
        selectedCategorie = c;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation du contrôleur
    }

    @FXML
    private Button Retour;
    @FXML
    private Button Confirmer;


    @FXML
    private void RetourSupp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
        if (selectedCategorie != null) {
            sc.supprimer(selectedCategorie.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Category deleted successfully.");
            alert.showAndWait();
            // Close the current stage
            ((Stage) Confirmer.getScene().getWindow()).close();

        }

    }
}
