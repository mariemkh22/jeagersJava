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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import services.serviceUser;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Feedback {

    @FXML
    private ImageView profileB;

    @FXML
    private HBox homeButton;

    @FXML
    private Button DeliveryB;

    @FXML
    private Button MessageB;

    @FXML
    private Button ProductB;

    @FXML
    private Button ServiceB;

    @FXML
    private Button UserB;

    @FXML
    private TableColumn<User, String> actionShow;

    @FXML
    private Button addB;

    @FXML
    private Button feedbackB;

    @FXML
    private TableColumn<User, String> feedbackShow;

    @FXML
    private TableColumn<User, String> feedbackStatusShow;

    @FXML
    private TableColumn<User, String> feedbackTypeShow;

    @FXML
    private Button homeB;

    @FXML
    private Button homeBDash;

    @FXML
    private Button listB;

    @FXML
    private Label messageLabel;

    @FXML
    private TableColumn<User, String> nameShow;

    @FXML
    private TableView<User> table;

    @FXML
    private ImageView home;

    private final serviceUser px=new serviceUser();

    Connection connection = MyDatabase.getInstance().getConnection();

    @FXML
    void initialize(){
        try {
            List<User> users = px.displayUserFeedback();
            ObservableList<User> observableList = FXCollections.observableList(users);
            table.setItems(observableList);
            nameShow.setCellValueFactory(new PropertyValueFactory<>("full_name"));
            feedbackTypeShow.setCellValueFactory(new PropertyValueFactory<>("feedbackType"));
            feedbackShow.setCellValueFactory(new PropertyValueFactory<>("feedback"));
            feedbackStatusShow.setCellValueFactory(new PropertyValueFactory<>("feedbackStatus"));
            Callback<TableColumn<User,String>, TableCell<User,String>> cellFactory = param -> new TableCell<User,String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        User user = getTableView().getItems().get(getIndex());

                        // Finished status
                        Button Finished = new Button("Finished");
                        String finish = "Done";
                        Finished.setOnAction(event -> {
                            try{
                                String req ="update user set feedbackStatus=? where id=?";

                                PreparedStatement preparedStatement= connection.prepareStatement(req);
                                preparedStatement.setString(1,finish);
                                preparedStatement.setInt(2,user.getId());

                                int rowsAffected = preparedStatement.executeUpdate();
                                if(rowsAffected>0){
                                    messageLabel.setText(user.getFull_name()+" feedback is done !");
                                    clearErrorMessageAfterDelay();
                                    refreshTableData();
                                }
                            }catch (Exception e){
                                throw new RuntimeException(e);
                            }

                        });

                        // In process status
                        Button Process = new Button("In Process");
                        String process = "In the process";
                        Process.setOnAction(event -> {
                            try{
                                String req ="update user set feedbackStatus=? where id=?";

                                PreparedStatement preparedStatement= connection.prepareStatement(req);
                                preparedStatement.setString(1,process);
                                preparedStatement.setInt(2,user.getId());

                                int rowsAffected = preparedStatement.executeUpdate();
                                if(rowsAffected>0){
                                    messageLabel.setText(user.getFull_name()+" feedback is in the process !");
                                    clearErrorMessageAfterDelay();
                                    refreshTableData();
                                }
                            }catch (Exception e){
                                throw new RuntimeException(e);
                            }

                        });

                        // Create an HBox to hold both buttons
                        HBox buttonBox = new HBox(Process, Finished);
                        buttonBox.setSpacing(10);

                        setGraphic(buttonBox);
                        setText(null);
                    }
                }
            };
            actionShow.setCellFactory(cellFactory);
        } catch (SQLException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void refreshTableData() {
        try {
            List<User> users = px.displayUserFeedback();
            ObservableList<User> observableList = FXCollections.observableList(users);
            table.setItems(observableList);
        } catch (SQLException e) {
            messageLabel.setText("Data not refreshed !");
        }
    }

    @FXML
    void DeliveryButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherLivraison.fxml"));
            DeliveryB.getScene().setRoot(root);
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
    void MessagingButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            MessageB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ProductManagmentButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherProduit.fxml"));
            ProductB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ServiceManagmentButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherService.fxml"));
            ServiceB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
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
            addB.getScene().setRoot(root);
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
    void profileButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void clearErrorMessageAfterDelay() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> messageLabel.setText("")));
        timeline.play(); // Start the timeline
    }
}
