package controllers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import services.serviceUser;
import utils.myDatabase;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

public class Login {

    @FXML
    private ImageView brandingImageViewL;

    @FXML
    private ImageView brandingImageViewR;

    @FXML
    private Button cancelB;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button loginB;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private PasswordField passwordTextField;
    private final serviceUser px = new serviceUser();

    private static User currentUser = null;

    @FXML
    void cancelButton(ActionEvent event) {
        emailTextField.clear();
        passwordTextField.clear();
    }

    @FXML
    void loginButton(ActionEvent event) {
        if(emailTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false){
            validateLogin();
            if (currentUser.getId()!=0){
                try {
                    Parent root= FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
                    Stage stage=new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(new Scene(root,1550,820));
                    stage.show();
                    ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        } else {
            loginMessageLabel.setText("Please try again.");
            clearErrorMessageAfterDelay();
        }
    }

    public void validateLogin() {
        myDatabase database = myDatabase.getInstance();
        Connection connection = database.getConnection();

        String verifyLogin = "SELECT * FROM user WHERE email = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(verifyLogin);
            statement.setString(1, emailTextField.getText());
            ResultSet queryResult = statement.executeQuery();

            if (queryResult.next()) {
                String hashedPasswordFromDB = queryResult.getString("password");
                String hashedInputPassword = hashPassword(passwordTextField.getText());

                if (hashedInputPassword.equals(hashedPasswordFromDB)) {
                    // Set the current user
                    currentUser = new User(
                            queryResult.getInt("id"),
                            queryResult.getString("email"),
                            queryResult.getString("full_name"),
                            queryResult.getString("phone_number"),
                            queryResult.getString("date_of_birth")
                    );
                    System.out.println("Login successful!");
                    return; // Exit method after successful login
                }
            }
            currentUser = null;
            loginMessageLabel.setText("Invalid email or password.");
            clearErrorMessageAfterDelay();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static User getCurrentUser() {
        return currentUser;
    }

    private void clearErrorMessageAfterDelay() {
        // Set a timeline to clear the label after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> loginMessageLabel.setText("")));
        timeline.play(); // Start the timeline
    }

}
