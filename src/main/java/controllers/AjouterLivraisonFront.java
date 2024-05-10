package controllers;

import entities.Livraison;
import entities.LocalisationGeographique;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.Servicelivraison;
import services.Servicelocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
public class AjouterLivraisonFront {

    @FXML
    private ImageView homeB;

    @FXML
    private ComboBox<String> EntrpriseTf;

    @FXML
    private ComboBox<Integer> FeeTf;

    @FXML
    private ComboBox<String> RegionTf;

    @FXML
    private Button Ratingbtn;

    @FXML
    private ComboBox<String> StatusTf1;

    @FXML
    private DatePicker firstdate;

    @FXML
    private DatePicker lastdate;
    @FXML
    private Label destinationtf;

    @FXML
    private Label entreprisetfcont;

    @FXML
    private Label feetfcont;

    @FXML
    private Label firstdatetf;

    @FXML
    private Label lastdatetf;

    @FXML
    private Label statustfcont;
    @FXML
    private Button Discountbtn;
    @FXML
    private Button Remboursementbtn;
    @FXML
    private Button addlocationbtn;

    private final Servicelocation sl = new Servicelocation(); // Instance du service de localisation
    private final Servicelivraison serviceLivraison = new Servicelivraison(); // Instance du service de livraison

    @FXML
    void initialize() {
        // Appeler la méthode pour peupler le ComboBox avec les régions disponibles
        populateRegionComboBox();
        // Définir les options pour le ComboBox EntrpriseTf
        ObservableList<String> entrepriseOptions = FXCollections.observableArrayList("ARAMEX", "FedEx", "ADEX");
        EntrpriseTf.setItems(entrepriseOptions);

        // Définir les options pour le ComboBox FeeTf
        ObservableList<Integer> feeOptions = FXCollections.observableArrayList(1, 3, 7); // J'ai supposé que 1 correspond à 1Day, 3 à 3Days et 7 à 1Week
        FeeTf.setItems(feeOptions);

        // Définir les options pour le ComboBox StatusTf1
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Completed", "Pending", "Non completed");
        StatusTf1.setItems(statusOptions);
    }

    private void populateRegionComboBox() {
        try {
            // Récupérer la liste des localisations géographiques
            List<LocalisationGeographique> locations = sl.afficherLocation();

            // Créer une liste observable pour stocker les régions
            ObservableList<String> regionsList = FXCollections.observableArrayList();

            // Parcourir la liste des localisations pour extraire les régions
            for (LocalisationGeographique location : locations) {
                regionsList.add(location.getRegion());
            }

            // Ajouter les régions à ComboBox
            RegionTf.setItems(regionsList);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception si une erreur SQL se produit
        }
    }

    @FXML
    void SaveDeliveryBtn(ActionEvent event) {
        try {
            // Récupérer les valeurs sélectionnées dans les ComboBox et les DatePicker
            String entreprise = EntrpriseTf.getValue();
            Integer frais = FeeTf.getValue(); // Utiliser Integer pour permettre la valeur null
            String region = RegionTf.getValue();
            String status = StatusTf1.getValue();
            LocalDate startDate = firstdate.getValue();
            LocalDate endDate = lastdate.getValue();


            // Vérifier si toutes les valeurs ont été sélectionnées
            boolean isValid = true; // Indicateur de validation
            StringBuilder errorMessage = new StringBuilder();

            if (entreprise == null || entreprise.isEmpty()) {
                entreprisetfcont.setText("Choose your entreprise");
                isValid = false;
            } else {
                entreprisetfcont.setText("");
            }

            if (frais == null) {
                feetfcont.setText("Choose level of service");
                isValid = false;
            } else {
                feetfcont.setText("");
            }

            if (region == null || region.isEmpty()) {
                destinationtf.setText("Choose your region");
                isValid = false;
            } else {
                destinationtf.setText("");
            }

            if (status == null || status.isEmpty()) {
                statustfcont.setText("Choose the status");
                isValid = false;
            } else {
                statustfcont.setText("");
            }

            if (startDate == null) {
                firstdatetf.setText("choose first date");
                isValid = false;
            } else {
                firstdatetf.setText("");
            }

            if (endDate == null) {
                lastdatetf.setText("choose last date");
                isValid = false;
            } else {
                lastdatetf.setText("");
            }
            // Vérifier si la date de fin est antérieure à la date de début
            if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("last date invalide");
                alert.setContentText("last date must be after start date.");
                alert.showAndWait();
                return;
            }


            if (!isValid) {
                // Afficher une alerte si toutes les valeurs ne sont pas sélectionnées
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("fields invalide");
                alert.setContentText("please fill all the fields correctly.");
                alert.showAndWait();
                return;
            }

            // Convertir les dates sélectionnées en objets Date
            Date start = Date.valueOf(startDate);
            Date end = Date.valueOf(endDate);

            // Enregistrer la livraison avec les valeurs récupérées
            Livraison livraison = new Livraison(start, end, entreprise, frais, status, new LocalisationGeographique(region, 0, null));
            serviceLivraison.ajouter(livraison);

            // Afficher une alerte de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("La livraison a été enregistrée avec succès.");
            alert.showAndWait();

        } catch (SQLException e) {
            // Gérer les exceptions liées à l'enregistrement de la livraison
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'enregistrement de la livraison : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void firstdate(ActionEvent event) {
        // Exemple : afficher la date sélectionnée dans la console
        LocalDate selectedDate = firstdate.getValue();
        System.out.println("First Date Selected: " + selectedDate);
    }

    @FXML
    void lastdate(ActionEvent event) {

        // Exemple : afficher la date sélectionnée dans la console
        LocalDate selectedDate = lastdate.getValue();
        System.out.println("Last Date Selected: " + selectedDate);
    }
    @FXML
    void paybtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketPayment.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);

            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Discountbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DiscountLivraison.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Remboursementbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Remboursement.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Ratingbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Rating.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void addlocationbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLocationFront.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void homeButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
