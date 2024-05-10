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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ServiceProduit;

import java.awt.*;
import javafx.scene.control.Label;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherProduit {

    @FXML
    private ImageView profileB;

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
    private TableColumn<Produit, String> PicCol;

    @FXML
    private TableView<Produit> tableviewP;

    ObservableList<Produit> observableList;

    @FXML
    void initialize() {
        try {
            List<Produit> produitList = sp.afficher();
            observableList = FXCollections.observableList(produitList);

            tableviewP.setItems(observableList);

            NomCol.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            EquivCol.setCellValueFactory(new PropertyValueFactory<>("equiv"));
            PicCol.setCellValueFactory(new PropertyValueFactory<>("imageFile")); // Bind image file path to TableColumn
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
    void navigateTo(ActionEvent event) {
        try {
            // Charger la vue AfficherCommande.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCommande.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 1550, 820));
            stage.show();
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
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

    public void userManagment(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            userB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void profileButton(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
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
