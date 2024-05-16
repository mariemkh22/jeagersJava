package controllers;

import entities.Produit;
import entities.categorie_service;
import entities.service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServiceCategorie;
import services.ServiceProduit;
import services.ServiceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Home implements Initializable {

    @FXML
    private Button about;

    @FXML
    private Button myPB;

    @FXML
    private ImageView profileB;

    @FXML
    private Button homeB;

    @FXML
    private Button productB;

    @FXML
    private Button deliveryB;

    @FXML
    private Button messageB;

    @FXML
    private Button serviceB;


    @FXML
    private Button myPP;

    @FXML
    private Button mySS;

    @FXML
    private HBox boxservices;

    @FXML
    private HBox boxproducts;

    @FXML
    private Pagination pagination;
    @FXML
    private ComboBox<categorie_service> filter;

    @FXML
    private TextField searchS;


    categorie_service selectedValue=null;

    private List<Produit> allProducts;

    private final ServiceProduit serviceProduit = new ServiceProduit();

    private ServiceService serviceService = new ServiceService();

    private final int ITEMS_PER_PAGE = 3; // Nombre d'éléments par page

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadServices();
            loadArticles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadServices() throws SQLException {

        List<service> services =null;

        if(selectedValue!=null){
            if(selectedValue.toString().equals("ALL")){
                services=serviceService.afficher();
            }else{
                int id = selectedValue.getId();
                services=serviceService.getServiceByCategorie(id);
                selectedValue=null;}
        }else{
            services=serviceService.afficher();
        }


        int pageCount = (int) Math.ceil((double) services.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0); // Définir la page actuelle sur la première page

        List<service> finalServices = services;
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                loadPage(newValue.intValue(), finalServices);
            }
        });

        loadPage(0, services);
    }

    private void loadPage(int pageIndex, List<service> services) {
        boxservices.getChildren().clear();
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, services.size());

        for (int i = startIndex; i < endIndex; i++) {
            service service = services.get(i);
            try {
                // Chargement des cartes
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardServiceFront.fxml"));
                HBox box = loader.load();
                cardServiceFront controller = loader.getController();
                controller.setService(service);
                boxservices.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadArticles() throws SQLException {
        allProducts = serviceProduit.afficher();
        int pageCount = (int) Math.ceil((double) allProducts.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0); // Set current page to the first page

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                loadPage(newValue.intValue());
            }
        });

        loadPage(0);
    }

    private void loadPage(int pageIndex) {
        boxproducts.getChildren().clear();
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, allProducts.size());

        for (int i = startIndex; i < endIndex; i++) {
            Produit produit = allProducts.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardProductFront.fxml"));
                HBox box = loader.load();
                produitFront controller = loader.getController();
                controller.setService(produit);
                boxproducts.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void profileButton(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void homeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void serviceButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ourServices.fxml"));
            messageB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    public void productButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cardListView.fxml"));
            productB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void deliveryButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            deliveryB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void messageButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Addmsg.fxml"));
            messageB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void myS(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ServicecardListView.fxml"));
            serviceB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void myProducts(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/myProductsFront.fxml"));
            myPB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void aboutUsButton(ActionEvent event) {

    }

}
