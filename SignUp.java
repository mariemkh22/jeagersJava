package controllers;
import javafx.util.converter.LongStringConverter;
import org.example.MainFX;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.serviceUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

public class SignUp {

    @FXML
    private TextField DateTF;

    @FXML
    private ImageView brandingImageViewL;

    @FXML
    private ImageView brandingImageViewR;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField fullNameTF;

    @FXML
    private Label labelErrorMessage;

    @FXML
    private PasswordField passTF;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private Button resetB;

    @FXML
    private Button signUpB;

    private final serviceUser px = new serviceUser();

    @FXML
    void signupButton(ActionEvent event) {
        try{
            if (emailTF.getText().isBlank() == true || passTF.getText().isBlank() == true || fullNameTF.getText().isBlank() == true || phoneNumberTF.getText().isBlank() == true || DateTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please fill the form.");
            }
            else {
                String role = "[\"ROLE_USER\"]";
                String pass = hashPassword(passTF.getText());
                px.addUser(new User(emailTF.getText(),pass,role,fullNameTF.getText(),phoneNumberTF.getText(),DateTF.getText()));
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
    void resetButton(ActionEvent event) {
        fullNameTF.clear();
        emailTF.clear();
        phoneNumberTF.clear();
        DateTF.clear();
        passTF.clear();
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
