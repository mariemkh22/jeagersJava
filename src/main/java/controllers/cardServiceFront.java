package controllers;

import com.google.zxing.WriterException;
import entities.Produit;
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
import java.util.Random;

public class cardServiceFront {

    @FXML
    private ImageView imgP;

    @FXML
    private Label Pname;

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
    private Label SERcategorie;


    private service service;
    @FXML
    private ImageView qrCodeImageView;

    private String[] Colors = {"#D5D6D4"};



    // Appeler cette méthode lorsque l'utilisateur donne une nouvelle note

    @FXML
    private void initialize() {
        // Initialiser le rating à 0
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

    public void setService(service service) {
        this.service = service;
        if (service != null && service.getImageFile() != null && !service.getImageFile().isEmpty()) {
            try {
                String filePath = service.getImageFile().replace("file:", "");
                filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());

                File imageFile = new File(filePath);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imgP.setImage(image);
                } else {
                    System.out.println("Image file does not exist: " + service.getImageFile());
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Produit or image file path is null or empty");
        }

        Pname.setText(service.getName_s());


        // Select a random light blue color from the array
        String randomColor = Colors[(int) (Math.random() * Colors.length)];
        // Apply the random light blue color to the background of the card box
        box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
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






