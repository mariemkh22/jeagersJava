package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.*;
import javafx.scene.chart.PieChart;
import entities.Produit;
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
}

