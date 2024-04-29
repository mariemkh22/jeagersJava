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
import utils.myDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileAdminEdit {
    @FXML
    private Button cancelB;

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
    private Button logoutB;

    @FXML
    private Button profileB;

    @FXML
    private Button saveB;

    int id =0;

    Connection connection = myDatabase.getInstance().getConnection();

    @FXML
    void initialize() {
        editnameTF.setText(Login.getCurrentUser().getFull_name());
        editemailTF.setText(Login.getCurrentUser().getEmail());
        editphoneTF.setText(Login.getCurrentUser().getPhone_number());
        editdateTF.setText(Login.getCurrentUser().getDate_of_birth());
    }

    @FXML
    void cancelButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
            cancelB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void homeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
            homeB.getScene().setRoot(root);
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
    void profileButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void saveButton(ActionEvent event) {
        String req ="update user set email=?,full_name=?,phone_number=?,date_of_birth=? where id=?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, editemailTF.getText());
            preparedStatement.setString(2, editnameTF.getText());
            preparedStatement.setString(3, editphoneTF.getText());
            preparedStatement.setString(4, editdateTF.getText());
            preparedStatement.setInt(5, Login.getCurrentUser().getId());
            preparedStatement.executeUpdate();
            initialize();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
                saveB.getScene().setRoot(root);
                initialize();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
