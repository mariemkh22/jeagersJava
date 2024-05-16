package controllers;

import com.google.zxing.WriterException;
import entities.categorie_service;
import entities.service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import menusandcharts.MenuAppWithChartController;
import org.controlsfx.control.Rating;
import services.ServiceCategorie;
import services.ServiceService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;

public class ourServicesCard {

    @FXML
    private HBox box;

    @FXML
    private Label SERdate;

    @FXML
    private Label SERdescription;

    @FXML
    private Label SERlocation;

    @FXML
    private Label SERname;

    @FXML
    private Label SERstate;

    @FXML
    private ImageView imgSRC;

    @FXML
    private VBox ratingBox; // Ajout du VBox pour les étoiles de notation



    @FXML
    private Label SERcategorie;


    private service service;
    @FXML
    private ImageView qrCodeImageView;

    private String[] Colors = {"#abb3ff", "#bfc5ff", "#b0beff"};


    @FXML
    private Rating serviceRating;

    // Appeler cette méthode lorsque l'utilisateur donne une nouvelle note
    @FXML
    private void handleRatingChange() {
        int serviceId = getServiceId(); // Récupérer l'identifiant du service
        int newRating = (int) serviceRating.getRating(); // Récupérer la nouvelle note
        RatingManager.saveRating(serviceId, newRating); // Enregistrer le rating
    }

    @FXML
    private void initialize() {
        // Initialiser le rating à 0
        serviceRating.setRating(0);

        // Ajouter un écouteur d'événements pour capturer les changements de note
        serviceRating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            handleRatingChange(); // Enregistrer le rating
        });
    }

    // Méthode pour récupérer l'identifiant du service à partir de votre modèle de données
    private int getServiceId() {
        // Implémentez cette méthode pour récupérer l'identifiant du service
        return 123; // Exemple: retourne l'identifiant du service
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private String generateQRData(service service) {
        // Concaténer toutes les données du service dans une chaîne
        return "Name: " + service.getName_s() +
                "\nDescription: " + service.getDescription_s() +
                "\nLocation: " + service.getLocalisation() +
                "\nState: " + service.getState() +
                "\nAvailability Date: " + service.getDispo_date() +
                "\nCategory ID: " + service.getCat_id();
    }

    public void setService(service service) {
        if (service != null && service.getImageFile() != null && !service.getImageFile().isEmpty()) {
            try {
                String filePath = service.getImageFile().replace("file:", "");
                filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());

                File imageFile = new File(filePath);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imgSRC.setImage(image);
                } else {
                    System.out.println("Image file does not exist: " + service.getImageFile());
                }
                SERname.setText(service.getName_s());


                String randomColor = Colors[(int) (Math.random() * Colors.length)];
                box.setStyle("-fx-background-color: " + randomColor +
                        "; -fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");

                try {
                    ServiceCategorie categorieService = new ServiceCategorie();
                    categorie_service categorie = categorieService.getById(service.getCat_id());

                    // Vérifier si la catégorie est trouvée
                    if (categorie != null) {
                        // Afficher le nom de la catégorie
                        SERcategorie.setText(categorie.getName_c());
                    } else {
                        // Si la catégorie n'est pas trouvée, afficher un message générique ou vide
                        SERcategorie.setText("Unknown");
                    }

                    String qrData = generateQRData(service);
                    AjouterServiceFront ajouterServiceController = new AjouterServiceFront();
                    ByteArrayOutputStream outputStream = ajouterServiceController.generateQRCode(qrData);
                    byte[] qrCodeBytes = outputStream.toByteArray();
                    qrCodeImageView.setImage(new Image(new ByteArrayInputStream(qrCodeBytes)));

                } catch (SQLException | WriterException | IOException e) {
                    e.printStackTrace(); // Gérer les exceptions appropriées
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Produit or image file path is null or empty");
        }
    }








}






