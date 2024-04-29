package controllers;

import entities.categorie_service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.ServiceCategorie;

public class ModifierCategorie {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField descriptionctf;

        @FXML
        private TextField namectf;

        @FXML
        private Label erreurNomLabel;
        @FXML
        private Label erreurDescriptionLabel;

        private categorie_service categoryToUpdate;
        private ServiceCategorie sc = new ServiceCategorie();

        //pour initialiser les données du service à mettre à jour:
        public void initData(categorie_service categorie) {
            categoryToUpdate = categorie;

            //utiliser les données de la categorie
            namectf.setText(categorie.getName_c());
            descriptionctf.setText(categorie.getDescription_c());

        }

        @FXML
        void modifierCategorie(ActionEvent event) {
            if (categoryToUpdate != null) {

                categoryToUpdate.setName_c(namectf.getText());
                categoryToUpdate.setDescription_c(descriptionctf.getText());

                String name_c = namectf.getText();
                String description_c = descriptionctf.getText();

                boolean erreur = false;

                if (name_c.isEmpty()) {
                    erreurNomLabel.setText("The category name is missing!");
                    erreur = true;
                } else {
                    erreurNomLabel.setText("");
                }

                if (description_c.isEmpty()) {
                    erreurDescriptionLabel.setText("Please enter the description.");
                    erreur = true;
                } else {
                    erreurDescriptionLabel.setText("");
                }

                if (erreur) {
                    return;
                }


                try {
                    sc.modifier(categoryToUpdate);
                    //boîte de dialogue
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success.");
                    alert.setHeaderText(null);
                    alert.setContentText("Category updated successfully.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An Error occurred while updating the category : " + e.getMessage());
                    alert.showAndWait();
                }
            } else {
                ///aucune categorie n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No selection !");
                alert.setHeaderText(null);
                alert.setContentText("No categories to update.");
                alert.showAndWait();
            }


        }

    @FXML
    void navigatetoAfficher(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCategorie.fxml"));
            namectf.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}


