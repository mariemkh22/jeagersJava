package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.serviceUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

public class SignUpAdmin {

    @FXML
    private Label dateLabel;

    @FXML
    private TextField dateTF;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTF;

    @FXML
    private Label fullNameLabel;

    @FXML
    private TextField nameTF;

    @FXML
    private Label passLabel;

    @FXML
    private TextField passwordTF;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField phoneTF;

    @FXML
    private Button resetB;

    @FXML
    private Button signUpB;

    @FXML
    private Label messageLabel;

    private final serviceUser px = new serviceUser();

    @FXML
    void SignUpOnAction(ActionEvent event) {
        try{
            if (emailTF.getText().isBlank() == true || passwordTF.getText().isBlank() == true || nameTF.getText().isBlank() == true || phoneTF.getText().isBlank() == true || dateTF.getText().isBlank() == true){
                messageLabel.setText("Please fill the form.");
            }
            else {
                String role = "[\"ROLE_ADMIN\"]";
                String pass = hashPassword(passwordTF.getText());
                px.addUser(new User(emailTF.getText(),pass,role,nameTF.getText(),phoneTF.getText(),dateTF.getText()));
                loginAccount(event);
            }
        }
        catch (SQLException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) {
        nameTF.clear();
        emailTF.clear();
        phoneTF.clear();
        dateTF.clear();
        passwordTF.clear();
    }

    public void loginAccount(ActionEvent event){
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage=new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root,550,400));
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
