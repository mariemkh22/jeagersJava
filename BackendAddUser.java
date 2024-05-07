package controllers;

import entities.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import services.serviceUser;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

public class BackendAddUser {

    @FXML
    private ImageView home;

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
    private Button addB;

    @FXML
    private TextField adddateTF;

    @FXML
    private TextField addemailTF;

    @FXML
    private TextField addnameTF;

    @FXML
    private TextField addphoneTF;

    @FXML
    private PasswordField addpassTF;

    @FXML
    private TableColumn<User, String> dateShow;

    @FXML
    private TableColumn<User, String> emailShow;

    @FXML
    private Button homeB;

    @FXML
    private Button homeBDash;

    @FXML
    private Button listB;

    @FXML
    private TableColumn<User, String> nameShow;

    @FXML
    private TableColumn<User, String> phoneShow;

    @FXML
    private Button resetB;

    @FXML
    private Button feedbackB;

    @FXML
    private Label labelErrorMessage;

    private final serviceUser px = new serviceUser();



    @FXML
    void DeliveryButton(ActionEvent event) {

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
    void MessagingButton(ActionEvent event) {

    }

    @FXML
    void ProductManagmentButton(ActionEvent event) {

    }

    @FXML
    void ServiceManagmentButton(ActionEvent event) {

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
        try{
            if (addemailTF.getText().isBlank() == true && addpassTF.getText().isBlank() == true && addnameTF.getText().isBlank() == true && addphoneTF.getText().isBlank() == true && adddateTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please fill the form.");
                clearErrorMessageAfterDelay();
            }
            else if (addnameTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please enter your full name.");
                clearErrorMessageAfterDelay();
            }
            else if (addemailTF.getText().isBlank() || !addemailTF.getText().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
                labelErrorMessage.setText("Please enter a valid email address.");
                clearErrorMessageAfterDelay();
            }
            else if (addphoneTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please enter a valid phone number.");
                clearErrorMessageAfterDelay();
            }
            else if (addphoneTF.getText().length() <8 ){
                labelErrorMessage.setText("Phone number should be at least 8 digits.");
                clearErrorMessageAfterDelay();
            }
            else if (adddateTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please enter a valid date of birth.");
                clearErrorMessageAfterDelay();
            }
            else if (addpassTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please enter a valid password.");
                clearErrorMessageAfterDelay();
            }
            else {
                String role = "[\"ROLE_USER\"]";
                String pass = hashPassword(addpassTF.getText());
                px.addUser(new User(addemailTF.getText(),pass,role,addnameTF.getText(),addphoneTF.getText(),adddateTF.getText()));
                labelErrorMessage.setText("Account added !");
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
    void homeButton(ActionEvent event) {
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
    void listButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            listB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void resetButton(ActionEvent event) {
        addnameTF.clear();
        adddateTF.clear();
        addphoneTF.clear();
        addemailTF.clear();
        addpassTF.clear();
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

    private void clearErrorMessageAfterDelay() {
        // Set a timeline to clear the label after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> labelErrorMessage.setText("")));
        timeline.play(); // Start the timeline
    }

    @FXML
    void profileButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
            UserB.getScene().setRoot(root);
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
