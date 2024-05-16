package controllers;

import entities.Produit;
import entities.categorie_service;
import entities.service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import menusandcharts.MenuAppWithChartController;
import services.ServiceCategorie;
import services.ServiceProduit;
import services.ServiceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MyProductsFront implements Initializable {

    @FXML
    private Button addService;

    @FXML
    private Button homeB;

    @FXML
    private Button myProduct;

    @FXML
    private Button productB;

    @FXML
    private HBox boxproducts;

    @FXML
    private Pagination pagination;
    @FXML
    private ComboBox<categorie_service> filter;

    @FXML
    private TextField searchS;


    categorie_service selectedValue=null;




    private ServiceProduit serviceProduit = new ServiceProduit();

    private final int ITEMS_PER_PAGE = 3; // Nombre d'éléments par page

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadProducts();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProducts() throws SQLException {

        List<Produit> produits =null;

        if(selectedValue!=null){
            if(selectedValue.toString().equals("ALL")){
                if (Login.getCurrentUser() != null) {
                    produits = serviceProduit.afficherPrivate();
                }
            }else{
                }
        }else{
            if (Login.getCurrentUser() != null) {
                produits=serviceProduit.afficherPrivate();
            }
        }

        int pageCount = (int) Math.ceil((double) produits.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0); // Définir la page actuelle sur la première page

        List<Produit> finalServices = produits;
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                loadPage(newValue.intValue(), finalServices);
            }
        });

        loadPage(0, produits);
    }


    private void loadPage(int pageIndex, List<Produit> produits) {
        boxproducts.getChildren().clear();
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, produits.size());

        for (int i = startIndex; i < endIndex; i++) {
            Produit produit = produits.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ServiceProduitCard.fxml"));
                HBox box = loader.load();
                ServiceProduitCard controller = loader.getController();
                controller.setService(produit);
                boxproducts.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void navigatetoAdd1(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterServiceFront.fxml"));
            Parent root = loader.load();
            AjouterServiceFront AjouterService = loader.getController();
            addService.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void search(ActionEvent event) {

    }
    @FXML
    void searchservice(KeyEvent event) throws SQLException {
        service.setSearchValue(((TextField) event.getSource()).getText());
        System.out.println("Recherche en cours: " + service.getSearchValue());

        // categId = -1;

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));


        List<Produit> services = null;
        //services = serviceProduit.searchService(service.getSearchValue());
        loadPage(0, services);





    }
    @FXML
    void filterS(ActionEvent event) throws SQLException {
        selectedValue=filter.getValue();
        this.loadProducts();


    }

    @FXML
    void homeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void myProducts(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/myProductsFront.fxml"));
            myProduct.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void productButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cardListView.fxml"));
            productB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
