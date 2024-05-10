package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.categorie_service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.ServiceCategorie;

public class AjouterCategorie {

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


    @FXML
    void insererCategorie(ActionEvent event) {
        String name_c = namectf.getText();
        String description_c = descriptionctf.getText();

        boolean erreur = false; //var pour verifier s'il y a des erreurs de saisie//

        //verification des champs
        if (name_c.isEmpty()) {
            erreurNomLabel.setText("The category name is missing!");
            erreur = true;
        } else {
            erreurNomLabel.setText(""); // pas dee message d'erreur
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


        ServiceCategorie sc = new ServiceCategorie();
        try {
            categorie_service c = new categorie_service(name_c, description_c );

            sc.ajouter(c);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Category inserted successfully.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void afficherCategorie(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCategorie.fxml"));
            namectf.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
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
