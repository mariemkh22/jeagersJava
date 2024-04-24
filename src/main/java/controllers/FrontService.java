package controllers;

import entities.service;
import services.ServiceService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FrontService implements Initializable {



    @FXML
    private HBox boxservices;

    private ServiceService serviceService = new ServiceService();

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
        List<service> services = serviceService.afficher();

        for (service service : services)
        {
            try
            {

                // Charge chaque carte des services et l'ajoute au GridPane
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/card.fxml"));
                HBox box = loader.load();
                CardController controller = loader.getController();

                controller.setService(service);
                boxservices.getChildren().add(box);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}