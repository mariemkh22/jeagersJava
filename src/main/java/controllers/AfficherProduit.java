package controllers;

import entities.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceProduit;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherProduit {
    ServiceProduit sp = new ServiceProduit();

    @FXML
    private TableColumn<Produit,String> DescriptionCol;

    @FXML
    private TableColumn<Produit, Float> EquivCol;

    @FXML
    private TableColumn<Produit, String> NomCol;

    @FXML
    private TableColumn<Produit, String> TypeCol;

    @FXML
    private TableView<Produit> tableviewP;

    ObservableList<Produit> observableList;




    @FXML
    void initialize() {

        try {
            List<Produit> produitList = sp.afficher();
            observableList = FXCollections.observableList(produitList);

            tableviewP.setItems(observableList);

            NomCol.setCellValueFactory(new PropertyValueFactory<>("NomProduit"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            EquivCol.setCellValueFactory(new PropertyValueFactory<>("equiv"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    void Delete(ActionEvent event) {
        // Get the selected product
        Produit p = tableviewP.getSelectionModel().getSelectedItem();

        if (p == null) {
            // If no product is selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No product selected!");
            alert.showAndWait();
        } else {
            // Confirm deletion with a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete the selected product?");
            alert.setContentText("This action cannot be undone.");

            // Get the user's choice
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Delete the product from the database
                    sp.supprimer(p.getId());

                    // Remove the product from the TableView
                    observableList.remove(p);

                    // Show a confirmation message
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Success");
                    confirmation.setHeaderText(null);
                    confirmation.setContentText("Product deleted successfully!");
                    confirmation.showAndWait();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }


    @FXML
    void modifierProduit(ActionEvent event) {
        Produit selectedProduit = tableviewP.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierProduit.fxml"));
                Parent root = loader.load();

                ModifierProduit modifierProduitController = loader.getController();
                modifierProduitController.initData(selectedProduit); // Pass the selected product to the modification controller

                // Get the current stage (window) from the event
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Create a new stage for the "Modifier" window
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(currentStage); // Set the owner to the current window
                stage.setScene(new Scene(root));

                // Close the current stage (window)
                currentStage.close();

                // Show the "Modifier" window
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show an error message if no product is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to update.");
            alert.showAndWait();
        }
    }



    @FXML
    public void NavigateA(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML "AjouterProduit.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre pour la fenêtre "Ajouter Produit"
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // Récupérer la scène actuelle à partir de n'importe quel nœud de la scène
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Fermer la fenêtre actuelle (la fenêtre de l'affichage)
            currentStage.close();

            // Afficher la nouvelle fenêtre d'ajout de produit
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void NavigateO(ActionEvent event) {
        try {
            // Charger la vue AfficherCommande.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommande.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle
            Scene scene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène par la vue AfficherCommande
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void deletAll(ActionEvent actionEvent) {

            // Show a confirmation dialog to confirm deletion of all products
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete all products?");
            alert.setContentText("This action cannot be undone.");

            // Get the user's choice
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Delete all products from the database
                    sp.deleteAll();

                    // Clear the TableView
                    observableList.clear();

                    // Show a confirmation message
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Success");
                    confirmation.setHeaderText(null);
                    confirmation.setContentText("All products deleted successfully!");
                    confirmation.showAndWait();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

    }
