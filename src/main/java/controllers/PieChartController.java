package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import entities.Produit;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.ServiceProduit;

public class PieChartController implements Initializable {

    @FXML
    private Button commandB;

    @FXML
    private Button addProductB;

    @FXML
    private Button statisticB;

    @FXML
    private Button homeB;

    @FXML
    private Button productB;

    @FXML
    private ImageView profileB;

    @FXML
    private PieChart chart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch products from the database
        List<Produit> products = ServiceProduit.getProducts();

        // Calculate the count of products for each category
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Produit produit : products) {
            String category = produit.getType();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        // Convert category count to percentages
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        int totalProducts = products.size();
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            String category = entry.getKey();
            int count = entry.getValue();
            double percentage = (count * 100.0) / totalProducts;
            pieChartData.add(new PieChart.Data(category, percentage));
        }

        // Populate the pie chart with data
        chart.setData(pieChartData);
    }

    public void profileButton(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    @FXML
    void statisticsButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/PieChart.fxml"));
            statisticB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addProductButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterProduitFront.fxml"));
            addProductB.getScene().setRoot(root);
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
    void productButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cardListView.fxml"));
            productB.getScene().setRoot(root);
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
}

