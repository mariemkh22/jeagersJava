package controllers;

import Entity.Livraison;
import Services.Servicelivraison;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DiscountController {

    @FXML
    private Label regionWith30PercentOff;

    @FXML
    private Label regionWith20PercentOff;

    private final Servicelivraison serviceLivraison = new Servicelivraison();

    @FXML
    private Button backbtn;

    @FXML
    public void initialize() {
        try {
            // Récupérer la liste des livraisons
            List<Livraison> livraisons = serviceLivraison.afficher(null);

            // Compter le nombre de livraisons par région
            Map<String, Integer> regionCounts = new HashMap<>();
            for (Livraison livraison : livraisons) {
                String region = livraison.getLocalisationGeographique().getRegion();
                regionCounts.put(region, regionCounts.getOrDefault(region, 0) + 1);
            }

            // Appliquer les réductions et afficher les résultats
            StringBuilder region30PercentOff = new StringBuilder();
            StringBuilder region20PercentOff = new StringBuilder();
            for (Map.Entry<String, Integer> entry : regionCounts.entrySet()) {
                String region = entry.getKey();
                int count = entry.getValue();
                if (count >= 3) {
                    double discount = 0.3; // 30% discount
                    region30PercentOff.append(region).append(" (30% off), ");
                } else if (count == 2) {
                    double discount = 0.2; // 20% discount
                    region20PercentOff.append(region).append(" (20% off), ");
                }
            }

            // Afficher les régions avec des réductions dans l'interface
            regionWith30PercentOff.setText(region30PercentOff.toString());
            regionWith20PercentOff.setText(region20PercentOff.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur SQL
        }
    }
    @FXML
    void backbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLivraisonFront.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
