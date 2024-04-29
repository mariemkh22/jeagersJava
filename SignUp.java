package controllers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
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

import java.io.IOException;
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

    @FXML
    private Button loginB;

    private final serviceUser px = new serviceUser();

    @FXML
    void signupButton(ActionEvent event) {
        try{
            if (emailTF.getText().isBlank() == true && passTF.getText().isBlank() == true && fullNameTF.getText().isBlank() == true && phoneNumberTF.getText().isBlank() == true && DateTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please fill the form.");
                clearErrorMessageAfterDelay();
            }
            else if (fullNameTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please enter your full name.");
                clearErrorMessageAfterDelay();
            }
            else if (emailTF.getText().isBlank()) {
                labelErrorMessage.setText("Please enter a valid email address.");
                clearErrorMessageAfterDelay();
            }
            else if (!emailTF.getText().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
                labelErrorMessage.setText("Please enter a valid email address.");
                clearErrorMessageAfterDelay();
            }
            else if (phoneNumberTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please enter a valid phone number.");
                clearErrorMessageAfterDelay();
            }
            else if (phoneNumberTF.getText().length()<8){
                labelErrorMessage.setText("Phone number should be at least 8 digits.");
                clearErrorMessageAfterDelay();
            }
            else if (DateTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please enter a valid date of birth.");
                clearErrorMessageAfterDelay();
            }
            else if (passTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please enter a valid password.");
                clearErrorMessageAfterDelay();
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
            stage.setScene(new Scene(root,569,400));
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void clearErrorMessageAfterDelay() {
        // Set a timeline to clear the label after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> labelErrorMessage.setText("")));
        timeline.play(); // Start the timeline
    }

    @FXML
    void loginButton(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage=new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root,569,400));
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
