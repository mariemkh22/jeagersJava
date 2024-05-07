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
            erreurNomLabel.setText("");
        }

        if (description_s.isEmpty()) {
            erreurDescriptionLabel.setText("Please enter the description.");
            erreur = true;
        } else {
            erreurDescriptionLabel.setText("");
        }

        if (localisation.isEmpty()) {
            erreurLocationLabel.setText("The location field is empty.");
            erreur = true;
        } else {
            erreurLocationLabel.setText("");
        }

        if (state.isEmpty()) {
            erreurStateLabel.setText("The state field is empty.");
            erreur = true;
        } else {
            erreurStateLabel.setText("");
        }

        if (dispo_date.isEmpty()) {
            erreurDateLabel.setText("Please enter the availability date.");
            erreur = true;
        } else {
            erreurDateLabel.setText("");
        }

        if (erreur) {
            return;
        }

        ServiceService ss = new ServiceService();

        //recuperation du chemin d'image selectionnée//
        String imagePath = selectedImage.getUrl();

        try {
            service s = new service(name_s, description_s, localisation, state, dispo_date, cat_id, imageFile.getUrl());
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
}