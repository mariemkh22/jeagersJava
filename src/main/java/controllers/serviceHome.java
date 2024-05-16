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

public class serviceHome {

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



    @FXML
    private void initialize() {

    }


    private int getServiceId() {
        return 123;
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
        }
    }

    public void setData(service service) {
        this.service = service;
    }


}






