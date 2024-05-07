package controllers;

import entities.Produit;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterProduit {

    @FXML
    private TextField DescTF;

    @FXML
    private TextField EpTF;

    @FXML
    private TextField PnomTF;

    @FXML
    private ComboBox<String> typeTF;

    @FXML
    private ImageView imageView;

    private Image selectedImage;

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

    @FXML
    void AjouterP(ActionEvent event) {
        try {
            // Load the CAPTCHA dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/captcha.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Check if CAPTCHA was solved correctly
            CaptchaController captchaController = loader.getController();
            if (captchaController.isRotationCorrect()) {
                // Proceed with adding the product
                // Récupération des valeurs des champs
                String nomProduit = PnomTF.getText().trim();
                String typeProduit = typeTF.getValue();
                String descriptionProduit = DescTF.getText().trim();
                String equivPriceStr = EpTF.getText().trim();

                // Vérification de la saisie
                if (nomProduit.isEmpty() || typeProduit == null || descriptionProduit.isEmpty() || equivPriceStr.isEmpty()) {
                    // Modification du style des champs de texte invalides
                    if (nomProduit.isEmpty()) {
                        PnomTF.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        PnomTF.setStyle("");
                    }
                    if (typeProduit == null) {
                        typeTF.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        typeTF.setStyle("");
                    }
                    if (descriptionProduit.isEmpty()) {
                        DescTF.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        DescTF.setStyle("");
                    }
                    if (equivPriceStr.isEmpty()) {
                        EpTF.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        EpTF.setStyle("");
                    }

                    // Affichage d'une boîte de dialogue d'erreur si un champ est vide
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please fill in all fields!");
                    alert.show();
                    return; // Arrête l'exécution de la méthode si la saisie est invalide
                }

                // Vérification de la validité du prix équivalent
                float equivPrice;
                try {
                    equivPrice = Float.parseFloat(equivPriceStr);
                    if (equivPrice <= 0) {
                        throw new NumberFormatException(); // Prix invalide (inférieur ou égal à zéro)
                    } else {
                        EpTF.setStyle("");
                    }
                } catch (NumberFormatException e) {
                    // Modification du style du champ de texte invalide
                    EpTF.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");

                    // Affichage d'une boîte de dialogue d'erreur si le prix équivalent n'est pas un nombre valide
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please enter a valid Equivalent Price (Characters not allowed).");
                    alert.show();
                    return; // Arrête l'exécution de la méthode si le prix équivalent est invalide
                }

                // Ajout de l'image
                if (selectedImage == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please select an image.");
                    alert.show();
                    return;
                }

                // Création de l'objet Produit
                Produit p = new Produit(nomProduit, typeProduit, descriptionProduit, equivPrice, selectedImage.getUrl());

                // Utilisation du service pour ajouter le produit à la base de données
                ServiceProduit sp = new ServiceProduit();
                try {
                    sp.ajouter(p);
                    // Affichage d'une boîte de dialogue de succès en cas de réussite
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Product added Successfully!");
                    alert.show();
                } catch (SQLException e) {
                    // Affichage d'une boîte de dialogue d'erreur en cas d'échec
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            } else {
                // CAPTCHA not solved correctly, handle accordingly
                // For example, display an error message to the user
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Navigate(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherProduit.fxml"));
            PnomTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImage = new Image(selectedFile.toURI().toString());
            imageView.setImage(selectedImage);
        }
    }

}
