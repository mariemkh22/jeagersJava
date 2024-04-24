package controllers;

import entities.Produit;
import services.ServiceProduit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FrontProduit implements Initializable {



    @FXML
    private HBox boxproduit;

    private final ServiceProduit serviceProduit = new ServiceProduit();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadArticles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int column = 0 ;
    private int row = 1 ;

    private void loadArticles() throws SQLException {
        List<Produit> produits = serviceProduit.afficher();

        for (Produit produit : produits)
        {
            try
            {

                // Charge chaque carte des services et l'ajoute au GridPane
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/card.fxml"));
                HBox box = loader.load();
                CardController controller = loader.getController();

                controller.setService(produit);
                boxproduit.getChildren().add(box);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }








}