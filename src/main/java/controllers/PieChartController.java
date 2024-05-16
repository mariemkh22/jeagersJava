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

        List<Produit> products = ServiceProduit.getProducts();

        // Calculer count
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Produit produit : products) {
            String category = produit.getType();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        // Converter percentages
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        int totalProducts = products.size();
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            String category = entry.getKey();
            int count = entry.getValue();
            double percentage = (count * 100.0) / totalProducts;
            pieChartData.add(new PieChart.Data(category, percentage));
        }


        chart.setData(pieChartData);
    }

    @FXML
    public void shop(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardListView.fxml"));
            Parent root = loader.load();


            Stage currentStage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();


            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

