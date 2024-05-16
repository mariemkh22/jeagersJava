package controllers;

import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class CardController {
    @FXML
    private HBox box;

    @FXML
    private Label PEquiv;

    @FXML
    private Label Pdescription;

    @FXML
    private Label Pname;

    @FXML
    private Label Ptype;

    @FXML
    private ImageView imgP;

    @FXML
    private Button orderButton;

    private Produit produit;

    private String[] lightBlueColors = {
            "lightblue",
            "skyblue",
            "powderblue",

    };

    public void setService(Produit produit) {
        this.produit = produit;
        if (produit != null && produit.getImageFile() != null && !produit.getImageFile().isEmpty()) {
            try {
                String filePath = produit.getImageFile().replace("file:", "");
                filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());

                File imageFile = new File(filePath);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imgP.setImage(image);
                } else {
                    System.out.println("Image file does not exist: " + produit.getImageFile());
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Produit or image file path is null or empty");
        }

        Pname.setText(produit.getNomProduit());
        Pdescription.setText(produit.getDescription());
        Ptype.setText(produit.getType());
        String price = String.valueOf(produit.getEquiv());
        PEquiv.setText(price+ " TND");

        // Select a random light blue color from the array
        String randomColor = lightBlueColors[new Random().nextInt(lightBlueColors.length)];
        // Apply the random light blue color to the background of the card box
        box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }

    @FXML
    private void orderButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommandeFront.fxml"));
            Parent root = loader.load();

            AjouterCommandeFront ajouterCommandeFrontController = loader.getController();
            ajouterCommandeFrontController.setProductName(Pname.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
