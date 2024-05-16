package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import utils.MyDatabase;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisplayBackEnd {

    @FXML
    private ImageView profileB;

    @FXML
    private ImageView home;

    @FXML
    private Button DeliveryB;

    @FXML
    private TableColumn actionShow;
    @FXML
    private TableColumn deleteShow;

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
    private TableColumn<User, Boolean> statusShow;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<User> table;

    @FXML
    private Button feedbackB;

    int id =0;

    private final serviceUser px=new serviceUser();

    Connection connection = MyDatabase.getInstance().getConnection();

    ObservableList<User> userList;


    @FXML
    void initialize(){
        try {
            List<User> users = px.displayUser();
            ObservableList<User> observableList = FXCollections.observableList(users);
            table.setItems(observableList);
            nameShow.setCellValueFactory(new PropertyValueFactory<>("full_name"));
            emailShow.setCellValueFactory(new PropertyValueFactory<>("email"));
            phoneShow.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
            dateShow.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));
            statusShow.setCellValueFactory(new PropertyValueFactory<>("enabled"));
            Callback<TableColumn<User,String>, TableCell<User,String>> cellFactory = param -> new TableCell<User,String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        User user = getTableView().getItems().get(getIndex());

                        // Edit button
                        Button editButton = new Button("Edit");
                        editButton.setOnAction(event -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/backendEdit.fxml"));
                                Parent root = loader.load();
                                BackendEdit backendEdit = loader.getController();
                                backendEdit.initData(user);
                                homeBDash.getScene().setRoot(root);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        // Delete button
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(event -> {
                            try {
                                px.deleteUser(user.getId());
                                initialize();
                                messageLabel.setText(user.getFull_name() + " has been deleted !");
                                clearErrorMessageAfterDelay();
                                if (user.getId() == Login.getCurrentUser().getId()) {
                                    px.deleteUser(user.getId());
                                    try {
                                        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
                                        Stage stage = new Stage();
                                        stage.initStyle(StageStyle.UNDECORATED);
                                        stage.setScene(new Scene(root, 569, 400));
                                        stage.show();
                                        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                                        Login.clearUserSession();
                                        if (Login.getCurrentUser() == null) {
                                            System.out.println("disconnected!");
                                        }
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getCause();
                            }
                        });

                        // Block/Unblock button
                        Button blockButton = new Button(user.isEnabled() ? "Unblock" : "Block");
                        blockButton.setOnAction(event -> {
                            try {
                                boolean newStatus = !user.isEnabled();
                                String req = "update user set enabled=? where id=?";
                                PreparedStatement preparedStatement = connection.prepareStatement(req);
                                preparedStatement.setBoolean(1, newStatus);
                                preparedStatement.setInt(2, user.getId());
                                int rowsAffected = preparedStatement.executeUpdate();
                                if (rowsAffected > 0) {
                                    String action = newStatus ? "unblocked" : "blocked";
                                    messageLabel.setText(user.getFull_name() + " has been " + action + " !");
                                    clearErrorMessageAfterDelay();
                                    refreshTableData();
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });

                        HBox buttonBox = new HBox(editButton, deleteButton, blockButton);
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

    @FXML
    void homeButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
            home.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void refreshTableData() {
        try {
            List<User> users = px.displayUser();
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
