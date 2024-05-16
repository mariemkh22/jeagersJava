package controllers;

import entities.Commande;
import entities.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceCommande;
import services.ServiceProduit;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AjouterCommandeFront {

    @FXML
    private Button afficherB;

    @FXML
    private Button addProductB;

    @FXML
    private Button commandB;

    @FXML
    private Button homeB;

    @FXML
    private Button productB;

    @FXML
    private Button stat;

    @FXML
    private DatePicker DateCommdTf;
    @FXML
    private ComboBox<String> MethodelivTF;

    @FXML
    private ComboBox<String> VilleTF;

    @FXML
    private Label nomProduitLabel;

    @FXML
    private ImageView profileB;

    public void setProductName(String productName) {
        nomProduitLabel.setText(productName);
    }

    private final ServiceCommande sc = new ServiceCommande();
    private final ServiceProduit sp = new ServiceProduit();

    @FXML
    void initialize() {
        NameProductLabel();

        ObservableList<String> MethodeLivOptions = FXCollections.observableArrayList("Express", "Regular");
        MethodelivTF.setItems(MethodeLivOptions);

        ObservableList<String> VilleOptions = FXCollections.observableArrayList(
                "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès",
                "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili",
                "Le Kef", "Mahdia", "La Manouba", "Médenine", "Monastir",
                "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse",
                "Tataouine", "Tozeur", "Tunis", "Zaghouan"
        );

        VilleTF.setItems(VilleOptions);

    }

    private void NameProductLabel() {
        try {
            List<Produit> produits = sp.afficher();

            if (!produits.isEmpty()) {
                Produit produit = produits.get(0); // Sélectionnez le premier produit par défaut
                nomProduitLabel.setText(produit.getNomProduit());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AjoutCmdbtn(ActionEvent event) {
        try {
            // Load the CAPTCHA dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/captcha.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Check if CAPTCHA was solved correctly
            CaptchaController captchaController = loader.getController();
            if (captchaController.isRotationCorrect()) {
                // Proceed with adding the command
                // Check if a date is selected
                LocalDate datecmd = DateCommdTf.getValue();
                if (datecmd == null) {
                    // Highlight the DatePicker border if no date is selected
                    DateCommdTf.setStyle("-fx-border-color: red;");
                    // Show an error alert indicating that the date is required
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please select a date.");
                    alert.showAndWait();
                    // Exit the method without adding the command
                    return;
                }

                // If a date is selected, continue with adding the command
                // Reset the DatePicker border style
                DateCommdTf.setStyle(null);

                String Ville = VilleTF.getValue();
                String nomprod = nomProduitLabel.getText(); // Use the product name from the Label
                String methodeLiv = MethodelivTF.getValue();

                Date datecommande = Date.valueOf(datecmd);

                Commande commande = new Commande(datecommande, methodeLiv, Ville, new Produit(nomprod, null, null, 0));
                sc.ajouter(commande);
                try {
                    Parent pass = FXMLLoader.load(getClass().getResource("/AjouterLivraisonFront.fxml"));
                    addProductB.getScene().setRoot(pass);
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            } else {
                // Handle CAPTCHA not solved correctly
                // For example, display an error message to the user
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while Save : " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void DateCommdTf(ActionEvent event) {
        LocalDate selectedDate = DateCommdTf.getValue();
        System.out.println("Date sélectionnée : " + selectedDate);
    }

    public void profileButton(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addProductButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterProduitFront.fxml"));
            addProductB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void commandButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCommandeFront.fxml"));
            commandB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void homeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void productButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cardListView.fxml"));
            productB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void statisticsButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/PieChart.fxml"));
            stat.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
