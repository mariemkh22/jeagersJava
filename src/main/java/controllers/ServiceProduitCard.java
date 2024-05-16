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
import services.ServiceProduit;
import services.ServiceService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;

public class ServiceProduitCard {

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


    private Produit produit;
    @FXML
    private ImageView qrCodeImageView;

    private String[] Colors = {"#abb3ff", "#bfc5ff", "#b0beff"};


    @FXML
    private void initialize() {
    }

    public void setService(Produit produit) {
        this.produit = produit;
        if (produit != null && produit.getImageFile() != null && !produit.getImageFile().isEmpty()) {
            try {
                String filePath = produit.getImageFile().replace("file:", "");
                filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());

                File imageFile = new File(filePath);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imgSRC.setImage(image);
                } else {
                    System.out.println("Image file does not exist: " + produit.getImageFile());
                }
                SERname.setText(produit.getNomProduit());
                SERname.setText(produit.getType());

                String randomColor = Colors[(int) (Math.random() * Colors.length)];
                box.setStyle("-fx-background-color: " + randomColor +
                        "; -fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Produit or image file path is null or empty");
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
            ServiceProduit serviceProduit = new ServiceProduit();
            try {
                serviceProduit.supprimer(produit.getId());

                ((Pane) box.getParent()).getChildren().remove(box);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setData(Produit produit) {
        this.produit = produit;
    }


}






