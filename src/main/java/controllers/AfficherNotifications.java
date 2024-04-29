package controllers;

import entities.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceNotification;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherNotifications {
    @FXML
    private TableColumn<Notification, String> contenueCol;

    @FXML
    private TableColumn<Notification, String> dateCol;

    @FXML
    private TableColumn<Notification, String> sujetCol;

    @FXML
    private TableView<Notification> tableview;
    @FXML
    private Text sujetTF;
    private final ServiceNotification serviceNotification=new ServiceNotification();
    @FXML
    void initialize() {
        try {
            List<Notification> notifications = serviceNotification.afficher();
            ObservableList<Notification> observableList = FXCollections.observableList(notifications);
            tableview.setItems(observableList);


            dateCol.setCellValueFactory(new PropertyValueFactory<>("date_envoie"));
            sujetCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));
            contenueCol.setCellValueFactory(new PropertyValueFactory<>("contenue"));



            System.out.println("Nombre de notifications chargés : " + observableList.size());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void NaviguerajoutAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterNotification.fxml"));
            sujetTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
    @FXML
    public void deletenotifAction(ActionEvent actionEvent) {
        // Obtenez la notification sélectionnée dans la table
        Notification notificationSelectionnee = tableview.getSelectionModel().getSelectedItem();
        if (notificationSelectionnee == null) {
            // Affichez une alerte si aucune notification n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune notification sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une notification à supprimer.");
            alert.showAndWait();
            return;
        }

        try {
            // Supprimez la notification de la base de données en utilisant le service
            serviceNotification.supprimer(notificationSelectionnee.getId());

            // Supprimez la notification de la liste observable utilisée par la table view
            ObservableList<Notification> items = tableview.getItems();
            items.remove(notificationSelectionnee);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Supprimez la notification de la liste observable utilisée par la table view



// Mettez à jour la table view pour refléter la suppression
        tableview.refresh();
    }
}
