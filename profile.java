package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class profile {
    @FXML
    private Button editProfile;

    @FXML
    private Button logoutB;

    @FXML
    private TextField readDate;

    @FXML
    private TextField readEmail;

    @FXML
    private TextField readName;

    @FXML
    private TextField readPhone;

    @FXML
    private Label nameAboveLabel;

    @FXML
    private Button homeB;

    Connection connection;

    @FXML
    void initialize() {
        readName.setText(Login.getCurrentUser().getFull_name());
        readDate.setText(Login.getCurrentUser().getDate_of_birth());
        readEmail.setText(Login.getCurrentUser().getEmail());
        readPhone.setText(Login.getCurrentUser().getPhone_number());
        nameAboveLabel.setText(Login.getCurrentUser().getFull_name());
    }

    @FXML
    void editProfileButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileEdit.fxml"));
            editProfile.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void logoutButton(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage=new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root,569,400));
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            Login.clearUserSession();
            if (Login.getCurrentUser()==null){
                System.out.println("disconnected!");
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void homeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
