package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import entities.Produit;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.ServiceProduit;

public class PieChartController implements Initializable {

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

    @FXML
    public void shop(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardListView.fxml"));
            Parent root = loader.load();

            // Obtenez la fenêtre actuelle à partir de l'image cliquée
            Stage currentStage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();

            // Changez la scène de la fenêtre actuelle
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

