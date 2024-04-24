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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import services.ServiceCommande;
import services.ServiceProduit;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AjouterCommande {

    @FXML
    private DatePicker DateCommdTf;
    @FXML
    private ComboBox<String> MethodelivTF;

    @FXML
    private ComboBox<String> NomProduitTf;


    @FXML
    private ComboBox<String> VilleTF;
    private final ServiceCommande sc = new ServiceCommande();
    private final ServiceProduit sp = new ServiceProduit();


    @FXML
    void Affichercmdbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommande.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);

            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void initialize() {
        NameProductComboBox();

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

    private void NameProductComboBox() {
        try {
            List<Produit> produits = sp.afficher();

            ObservableList<String> productList = FXCollections.observableArrayList();

            for (Produit produit : produits) {
                productList.add(produit.getNomProduit());
            }

            NomProduitTf.setItems(productList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void AjoutCmdbtn(ActionEvent event) {
        try {
            // Vérifier si une date a été sélectionnée
            LocalDate datecmd = DateCommdTf.getValue();
            if (datecmd == null) {
                // Mettre en rouge le cadre du DatePicker si aucune date n'est sélectionnée
                DateCommdTf.setStyle("-fx-border-color: red;");
                // Afficher une alerte indiquant que la date est obligatoire
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select a date.");
                alert.showAndWait();
                // Sortir de la méthode sans ajouter la commande
                return;
            }

            // Si une date a été sélectionnée, continuer avec l'ajout de la commande
            // Réinitialiser le style du cadre du DatePicker
            DateCommdTf.setStyle(null);

            String Ville = VilleTF.getValue();
            String nomprod = NomProduitTf.getValue();
            String methodeLiv = MethodelivTF.getValue();

            Date datecommande = Date.valueOf(datecmd);

            Commande commande = new Commande(datecommande, methodeLiv, Ville, new Produit(nomprod, null, null, 0));
            sc.ajouter(commande);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Order added successfully.");
            alert.showAndWait();
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

}
