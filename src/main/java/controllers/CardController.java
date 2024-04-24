package controllers;

import entities.service;
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

import java.io.File;
import java.io.IOException;

public class CardController {
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

    private service service;

    private String[] greenColors = {"#7CFC00", "#32CD32", "#228B22", "#008000", "#006400", "#ADFF2F", "#9ACD32", "#556B2F"};


    public void setService(service service) {
        this.service = service;
        if (service != null) {
            Image image = new Image(new File(service.getImageFile()).toURI().toString());
            imgSRC.setImage(image);
            SERname.setText(service.getName_s());
            SERdescription.setText(service.getDescription_s());
            SERlocation.setText(service.getLocalisation());
            SERstate.setText(service.getState());
            SERdate.setText(service.getDispo_date());
        }
        String randomColor = greenColors[(int) (Math.random() * greenColors.length)];
       box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }




}