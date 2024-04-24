package controllers;

import utils.myDatabase;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.serviceUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DisplayBackEnd {

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
    private TableColumn<User, String> dateShow;

    @FXML
    private Button deleteB;

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
    private TableView<User> table;

    int id =0;

    private final serviceUser px=new serviceUser();

    Connection connection = myDatabase.getInstance().getConnection();


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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendAddUser.fxml"));
            homeBDash.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
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
            listB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getData(MouseEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        id = user.getId();
        editnameTF.setText(user.getFull_name());
        editemailTF.setText(user.getEmail());
        editphoneTF.setText(user.getPhone_number());
        editdateTF.setText(user.getDate_of_birth());
    }

    @FXML
    void editButton(ActionEvent event) {
        String req ="update user set email=?,full_name=?,phone_number=?,date_of_birth=? where id=?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, editemailTF.getText());
            preparedStatement.setString(2, editnameTF.getText());
            preparedStatement.setString(3, editphoneTF.getText());
            preparedStatement.setString(4, editdateTF.getText());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            initialize();
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @FXML
    void deleteButton(ActionEvent event) {
        try {
            User p = table.getSelectionModel().getSelectedItem();
            px.deleteUser(p.getId());
            initialize();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
