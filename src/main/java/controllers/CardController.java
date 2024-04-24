package controllers;

import entities.Produit;
import services.ServiceProduit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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

    private Produit produit;

    private String[] blueColors = {"#87CEEB", "#00BFFF", "#1E90FF", "#4169E1", "#0000FF", "#0000CD", "#00008B", "#000080"};



    private ServiceProduit serviceProduit;

    public void setService(Produit produit) {
        this.produit = produit;
        if (produit != null) {

            Pname.setText(produit.getNomProduit());
            Pdescription.setText(produit.getDescription());
            Ptype.setText(produit.getType());
            PEquiv.setText(String.valueOf(produit.getEquiv()));

        }
        String randomColor = blueColors[(int) (Math.random() * blueColors.length)];
       box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }




    @FXML
    void DeleteF(ActionEvent event)  {
        // Perform deletion logic here
        if (produit != null) {
            try {
                // Remove the product from the database
                serviceProduit.supprimer(produit.getId());

                // Remove the card from the UI
                box.getChildren().clear();

                // Notify the parent controller to update its data
                Parent parent = box.getParent();
                if (parent instanceof VBox) {
                    VBox parentVBox = (VBox) parent;
                    if (parentVBox.getChildren().contains(box)) {
                        parentVBox.getChildren().remove(box);
                    }
                }
            } catch (SQLException e) {
                // Handle the exception
                e.printStackTrace();
            }
        }
    }












}