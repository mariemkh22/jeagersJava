package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ProfileAdmin {

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
    private Button logoutB;

    @FXML
    private Button profileB;

    @FXML
    void initialize(){
        editnameTF.setText(Login.getCurrentUser().getFull_name());
        editemailTF.setText(Login.getCurrentUser().getEmail());
        editphoneTF.setText(Login.getCurrentUser().getPhone_number());
        editdateTF.setText(Login.getCurrentUser().getDate_of_birth());
    }

    @FXML
    void editButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdminEdit.fxml"));
            editB.getScene().setRoot(root);
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

    public void profileButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
