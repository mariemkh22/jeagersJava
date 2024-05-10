package controllers;

import entities.Commande;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Stage;
import services.ServiceCommande;
import services.ServiceProduit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AfficherCommandeBack {

    @FXML
    private Label userB;

    @FXML
    private ImageView profileB;

    @FXML
    private Label productB;

    @FXML
    private ImageView messageB;

    @FXML
    private ImageView serviceB;

    @FXML
    private Label deliveryB;

    @FXML
    private Button AjoutCmdbtn;

    @FXML
    private Button ClearCmdbtn;

    @FXML
    private TableColumn<Commande, Date> DateCmdCol;

    @FXML
    private TableView<Commande> ListTF;

    @FXML
    private TableColumn<Commande, String> MethodLivCol;

    @FXML
    private Button ModifierCmdbtn;

    @FXML
    private TableColumn<Commande, String> NomProduitCol;

    @FXML
    private Button SupprimerCmdbtn;

    @FXML
    private TableColumn<Commande, String> VilleCmdCol;

    @FXML
    private ImageView telechargerPDFImage;

    private final ServiceCommande sc = new ServiceCommande();
    private final ServiceProduit sp = new ServiceProduit();
    private final PDFGenerator pdfGenerator = new PDFGenerator();

    @FXML
    public void initialize() {
        populateCmdTable();
        telechargerPDFImage.setOnMouseClicked(event -> telechargerPDF(event));
    }

    private void populateCmdTable() {
        try {
            List<Commande> cmds = sc.afficher();
            ListTF.setItems(FXCollections.observableArrayList(cmds));
            NomProduitCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getNomProduit()));
            DateCmdCol.setCellValueFactory(new PropertyValueFactory<>("dateCmd"));
            MethodLivCol.setCellValueFactory(new PropertyValueFactory<>("methode_livraison"));
            VilleCmdCol.setCellValueFactory(new PropertyValueFactory<>("ville"));

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Fetching Deliveries");
            alert.setContentText("An error occurred while fetching deliveries from the database.");
            alert.showAndWait();
        }
    }

    @FXML
    void ClearCmdbtn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to delete all orders?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                sc.deleteAllC();
                ListTF.getItems().clear();
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setTitle("Success");
                confirmation.setHeaderText(null);
                confirmation.setContentText("All orders deleted successfully!");
                confirmation.showAndWait();
            } catch (SQLException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText(null);
                error.setContentText("Error deleting orders: " + e.getMessage());
                error.showAndWait();
            }
        }
    }

    @FXML
    void SupprimerCmdbtn(ActionEvent event) {
        Commande selectedCommande = ListTF.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Cancel Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Cancel This Order ?");

            ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(deleteButton, cancelButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == deleteButton) {
                ListTF.getItems().remove(selectedCommande);
                try {
                    sc.supprimer(selectedCommande.getId());
                    confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Order Canceled");
                    confirmationAlert.showAndWait();
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setContentText("Error while Cancel : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select Order to cancel.");
            alert.showAndWait();
        }
    }

    @FXML
    void telechargerPDF(javafx.scene.input.MouseEvent event) {
        try {
            List<Commande> cmds = sc.afficher();
            pdfGenerator.generateCmdPDF(cmds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void StatB(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PieChart.fxml"));
            Parent root = loader.load();

            // Obtenez la fenêtre actuelle à partir de l'image cliquée
            Stage currentStage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();

            // Changez la scène de la fenêtre actuelle
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void telechargerPDF1(javafx.scene.input.MouseEvent event) {
        try {
            List<Commande> cmds = sc.afficher();
            pdfGenerator.generateCmdPDF(cmds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void userManagment(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            userB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void profileButton(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profileAdmin.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void messageButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherNotifications.fxml"));
            messageB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void productButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherProduit.fxml"));
            productB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void serviceButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherService.fxml"));
            serviceB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deliveryButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherLivraison.fxml"));
            deliveryB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}

