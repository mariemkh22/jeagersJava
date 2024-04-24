package controllers;

import Entity.Livraison;
import Entity.LocalisationGeographique;
import Services.Servicelivraison;
import Services.Servicelocation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AfficherLivraisonController {

    @FXML
    private Button ADDDeliverybtn;

    @FXML
    private Button CLEARDeliverybtn;

    @FXML
    private Button DELETEDeliverybtn;

    @FXML
    private TableColumn<Livraison, String> Entreprisetf;

    @FXML
    private TableColumn<Livraison, Date> Firstdatetf;

    @FXML
    private TableColumn<Livraison, Integer> IDlivraisontf;

    @FXML
    private TableColumn<Livraison, Date> Lastdatetf;

    @FXML
    private TableColumn<Livraison, String> Regionlivtf;

    @FXML
    private Button UPDATEDeliverybtn;

    @FXML
    private TableView<Livraison> deliverylisttf;

    @FXML
    private TableColumn<Livraison, Integer> feetf;

    @FXML
    private TableColumn<Livraison, String> statustf;

    @FXML
    void ADDDeliverybtn(ActionEvent event) {
        try {
            // Charger le fichier FXML de la fenêtre ajoutLocation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterLivraison.fxml"));
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
    void CLEARDeliverybtn(ActionEvent event) {
        // Créer une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Voulez-vous vraiment supprimer toutes les livraisons ?");

        // Ajouter les boutons "Clear" et "Cancel" à la boîte de dialogue
        ButtonType clearButton = new ButtonType("Clear", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(clearButton, cancelButton);

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == clearButton) {
            // Si l'utilisateur a choisi "Clear", vider la TableView et éventuellement supprimer toutes les livraisons de la base de données
            deliverylisttf.getItems().clear();
            try {
                servicelivraison.supprimerToutesLivraisons();
                // Afficher une alerte de confirmation
                confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Toutes les livraisons ont été supprimées avec succès.");
                confirmationAlert.showAndWait();
            } catch (SQLException e) {
                // Gérer l'exception si une erreur SQL se produit
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setContentText("Erreur lors de la suppression des livraisons : " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    void DELETEDeliverybtn(ActionEvent event) {
        // Récupérer la livraison sélectionnée dans la TableView
        Livraison selectedLivraison = deliverylisttf.getSelectionModel().getSelectedItem();
        if (selectedLivraison != null) {
            // Créer une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer cette livraison ?");

            // Ajouter les boutons "Delete" et "Cancel" à la boîte de dialogue
            ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(deleteButton, cancelButton);

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == deleteButton) {
                // Si l'utilisateur a choisi "Delete", supprimer la livraison de la TableView et éventuellement de la base de données
                deliverylisttf.getItems().remove(selectedLivraison);
                try {
                    servicelivraison.supprimer(selectedLivraison.getId());
                    // Afficher une alerte de confirmation
                    confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("La livraison a été supprimée avec succès.");
                    confirmationAlert.showAndWait();
                } catch (SQLException e) {
                    // Gérer l'exception si une erreur SQL se produit
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setContentText("Erreur lors de la suppression de la livraison : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        } else {
            // Aucune livraison sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une livraison à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    void UPDATEDeliverybtn(ActionEvent event) {
        // Récupérer la livraison sélectionnée dans la TableView
        Livraison selectedLivraison = deliverylisttf.getSelectionModel().getSelectedItem();
        if (selectedLivraison != null) {
            try {
                // Charger le fichier FXML de la fenêtre de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLivraison.fxml"));
                Parent root = loader.load();

                // Obtenir le contrôleur de la fenêtre de modification
                ModifierLivraisonController modifierLivraisonController = loader.getController();

                // Passer la livraison sélectionnée au contrôleur de modification
                modifierLivraisonController.initData(selectedLivraison);

                // Récupérer la scène actuelle et la remplacer par la scène de modification
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception s'il y a une erreur de chargement du fichier FXML
            }
        } else {
            // Aucune livraison sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une livraison à modifier.");
            alert.showAndWait();
        }
    }
    private Servicelivraison servicelivraison;

    public AfficherLivraisonController() {
        servicelivraison = new Servicelivraison();
    }

    @FXML
    public void initialize() {
        populateDeliveryTable();
    }

    private void populateDeliveryTable() {
        try {
            List<Livraison> livraisons = servicelivraison.afficher(null);
            deliverylisttf.getItems().addAll(livraisons);

            IDlivraisontf.setCellValueFactory(new PropertyValueFactory<>("id"));
            Firstdatetf.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
            Lastdatetf.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            Entreprisetf.setCellValueFactory(new PropertyValueFactory<>("entreprise"));
            feetf.setCellValueFactory(new PropertyValueFactory<>("frais"));
            statustf.setCellValueFactory(new PropertyValueFactory<>("status"));
            Regionlivtf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalisationGeographique().getRegion()));

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Fetching Deliveries");
            alert.setContentText("An error occurred while fetching deliveries from the database.");
            alert.showAndWait();
        }
    }

}