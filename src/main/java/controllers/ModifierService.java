package controllers;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;

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
    private ImageView qrCodeImageView;


    @FXML
    private ImageView imageView;


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
    private ComboBox<categorie_service> categorieBox;

    private service serviceToUpdate;
    private Image currentImage;

    private Stage stage;


    @FXML
    private TextField imgID;

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

    //pour initialiser les données de service à modifier
    public void initData(service service) {
        serviceToUpdate = service;

        namestf.setText(service.getName_s());
        descriptionstf.setText(service.getDescription_s());
        locationtf.setText(service.getLocalisation());
        statetf.setText(service.getState());
        datetf.setText(service.getDispo_date());
        imgID.setText(service.getImageFile());


    }


    @FXML
    boolean modifierService(ActionEvent event) {
        if (serviceToUpdate != null) {

            serviceToUpdate.setName_s(namestf.getText());
            serviceToUpdate.setDescription_s(descriptionstf.getText());
            serviceToUpdate.setLocalisation(locationtf.getText());
            serviceToUpdate.setState(statetf.getText());
            serviceToUpdate.setDispo_date(datetf.getText());
            serviceToUpdate.setImageFile(imgID.getText());


            // controle de saisie:
            String name_s = namestf.getText();
            String description_s = descriptionstf.getText();
            String localisation = locationtf.getText();
            String state = statetf.getText();
            String dispo_date = datetf.getText();


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
                return erreur;
            }


            try {
                ss.modifier(serviceToUpdate);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Service updated successfully.");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An Error occurred while updating the service : " + e.getMessage());
                alert.showAndWait();
            }
            // Génération du code QR
            try {
                String qrData = generateQRData(name_s, description_s, localisation, state, dispo_date);
                ByteArrayOutputStream outputStream = generateQRCode(qrData);
                byte[] qrCodeBytes = outputStream.toByteArray();
                qrCodeImageView.setImage(new Image(new ByteArrayInputStream(qrCodeBytes)));
            } catch (IOException | WriterException e) {
                e.printStackTrace();
                // Gérer l'erreur
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection !");
            alert.setHeaderText(null);
            alert.setContentText("No services to update.");
            alert.showAndWait();
        }
        return false;
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
    void addImage1(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imageView.setImage(new Image(file.toURI().toString()));
        }
    }

    private String generateQRData(String name_s, String description_s, String localisation, String state, String dispo_date) {
        return "Name: " + name_s + "\nDescription: " + description_s + "\nLocation: " + localisation + "\nState: " + state + "\nAvailability Date: " + dispo_date;
    }

    private ByteArrayOutputStream generateQRCode(String data) throws WriterException, IOException {
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 1000, 1000, hintMap);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream;
    }
}






