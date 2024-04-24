package controllers;

import Entity.Livraison;
import Entity.LocalisationGeographique;
import Services.Servicelivraison;
import Services.Servicelocation;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.List;

public class ModifierLivraisonController {

    @FXML
    private ComboBox<String> EntrpriseTf;

    @FXML
    private ComboBox<Integer> FeeTf;

    @FXML
    private ComboBox<String> RegionTf;

    @FXML
    private ComboBox<String> StatusTf1;

    @FXML
    private DatePicker firstdate;

    @FXML
    private DatePicker lastdate;


    private  Livraison livraison; // Instance de Livraison
    private final Servicelocation sl = new Servicelocation(); // Instance du service de localisation
    private final Servicelivraison slv = new Servicelivraison(); // Instance du service de localisation


    // Dans la méthode initData de votre contrôleur ModifierLivraisonController
    public void initData(Livraison livraison) {
        this.livraison = livraison;

        if (livraison != null) {
            EntrpriseTf.setValue(livraison.getEntreprise());
            FeeTf.setValue(livraison.getFrais());
            RegionTf.setValue(livraison.getLocalisationGeographique().getRegion());
            StatusTf1.setValue(livraison.getStatus());

            // Convertir java.sql.Date en LocalDate
            Date startDate = (Date) livraison.getDate_debut();
            if (startDate != null) {
                LocalDate startLocalDate = startDate.toLocalDate();
                firstdate.setValue(startLocalDate);
            }

            Date endDate = (Date) livraison.getDate_fin();
            if (endDate != null) {
                LocalDate endLocalDate = endDate.toLocalDate();
                lastdate.setValue(endLocalDate);
            }
        }
    }
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
    void SaveUpdateDeliveryBtn(ActionEvent event) {
        // Vérifier si tous les champs obligatoires sont remplis
        if (validateFields()) {
            LocalDate startDate = firstdate.getValue();
            LocalDate endDate = lastdate.getValue();

            // Vérifier si la date de fin est antérieure à la date de début
            if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "La date de fin ne peut pas être antérieure à la date de début.");
                return; // Sortir de la méthode si la validation échoue
            }

            // Mettre à jour les propriétés de la livraison avec les valeurs des champs
            livraison.setEntreprise(EntrpriseTf.getValue());
            livraison.setFrais(FeeTf.getValue());
            livraison.getLocalisationGeographique().setRegion(RegionTf.getValue());
            livraison.setStatus(StatusTf1.getValue());

            // Convertir les valeurs des DatePicker en objets Date SQL
            if (startDate != null) {
                Date startDateSql = Date.valueOf(startDate);
                livraison.setDate_debut(startDateSql);
            }
            if (endDate != null) {
                Date endDateSql = Date.valueOf(endDate);
                livraison.setDate_fin(endDateSql);
            }

            // Appeler la méthode de mise à jour de livraison dans le service de livraison
            try {
                slv.modifier(livraison);
                // Afficher un message de succès
                showAlert(Alert.AlertType.INFORMATION, "Livraison mise à jour", "La livraison a été mise à jour avec succès.");
            } catch (SQLException e) {
                // Afficher un message d'erreur en cas d'échec de la mise à jour
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la mise à jour de la livraison.");
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur si tous les champs ne sont pas remplis
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "Veuillez remplir tous les champs obligatoires.");
        }
    }
    // Méthode pour valider si tous les champs sont remplis
    private boolean validateFields() {
        return EntrpriseTf.getValue() != null &&
                FeeTf.getValue() != null &&
                RegionTf.getValue() != null &&
                StatusTf1.getValue() != null;
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void ShowlistDeliveryBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLivraison.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Obtenir la scène de l'événement actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Remplacer la scène actuelle par la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception s'il y a une erreur de chargement du fichier FXML
        }
    }

    @FXML
    void firstdate(ActionEvent event) {
        // Cette méthode est déclenchée lorsque la date du premier DatePicker est modifiée
        LocalDate selectedDate = firstdate.getValue();
        System.out.println("Nouvelle date sélectionnée pour la première date : " + selectedDate);
        // Vous pouvez ajouter ici toute logique supplémentaire que vous souhaitez exécuter lorsque la date est modifiée.
    }

    @FXML
    void lastdate(ActionEvent event) {
        // Cette méthode est déclenchée lorsque la date du deuxième DatePicker est modifiée
        LocalDate selectedDate = lastdate.getValue();
        System.out.println("Nouvelle date sélectionnée pour la dernière date : " + selectedDate);
        // Vous pouvez ajouter ici toute logique supplémentaire que vous souhaitez exécuter lorsque la date est modifiée.
    }

}
