package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.categorie_service;
import entities.service;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import services.ServiceCategorie;
import services.ServiceService;

import javax.swing.*;

public class AjouterService {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField datetf;

    @FXML
    private TextField descriptionstf;

    @FXML
    private TextField locationtf;

    @FXML
    private TextField namestf;

    @FXML
    private TextField statetf;

    @FXML
    private Label erreurDateLabel;

    @FXML
    private Label erreurDescriptionLabel;

    @FXML
    private Label erreurLocationLabel;

    @FXML
    private Label erreurNomLabel;

    @FXML
    private Label erreurStateLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Button insert_image;

    @FXML
    private ComboBox<categorie_service> categorieBox;
    private Image selectedImage;


    @FXML
    void afficherService(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherService.fxml"));
            namestf.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void initialize() {
        try {
            ServiceCategorie serviceCategorie = new ServiceCategorie();
            List<categorie_service> cats = serviceCategorie.afficher();
            categorieBox.setItems(FXCollections.observableList(cats));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

    @FXML
    void ajouterService(ActionEvent event) {
        String name_s = namestf.getText();
        String description_s = descriptionstf.getText();
        String localisation = locationtf.getText();
        String state = statetf.getText();
        String dispo_date = datetf.getText();
        int cat_id= categorieBox.getValue().getId();
        Image imageFile= imageView.getImage();

       /* String imagePath = (imageFile != null) ? imageFile.getUrl() : "";*/


        boolean erreur = false;

        if (name_s.isEmpty()) {
            erreurNomLabel.setText("The service name is missing!");
            erreur = true;
        } else {
            erreurNomLabel.setText(""); // Effacer le message d'erreur
        }

        if (description_s.isEmpty()) {
            erreurDescriptionLabel.setText("Please enter the description.");
            erreur = true;
        } else {
            erreurDescriptionLabel.setText(""); // Effacer le message d'erreur
        }

        if (localisation.isEmpty()) {
            erreurLocationLabel.setText("The location field is empty.");
            erreur = true;
        } else {
            erreurLocationLabel.setText(""); // Effacer le message d'erreur
        }

        if (state.isEmpty()) {
            erreurStateLabel.setText("The state field is empty.");
            erreur = true;
        } else {
            erreurStateLabel.setText(""); // Effacer le message d'erreur
        }

        if (dispo_date.isEmpty()) {
            erreurDateLabel.setText("Please enter the availability date.");
            erreur = true;
        } else {
            erreurDateLabel.setText(""); // Effacer le message d'erreur
        }

        if (erreur) {
            return; // Arrêter l'exécution si des erreurs sont trouvées
        }

        ServiceService ss = new ServiceService();

        // Récupérer le chemin de l'image sélectionnée
        String imagePath = selectedImage.getUrl();



        // Créer l'objet service avec le chemin de l'image
        try {
            service s = new service(name_s, description_s, localisation, state, dispo_date, cat_id, imageFile);
            s.setCat_id(categorieBox.getValue().getId());
            ss.ajouter(s);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Service inserted successfully.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Charge l'image sélectionnée et l'affiche dans l'interface utilisateur
            selectedImage = new Image(selectedFile.toURI().toString());
            imageView.setImage(selectedImage);
        }
    }






}



