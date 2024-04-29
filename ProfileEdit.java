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
import services.serviceUser;
import utils.myDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileEdit {
    @FXML
    private TextField editDate;

    @FXML
    private TextField editEmail;

    @FXML
    private TextField editName;

    @FXML
    private TextField editPhone;

    @FXML
    private Button saveB;

    @FXML
    private Button logoutB;

    @FXML
    private Label nameAboveLabel;

    @FXML
    private Button cancelB;

    @FXML
    private Button homeB;

    int id =0;

    Connection connection = myDatabase.getInstance().getConnection();

    @FXML
    void initialize() {
        editName.setText(Login.getCurrentUser().getFull_name());
        editEmail.setText(Login.getCurrentUser().getEmail());
        editPhone.setText(Login.getCurrentUser().getPhone_number());
        editDate.setText(Login.getCurrentUser().getDate_of_birth());
        nameAboveLabel.setText(Login.getCurrentUser().getFull_name());
    }

    @FXML
    void saveButton (ActionEvent event) {
        String req ="update user set email=?,full_name=?,phone_number=?,date_of_birth=? where id=?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, editEmail.getText());
            preparedStatement.setString(2, editName.getText());
            preparedStatement.setString(3, editPhone.getText());
            preparedStatement.setString(4, editDate.getText());
            preparedStatement.setInt(5, Login.getCurrentUser().getId());
            preparedStatement.executeUpdate();
            initialize();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
                saveB.getScene().setRoot(root);
                initialize();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @FXML
    void cancelButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
            cancelB.getScene().setRoot(root);
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
