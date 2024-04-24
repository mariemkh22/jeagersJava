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

public class ModifierService {

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
    private ImageView imageView;

    @FXML
    private Button insert_image1;

    @FXML
    private Label erreurDateLabel;

    @FXML
    private Label erreurDescriptionLabel;

    @FXML
    private Label erreurLocationLabel ;

    @FXML
    private Label erreurNomLabel;

    @FXML
    private Label erreurStateLabel;
    @FXML
    private ComboBox<categorie_service> categorieBox;

    private service serviceToUpdate;
    private Image currentImage;

    private ServiceService ss = new ServiceService();

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

    // Méthode pour initialiser les données de service à mettre à jour
    public void initData(service service) {
        serviceToUpdate = service;

        // Utiliser les données de service pour pré-remplir les champs de texte
        namestf.setText(service.getName_s());
        descriptionstf.setText(service.getDescription_s());
        locationtf.setText(service.getLocalisation());
        statetf.setText(service.getState());
        datetf.setText(service.getDispo_date());


    }


        @FXML
        void modifierService(ActionEvent event) {
            if (serviceToUpdate != null) {
                // Mettre à jour les propriétés du service avec les valeurs des champs de texte
                serviceToUpdate.setName_s(namestf.getText());
                serviceToUpdate.setDescription_s(descriptionstf.getText());
                serviceToUpdate.setLocalisation(locationtf.getText());
                serviceToUpdate.setState(statetf.getText());
                serviceToUpdate.setDispo_date(datetf.getText());

                Image updatedImage = imageView.getImage();
                serviceToUpdate.setImageFile(String.valueOf((updatedImage)));

                // Vérification des champs de saisie
                String name_s = namestf.getText();
                String description_s = descriptionstf.getText();
                String localisation = locationtf.getText();
                String state = statetf.getText();
                String dispo_date = datetf.getText();




                ///controle de saisie///
                boolean erreur = false;

                // Vérification du champ de nom
                if (name_s.isEmpty()) {
                    erreurNomLabel.setText("The service name is missing!");
                    erreur = true;
                } else {
                    erreurNomLabel.setText(""); // Effacer le message d'erreur
                }

                // Vérification du champ de description
                if (description_s.isEmpty()) {
                    erreurDescriptionLabel.setText("Please enter the description.");
                    erreur = true;
                } else {
                    erreurDescriptionLabel.setText(""); // Effacer le message d'erreur
                }

                // Vérification du champ de localisation
                if (localisation.isEmpty()) {
                    erreurLocationLabel.setText("The location field is empty.");
                    erreur = true;
                } else {
                    erreurLocationLabel.setText(""); // Effacer le message d'erreur
                }

                // Vérification du champ d'état
                if (state.isEmpty()) {
                    erreurStateLabel.setText("The state field is empty.");
                    erreur = true;
                } else {
                    erreurStateLabel.setText(""); // Effacer le message d'erreur
                }

                // Vérification du champ de date de disponibilité
                if (dispo_date.isEmpty()) {
                    erreurDateLabel.setText("Please enter the availability date.");
                    erreur = true;
                } else {
                    erreurDateLabel.setText(""); // Effacer le message d'erreur
                }

                // Si des erreurs sont détectées, arrêtez l'exécution
                if (erreur) {
                    return;
                }



                try {
                    // Appeler la méthode de service pour mettre à jour le service dans la base de données
                    ss.modifier(serviceToUpdate);
                    // Afficher une boîte de dialogue de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Service updated successfully.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    // En cas d'erreur, afficher une boîte de dialogue d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An Error occurred while updating the service : " + e.getMessage());
                    alert.showAndWait();
                }
            } else {
                // Si aucun service n'est sélectionné, afficher une boîte de dialogue d'avertissement
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No selection !");
                alert.setHeaderText(null);
                alert.setContentText("No services to update.");
                alert.showAndWait();
            }




        }

        @FXML
        void navigatetoAfficher(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherService.fxml"));
                namestf.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    @FXML
    void addImage1(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Charge l'image sélectionnée et l'affiche dans l'interface utilisateur
            Image imageFile = new Image(selectedFile.toURI().toString());
            imageView.setImage(imageFile);

            // Mettre à jour l'image du service à modifier
            serviceToUpdate.setImageFile(String.valueOf((imageFile)));
        }

    }

        }






