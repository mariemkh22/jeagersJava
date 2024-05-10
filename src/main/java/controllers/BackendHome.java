package controllers;

import com.mysql.cj.log.Log;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.serviceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BackendHome {

    @FXML
    private ImageView profileB;

    @FXML
    private ImageView homeButton;

    @FXML
    private Button showB;
    @FXML
    private TableColumn<User, ?> ActionsShow;

    @FXML
    private Button DeliveryB;

    @FXML
    private TableColumn<User, Integer> IdShow;

    @FXML
    private Button MessageB;

    @FXML
    private Button ProductB;

    @FXML
    private Button ServiceB;

    @FXML
    private Button UserB;

    @FXML
    private TableColumn<User, String> dateShow;

    @FXML
    private TableColumn<User, String> emailShow;

    @FXML
    private Label labelTitle;

    @FXML
    private TableColumn<User, String> nameShow;

    @FXML
    private TableColumn<User, String> phoneShow;

    @FXML
    private TableView<User> tableView;



    @FXML
    void DeliveryButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherLivraison.fxml"));
            DeliveryB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void MessagingButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            MessageB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ProductManagmentButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherProduit.fxml"));
            ProductB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ServiceManagmentButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherService.fxml"));
            ServiceB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void homeButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
            homeButton.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void UserManagmentButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            UserB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private final serviceUser px=new serviceUser();

    @FXML
    void initialize(){
        try {
            List<User> users = px.displayUser();
            ObservableList<User> observableList = FXCollections.observableList(users);
            tableView.setItems(observableList);
            nameShow.setCellValueFactory(new PropertyValueFactory<>("full_name"));
            emailShow.setCellValueFactory(new PropertyValueFactory<>("email"));
            phoneShow.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
            dateShow.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));
        } catch (SQLException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void showAllButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            UserB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void profileButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
