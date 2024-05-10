package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.*;

public class ProfileAdmin {

    @FXML
    private ImageView home;

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

    Connection connection = MyDatabase.getInstance().getConnection();

    @FXML
    void initialize() throws SQLException {
        try {
            // SQL query to fetch user information by ID
            String req = "SELECT * FROM user WHERE id=?";

            // Prepare the statement with the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(req);

            // Set the parameter for the user ID (assuming you have stored the user ID somewhere)
            preparedStatement.setInt(1, Login.getCurrentUser().getId());

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();

            // Check if there is a result
            if (rs.next()) {
                // Create a new User object and set its properties from the result set
                User user = new User();
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setDate_of_birth(rs.getString("date_of_birth"));
                user.setId(rs.getInt("id"));

                // Set the text fields with the user information
                editnameTF.setText(user.getFull_name());
                editemailTF.setText(user.getEmail());
                editphoneTF.setText(user.getPhone_number());
                editdateTF.setText(user.getDate_of_birth());
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
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
    void HomeButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
            home.getScene().setRoot(root);
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

    public void updateUserInfo(User updatedUser) {
        editnameTF.setText(updatedUser.getFull_name());
        editemailTF.setText(updatedUser.getEmail());
        editphoneTF.setText(updatedUser.getPhone_number());
        editdateTF.setText(updatedUser.getDate_of_birth());
    }
}
