package controllers;

import entities.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceNotification;

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

    private final ServiceNotification serviceNotification=new ServiceNotification();
    @FXML
    void iniatialize(){

        try {
            List<Notification> notifications = serviceNotification.afficher();
            ObservableList<Notification> observableList=FXCollections.observableList(notifications);

            notifications = serviceNotification.afficher();
            tableview.setItems(observableList);
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date_envoie"));
            sujetCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));
            contenueCol.setCellValueFactory(new PropertyValueFactory<>("contenue"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }


    }

}
