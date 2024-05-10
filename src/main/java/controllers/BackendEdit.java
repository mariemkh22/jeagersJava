package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.serviceUser;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BackendEdit {

    @FXML
    private Button feedbackB;

    @FXML
    private ImageView profileB;

    @FXML
    private ImageView home;

    @FXML
    private Button DeliveryB;

    @FXML
    private Button MessageB;

    @FXML
    private Button ProductB;

    @FXML
    private Button ServiceB;

    @FXML
    private Button UserB;

    @FXML
    private Button addB;

    @FXML
    private Button cancelB;

    @FXML
    private Button editB;

    @FXML
    private TextField editdateTF;

    @FXML
    private TextField editemailTF;

    @FXML
    private TextField editnameTF;

    @FXML
    private TextField editphoneTF;

    @FXML
    private Button homeB;

    @FXML
    private Button homeBDash;

    @FXML
    private Label labelErrorMessage;

    @FXML
    private Button listB;

    int id=0;
    private User userMoved;
    Connection connection = MyDatabase.getInstance().getConnection();
    private final serviceUser px=new serviceUser();


    public void initData(User user) {
        userMoved = user;
        editnameTF.setText(user.getFull_name());
        editemailTF.setText(user.getEmail());
        editphoneTF.setText(user.getPhone_number());
        editdateTF.setText(user.getDate_of_birth());
    }


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
    void HomeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
            homeBDash.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void homeButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
            home.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void MessagingButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            MessageB.getScene().setRoot(root);
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
    void UserManagmentButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            UserB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendAddUser.fxml"));
            addB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cancelButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            cancelB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void editButton(ActionEvent event) {
        if(userMoved!=null){
            userMoved.setFull_name(editnameTF.getText());
            userMoved.setEmail(editemailTF.getText());
            userMoved.setPhone_number(editphoneTF.getText());
            userMoved.setDate_of_birth(editdateTF.getText());
        }
        try {
            px.updateUser(userMoved);
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            editB.getScene().setRoot(root);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @FXML
    void homeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
            homeBDash.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void listButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            listB.getScene().setRoot(root);
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

    @FXML
    void feedbackButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/feedback.fxml"));
            feedbackB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
