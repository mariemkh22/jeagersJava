package controllers;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.categorie_service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceCategorie;

public class AfficherCategorieController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<categorie_service, String> descriptionCol;

    @FXML
    private TableColumn<categorie_service, String> nameCol;
    @FXML
    private TableView<categorie_service> tableview;
    @FXML
    private Label welcomeLBL;

    private final ServiceCategorie sc = new ServiceCategorie();
    ObservableList<categorie_service> observableList;



    @FXML
    void initialize() {

        try {
            List<categorie_service> categoryList = sc.afficher();

          observableList = FXCollections.observableList(categoryList);

            tableview.setItems(observableList);

            nameCol.setCellValueFactory(new PropertyValueFactory<>("name_c"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description_c"));


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    @FXML
    void supprimerCategorie(ActionEvent event) {
        categorie_service selectedCategorie = tableview.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            try {
                sc.supprimer(selectedCategorie.getId());
                observableList.remove(selectedCategorie);
                // Afficher une boîte de dialogue de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("The category was successfully deleted");
                alert.showAndWait();
            } catch (SQLException e) {
                // En cas d'erreur, afficher une boîte de dialogue d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occured while deleting the category : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Si aucune categorie n'est sélectionné, afficher une boîte de dialogue d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a category to delete.");
            alert.showAndWait();
        }
    }
    //// naviguer vers l'interface de mise à jour////
    @FXML
    void navigatetoupdate(ActionEvent event) {
        categorie_service selectedCategorie = tableview.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCategorie.fxml"));
                Parent root = loader.load();

                ModifierCategorie ModifierCategorie = loader.getController();
                ModifierCategorie.initData(selectedCategorie);

                // Changer la scène pour afficher l'interface de mise à jour
                tableview.getScene().setRoot(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Si aucune categorie n'est sélectionné, afficher une boîte de dialogue d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a category to update.");
            alert.showAndWait();
        }
    }

    @FXML
    void naviguerAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCategorie.fxml"));
            Parent root = loader.load();

            AjouterCategorie AjouterCategorie = loader.getController();

            tableview.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void naviguerSer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherService.fxml"));
            Parent root = loader.load();

            AfficherServiceController AfficherServiceController = loader.getController();

            tableview.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }








    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
    }
}


