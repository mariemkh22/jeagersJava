package controllers;

import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import services.ServiceProduit;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierProduit {
    @FXML
    private Label userB;

    @FXML
    private Label productB;
    private Produit produit;

    @FXML
    private TextField PnomTF;

    @FXML
    private ComboBox<String> typeTF;

    @FXML
    private TextField DescTF;

    @FXML
    private TextField EpTF;

    private final ServiceProduit serviceProduit = new ServiceProduit();



    public void setProduit(Produit produit) {
        this.produit = produit;
        if (produit != null) {
            // Afficher les détails du produit dans les champs de texte
            PnomTF.setText(produit.getNomProduit());
            typeTF.setValue(produit.getType());
            DescTF.setText(produit.getDescription());
            EpTF.setText(String.valueOf(produit.getEquiv()));
        }
    }

    @FXML
    void initialize() {
        // Réinitialisation du style des champs de texte lors de l'initialisation du contrôleur
        PnomTF.setStyle("");
        DescTF.setStyle("");
        EpTF.setStyle("");

        if (typeTF != null) {
            typeTF.setItems(FXCollections.observableArrayList(
                    "Electronics",
                    "Fashion",
                    "Home",
                    "Garden",
                    "Health & Fitness",
                    "Automotive",
                    "Games & Toys",
                    "Books & Media"
            ));
        } else {
            System.out.println("typeTF is null");
        }
    }

    public void initData(Produit produit) {
        this.produit = produit;
        // Utilisez les données du produit pour initialiser les champs de texte
        PnomTF.setText(produit.getNomProduit());

        // Assurez-vous que le type du produit est inclus dans les options du ComboBox
        if (typeTF.getItems().contains(produit.getType())) {
            typeTF.setValue(produit.getType());
        } else {
            System.out.println("Type not found in ComboBox options");
        }

        DescTF.setText(produit.getDescription());
        EpTF.setText(String.valueOf(produit.getEquiv()));
    }

    @FXML
    void Update(ActionEvent event) {
        if (produit != null) {
            // Mettre à jour les données du produit avec les nouvelles valeurs
            produit.setNomProduit(PnomTF.getText());
            produit.setType(typeTF.getValue());
            produit.setDescription(DescTF.getText());
            produit.setEquiv(Float.parseFloat(EpTF.getText()));

            try {
                // Appeler le service pour effectuer la modification dans la base de données
                serviceProduit.modifier(produit);

                // Afficher un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Product updated successfully!");
                alert.showAndWait();

                // Reload the AfficherProduit scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduit.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Afficher Produit");
                stage.show();

                // Close the current stage
                ((Stage) PnomTF.getScene().getWindow()).close();
            } catch (SQLException | IOException e) {
                // En cas d'erreur, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error while updating product: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Si le produit est null, afficher un message d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("No product selected for modification!");
            alert.showAndWait();
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        try {
            // Load the AfficherProduit scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Afficher Produit");
            stage.show();

            // Close the current stage
            ((Stage) PnomTF.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void userButton(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
            userB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void productButton(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherProduit.fxml"));
            productB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
