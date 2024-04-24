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
    private TableView<User> table;

    @FXML
    private Label labelErrorMessage;

    private final serviceUser px = new serviceUser();

    @FXML
    void initialize(){
        try {
            List<User> users = px.displayUser();
            ObservableList<User> observableList = FXCollections.observableList(users);
            table.setItems(observableList);
            IdShow.setCellValueFactory(new PropertyValueFactory<>("id"));
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
    void DeliveryButton(ActionEvent event) {

    }

    @FXML
    void HomeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
            homeB.getScene().setRoot(root);
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
            if (addemailTF.getText().isBlank() == true || addpassTF.getText().isBlank() == true || addnameTF.getText().isBlank() == true || addphoneTF.getText().isBlank() == true || adddateTF.getText().isBlank() == true){
                labelErrorMessage.setText("Please fill the form.");
                clearErrorMessageAfterDelay();
            }
            else {
                String role = "[\"ROLE_USER\"]";
                String pass = hashPassword(addpassTF.getText());
                px.addUser(new User(addemailTF.getText(),pass,role,addnameTF.getText(),addphoneTF.getText(),adddateTF.getText()));
                initialize();
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
    void listButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            homeBDash.getScene().setRoot(root);
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
}
