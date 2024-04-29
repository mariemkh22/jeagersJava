package controllers;

import entities.service;
import javafx.event.ActionEvent;
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
import java.net.MalformedURLException;
import java.net.URL;

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

    private String[] Colors = {"#abb3ff", "#bfc5ff", "#b0beff"};



    public void setService(service service) {
        this.service = service;
        if (service != null) {
            Image imageFile = new Image(new File(service.getImageFile()).toURI().toString());
            imgSRC.setImage(imageFile);
            SERname.setText(service.getName_s());
            SERdescription.setText(service.getDescription_s());
            SERlocation.setText(service.getLocalisation());
            SERstate.setText(service.getState());
            SERdate.setText(service.getDispo_date());
        }
        File imageFile = new File("C:\\Users\\khadi\\IdeaProjects\\Pi\\src\\main\\resources"+service.getImageFile());

        String imageUrl = imageFile.toURI().toString();

        try {
            URL url = new URL(imageUrl);
            System.out.println(url);

            // Load the image from the URL
            Image image = new Image(url.toExternalForm());

            // Set the image to the ImageView
            imgSRC.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Handle invalid URL exception
        }
        String randomColor = Colors[(int) (Math.random() * Colors.length)];
       box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }
}