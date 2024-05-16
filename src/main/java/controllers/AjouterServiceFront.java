package controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import entities.Produit;
import entities.categorie_service;
import entities.service;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceCategorie;
import services.ServiceProduit;
import services.ServiceService;

import javax.swing.*;

public class AjouterServiceFront {

    @FXML
    private Button homeB;

    @FXML
    private Button myS;

    @FXML
    private Button serviceB;

    @FXML
    private Label deliveryB;

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

    @FXML
    private ImageView qrCodeImageView;
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
        try {
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
                String name_s = namestf.getText();
                String description_s = descriptionstf.getText();
                String localisation = locationtf.getText();
                String state = statetf.getText();
                String dispo_date = datetf.getText();
                int cat_id= categorieBox.getValue().getId();

                // Vérification de la saisie
                if (name_s.isEmpty() || description_s == null || localisation.isEmpty() || state.isEmpty() || dispo_date.isEmpty()) {
                    // Modification du style des champs de texte invalides
                    if (name_s.isEmpty()) {
                        namestf.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        namestf.setStyle("");
                    }
                    if (description_s == null) {
                        descriptionstf.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        descriptionstf.setStyle("");
                    }
                    if (localisation.isEmpty()) {
                        locationtf.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        locationtf.setStyle("");
                    }
                    if (state.isEmpty()) {
                        statetf.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        statetf.setStyle("");
                    }
                    if (dispo_date.isEmpty()) {
                        datetf.setStyle("-fx-border-color: red; -fx-text-inner-color: red;");
                    } else {
                        datetf.setStyle("");
                    }
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please fill in all fields!");
                    alert.show();
                    return;
                }

                if (selectedImage == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please select an image.");
                    alert.show();
                    return;
                }

                // Création de l'objet Produit
                service p = new service(name_s, description_s, localisation, state, dispo_date, cat_id, selectedImage.getUrl(),Login.getCurrentUser().getId());

                // Utilisation du service pour ajouter le produit à la base de données
                ServiceService sp = new ServiceService();
                try {
                    sp.ajouter(p);
                    // Affichage d'une boîte de dialogue de succès en cas de réussite
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Product added Successfully!");
                    alert.show();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
                // Générer le code QR
                try {
                    String qrData = generateQRData(name_s, description_s, localisation, state, dispo_date, cat_id);
                    ByteArrayOutputStream outputStream = generateQRCode(qrData);
                    byte[] qrCodeBytes = outputStream.toByteArray();

                    // Convertir les octets du code QR en une chaîne Base64
                    String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);

                    // Charger l'image du code QR dans l'ImageView qrCodeImageView
                    qrCodeImageView.setImage(new Image(new ByteArrayInputStream(qrCodeBytes)));
                } catch (IOException | WriterException e) {
                    e.printStackTrace();
                    // Gérer l'erreur
                }
            } else {
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String generateQRData(String name_s, String description_s, String localisation, String state, String dispo_date, int cat_id) {
        return "Name: " + name_s + "\nDescription: " + description_s + "\nLocation: " + localisation + "\nState: " + state + "\nAvailability Date: " + dispo_date + "\nCategory ID: " + cat_id;
    }
    ByteArrayOutputStream generateQRCode(String data) throws WriterException, IOException {
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 1000, 1000, hintMap);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream;
    }

    @FXML
    void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImage = new Image(selectedFile.toURI().toString());
            imageView.setImage(selectedImage);
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
    void homeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void myServices(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ServicecardListView.fxml"));
            myS.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void serviceButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ourServices.fxml"));
            serviceB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}