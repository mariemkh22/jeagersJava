package controllers;

import entities.Notification;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceNotification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AfficherNotifications {

    @FXML
    private Label deliveryB;

    @FXML
    private Label messageB;

    @FXML
    private Label productB;

    @FXML
    private Label serviceB;

    @FXML
    private Label userB;

    @FXML
    private TableColumn<Notification, Boolean> selectCol; // New column for checkboxes

    @FXML
    private TableColumn<Notification, String> dateCol;

    @FXML
    private TableColumn<Notification, String> sujetCol;

    @FXML
    private TableColumn<Notification, String> contenueCol;

    @FXML
    private TableView<Notification> tableview;

    @FXML
    private Text sujetTF;

    @FXML
    private CheckBox selectAllCheckBox;
    @FXML
    private ListView<Notification> listView;
    @FXML
    private Label messagerieTF;

    private final ServiceNotification serviceNotification = new ServiceNotification();

    @FXML
    void initialize() {
        try {
            ObservableList<Notification> observableList = FXCollections.observableList(serviceNotification.afficher());
            tableview.setItems(observableList);

            dateCol.setCellValueFactory(new PropertyValueFactory<>("date_envoie"));
            sujetCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));
            contenueCol.setCellValueFactory(new PropertyValueFactory<>("contenue"));

            // Add checkbox to select column
            selectCol.setCellValueFactory(cellData -> {
                Notification notification = cellData.getValue();
                BooleanProperty selected = notification.selectedProperty();
                selected.addListener((obs, oldVal, newVal) -> {
                    notification.setSelected(newVal);
                });
                return selected;
            });
            selectCol.setCellFactory(column -> {
                CheckBoxTableCell<Notification, Boolean> cell = new CheckBoxTableCell<>();
                cell.setSelectedStateCallback(index -> tableview.getItems().get(index).selectedProperty());
                return cell;
            });

            // Enable multiple selection
            tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            // Bind select all checkbox to tableview selection
            selectAllCheckBox.setOnAction(event -> {
                if (selectAllCheckBox.isSelected()) {
                    tableview.getSelectionModel().selectAll();
                } else {
                    tableview.getSelectionModel().clearSelection();
                }
            });

            // Enable/disable select all checkbox based on selection
            selectAllCheckBox.disableProperty().bind(Bindings.isEmpty(tableview.getItems()));

            System.out.println("Nombre de notifications chargés : " + observableList.size());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void NaviguerajoutAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterNotification.fxml"));
            sujetTF.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @FXML
    void deleteMultipleNotifications(ActionEvent event) {
        ObservableList<Notification> selectedNotifications = tableview.getSelectionModel().getSelectedItems();

        if (selectedNotifications.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune notification sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner au moins une notification à supprimer.");
            alert.showAndWait();
        } else {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Supprimer les notifications sélectionnées ?");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer les notifications sélectionnées ?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Create a copy of selected notifications list
                    ObservableList<Notification> selectedNotificationsCopy = FXCollections.observableArrayList(selectedNotifications);

                    // Iterate over the copy and remove each notification from the original list
                    for (Notification notification : selectedNotificationsCopy) {
                        serviceNotification.supprimer(notification.getId());
                        tableview.getItems().remove(notification);
                    }
                    // Clear the selection after removing notifications
                    tableview.getSelectionModel().clearSelection();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Une erreur s'est produite lors de la suppression des notifications.");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void modifierNotificationAction(ActionEvent event) {
        Notification notificationSelectionnee = tableview.getSelectionModel().getSelectedItem();
        if (notificationSelectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune notification sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une notification à modifier.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditNotification.fxml"));
            Parent root = loader.load();
            EditNotification editNotificationController = loader.getController();
            editNotificationController.initData(notificationSelectionnee); // Pass selected notification
            editNotificationController.setAfficherNotificationsController(this); // Pass the AfficherNotifications controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de l'ouverture de la fenêtre de modification");
            alert.setContentText("Une erreur s'est produite lors de l'ouverture de la fenêtre de modification de la notification.");
            alert.showAndWait();
        }
    }


    public void updateNotification(Notification notification) {
        tableview.getItems().set(tableview.getItems().indexOf(notification), notification);
    }

    public void selectAllAction(ActionEvent actionEvent) {
        if (selectAllCheckBox.isSelected()) {
            tableview.getSelectionModel().selectAll();
        } else {
            tableview.getSelectionModel().clearSelection();
        }
    }

    public void sendEmailAction(ActionEvent actionEvent) {
        Notification notificationSelectionnee = tableview.getSelectionModel().getSelectedItem();
        if (notificationSelectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune notification sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une notification pour l'envoi par e-mail.");
            alert.showAndWait();
            return;
        }
        // Création d'une instance de la classe sendEmail
        sendEmailMessage emailSender = new sendEmailMessage();
        // Vous devez remplacer les valeurs fictives par les vraies adresses e-mail, le sujet, le contenu, etc.
        String destinataire = "mariemkhelifi3@gmail.com";
        String sujet = "Notification";
        String contenu = "Contenu de la notification : \n" + notificationSelectionnee.getSujet() + "\n" + notificationSelectionnee.getContenue();
        try {
// Appel de la méthode sendMail avec les informations nécessaires
            emailSender.sendMail(destinataire, sujet, contenu, "Nom du client");


            // Affichage d'un message de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("E-mail envoyé");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("L'e-mail a été envoyé avec succès à " + destinataire);
            confirmationAlert.showAndWait();

        } catch (Exception e) {
            // Gestion des erreurs lors de l'envoi de l'e-mail
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de l'envoi de l'e-mail");
            alert.setContentText("Une erreur s'est produite lors de l'envoi de l'e-mail.");
            alert.showAndWait();
            e.printStackTrace();
        }


    }

    @FXML
    void deliveryManagment(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherLivraison.fxml"));
            deliveryB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void messageManagment(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            messageB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void productManagment(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherProduit.fxml"));
            productB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void serviceManagment(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherService.fxml"));
            serviceB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void userManagment(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            userB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}