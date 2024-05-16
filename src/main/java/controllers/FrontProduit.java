package controllers;

import controllers.CardController;
import entities.Produit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServiceProduit;


import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FrontProduit implements Initializable {

    @FXML
    private Button myPB;

    @FXML
    private Button commandB;

    @FXML
    private Button addB;

    @FXML
    private Button stat;

    @FXML
    private Button productB;

    @FXML
    private Button homeB;

    @FXML
    private HBox boxproduit;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView profileB;

    private final ServiceProduit serviceProduit = new ServiceProduit();
    private List<Produit> allProducts;
    private final int ITEMS_PER_PAGE = 3; // Number of items per page

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadArticles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        boxproduit.getChildren().clear();
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, allProducts.size());

        for (int i = startIndex; i < endIndex; i++) {
            Produit produit = allProducts.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/card.fxml"));
                HBox box = loader.load();
                CardController controller = loader.getController();
                controller.setService(produit);
                boxproduit.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void search() {
        String query = searchField.getText().toLowerCase();
        if (query.isEmpty()) {
            reloadAllProducts();
        } else {
            List<Produit> filteredProducts = allProducts.stream()
                    .filter(produit -> produit.getNomProduit().toLowerCase().contains(query) ||
                            produit.getDescription().toLowerCase().contains(query) ||
                            produit.getType().toLowerCase().contains(query))
                    .collect(Collectors.toList());
            reloadProducts(filteredProducts);
        }
    }

    private void reloadAllProducts() {
        pagination.setCurrentPageIndex(0); // Reset to the first page
        loadPage(0);
    }

    private void reloadProducts(List<Produit> products) {
        boxproduit.getChildren().clear();
        for (Produit produit : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/card.fxml"));
                HBox box = loader.load();
                CardController controller = loader.getController();
                controller.setService(produit);
                boxproduit.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Update pagination based on the filtered products
        int pageCount = (int) Math.ceil((double) products.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0); // Set to the first page
    }

    public void profileButton(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
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
    void ordersL(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cardListView.fxml"));
            productB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @FXML
    void StatB(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/PieChart.fxml"));
            stat.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addpng(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterProduitFront.fxml"));
            addB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void commandButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCommandeFront.fxml"));
            commandB.getScene().setRoot(root);
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
}
