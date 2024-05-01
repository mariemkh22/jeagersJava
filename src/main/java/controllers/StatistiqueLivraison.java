package controllers;

import Entity.Livraison;
import Services.Servicelivraison;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class StatistiqueLivraison implements Initializable {

    @FXML
    private BarChart<String, Integer> livraisonBarChart;

    @FXML
    private CategoryAxis regionAxis;

    @FXML
    private NumberAxis livraisonCountAxis;

    private Servicelivraison servicelivraison;

    public StatistiqueLivraison() {
        servicelivraison = new Servicelivraison();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherStatistique();
    }

    private void afficherStatistique() {
        try {
            List<Livraison> livraisons = servicelivraison.afficher(null);

            Map<String, Integer> livraisonsParRegion = new HashMap<>();
            for (Livraison livraison : livraisons) {
                String region = livraison.getLocalisationGeographique().getRegion();
                livraisonsParRegion.put(region, livraisonsParRegion.getOrDefault(region, 0) + 1);
            }

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(livraisonsParRegion.entrySet());
            Collections.sort(sortedEntries, (e1, e2) -> {
                int compare = e2.getValue().compareTo(e1.getValue());
                if (compare == 0) {
                    return e1.getKey().compareTo(e2.getKey());
                }
                return compare;
            });

            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            livraisonBarChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
