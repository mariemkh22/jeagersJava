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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

public class ServiceCardController {

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
        this.service = service;
        if (service != null) {
            Image imageFile = new Image(new File(service.getImageFile()).toURI().toString());
            imgSRC.setImage(imageFile);

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

                // Générer et afficher le QRCode avec les données du service
                String qrData = generateQRData(service);
                AjouterService ajouterServiceController = new AjouterService(); // Créer une instance du contrôleur AjouterService
                ByteArrayOutputStream outputStream = ajouterServiceController.generateQRCode(qrData); // Appeler la méthode generateQRCode() depuis cette instance
                byte[] qrCodeBytes = outputStream.toByteArray();
                qrCodeImageView.setImage(new Image(new ByteArrayInputStream(qrCodeBytes)));

            } catch (SQLException | WriterException | IOException e) {
                e.printStackTrace(); // Gérer les exceptions appropriées
            }
        }
    }



    @FXML
    public void supprimerCard(javafx.event.ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Supprimer le service");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce service ?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ServiceService serviceService = new ServiceService();
            try {
                serviceService.supprimer(service.getId());

                ((Pane) box.getParent()).getChildren().remove(box);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @FXML
    public void modifierCard(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierService.fxml"));
            Parent root = loader.load();

            ModifierService modifierServiceController = loader.getController();
            modifierServiceController.initData(service);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier Service");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (modifierServiceController.modifierService(event)) {
                setData(service);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(service service) {
        this.service = service;
    }


}






