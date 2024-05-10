package controllers;

import entities.LocalisationGeographique;
import javafx.scene.input.MouseEvent;
import services.Servicelocation;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherLocationController {

    @FXML
    private Label deliveryB;

    @FXML
    private Label productB;

    @FXML
    private Label userB;

    @FXML
    private Label serviceB;

    @FXML
    private Label messageB;

    @FXML
    private TableColumn<LocalisationGeographique, String> AddressCol;

    @FXML
    private TableColumn<LocalisationGeographique, Integer> PostalcodeCol;

    @FXML
    private TableColumn<LocalisationGeographique, String> RegionCol;
    @FXML
    private TableColumn<LocalisationGeographique, Integer> IDLocationCol;

    @FXML
    private TableView<LocalisationGeographique> Tableview;

    @FXML
    void BtnAddLocation(ActionEvent event) {
        try {
            // Charger le fichier FXML de la fenêtre ajoutLocation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterLocation.fxml"));
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
    void CLEARLocationBtn(ActionEvent event) {
        // Créer une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure?");

        // Ajouter les boutons "Clear" et "Cancel" à la boîte de dialogue
        ButtonType clearButton = new ButtonType("Clear", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(clearButton, cancelButton);

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == clearButton) {
            // Si l'utilisateur a choisi "Clear", supprimer toutes les localisations de la base de données
            try {
                sl.supprimerToutesLocations();

                // Réinitialiser le texte des colonnes
                AddressCol.setText(null);
                PostalcodeCol.setText(null);
                RegionCol.setText(null);
                IDLocationCol.setText(null);

                // Supprimer toutes les données de l'ObservableList associée au TableView
                Tableview.getItems().clear();
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setContentText("Error while Deleting from Data Base : " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    void DELETELocationBtn(ActionEvent event) {
        // Récupérer la localisation sélectionnée dans le TableView
        LocalisationGeographique selectedLocation = Tableview.getSelectionModel().getSelectedItem();
        if (selectedLocation != null) {
            // Créer une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure?");

            // Ajouter les boutons "Delete" et "Cancel" à la boîte de dialogue
            ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(deleteButton, cancelButton);

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == deleteButton) {
                // Si l'utilisateur a choisi "Delete", supprimer la localisation de la base de données
                try {
                    sl.supprimerLocation(selectedLocation.getId());

                    // Mettre à jour l'affichage dans le TableView
                    Tableview.getItems().remove(selectedLocation);
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Errorr");
                    errorAlert.setContentText("Error while deleting : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        } else {
            // Aucune localisation sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose location.");
            alert.showAndWait();
        }
    }

    @FXML
    void UPDATELocationBtn(ActionEvent event) {
        // Récupérer la localisation sélectionnée dans le TableView
        LocalisationGeographique selectedLocation = Tableview.getSelectionModel().getSelectedItem();
        if (selectedLocation != null) {
            try {
                // Charger le fichier FXML de la fenêtre de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLocation.fxml"));
                Parent root = loader.load();

                // Obtenir le contrôleur de la fenêtre de modification
                ModifierLocationController modifierLocationController = loader.getController();

                // Passer la localisation sélectionnée au contrôleur de modification
                modifierLocationController.initData(selectedLocation);

                // Récupérer la scène actuelle et la remplacer par la scène de modification
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception s'il y a une erreur de chargement du fichier FXML
            }
        } else {
            // Aucune localisation sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose location to update.");
            alert.showAndWait();
        }
    }
    private final Servicelocation sl = new Servicelocation();
    @FXML
    void initialize(){
        try{
            List<LocalisationGeographique> location = sl.afficherLocation();
            ObservableList<LocalisationGeographique> observableList = FXCollections.observableList(location);
            Tableview.setItems(observableList);
            IDLocationCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            RegionCol.setCellValueFactory(new PropertyValueFactory<>("Region"));
            PostalcodeCol.setCellValueFactory(new PropertyValueFactory<>("codepostal"));
            AddressCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        } catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
