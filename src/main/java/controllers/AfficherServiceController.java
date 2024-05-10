package controllers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.categorie_service;
import entities.service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import menusandcharts.MenuAppWithChartController;
import services.ServiceService;

public class AfficherServiceController {

    @FXML
    private Label deliveryB;
    @FXML
    private Label serviceB;
    @FXML
    private Label userB;
    @FXML
    private Label productB;
    @FXML
    private Label messageB;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<service, String> datestf;

    @FXML
    private TableColumn<service, String> descriptionstf;

    @FXML
    private TableColumn<service, String> locationstf;

    @FXML
    private TableColumn<service, String> namestf;

    @FXML
    private TableColumn<service, String> statestf;

    @FXML
    private TableColumn<categorie_service, Integer> catidtf;



    @FXML
    private TableColumn<service, Image> imageCol;

    @FXML
    private Button BBBB;

    @FXML
    private Button BBBB1;

    @FXML
    private Button BBBB11;

    @FXML
    private Button BBBB111;

    @FXML
    private Button BBBB1111;

    @FXML
    private Button BBBB11111;

    @FXML
    private Button BBBB2;

    @FXML
    private Button addBTN;

    @FXML
    private ButtonBar bar;

    @FXML
    private ButtonBar bar1;

    @FXML
    private ButtonBar bar11;

    @FXML
    private ButtonBar bar111;

    @FXML
    private ButtonBar bar1111;

    @FXML
    private ButtonBar bar11111;

    @FXML
    private ButtonBar bar2;


    @FXML
    private Button deleteBTN;
    @FXML
    private Pane inner_pane;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private HBox root;

    @FXML
    private TextField searchtxt;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Button updateBTN;
    @FXML
    private Label welcomeLBL;

    /*@FXML
    private TableView<service> tableview;*/
    @FXML
    private ListView<service> listView;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img10;

    @FXML
    private ImageView img11;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @FXML
    private ImageView img6;

    @FXML
    private ImageView img7;

    @FXML
    private ImageView img8;

    @FXML
    private ImageView img9;




    private final ServiceService ss = new ServiceService();
    private ObservableList<service> observableList;

    @FXML
    void initialize() {
        try {
            List<service> serviceList = ss.afficher();
            observableList = FXCollections.observableList(serviceList);

            listView.setItems(observableList);

            listView.setCellFactory(param -> new ListCell<service>() {
                @Override
                protected void updateItem(service item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Name :  ").append(item.getName_s()).append("\n")
                                .append("Description :  ").append(item.getDescription_s()).append("\n")
                                .append("Location :  ").append(item.getLocalisation()).append("\n")
                                .append("State :  ").append(item.getState()).append("\n")
                                .append("Availability Date : ").append(item.getDispo_date()).append("\n")
                                .append("Category ID : ").append(item.getCat_id()).append("\n")


                        ;



                        ImageView imageView = new ImageView();
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);

                        try {
                            Image image = new Image(new FileInputStream(item.getImageFile()));
                            imageView.setImage(image);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            System.err.println("Erreur lors de la lecture de l'image : " + e.getMessage());
                        }

                        setText(sb.toString());
                        setGraphic(imageView);
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void supprimerService(ActionEvent event) {
        service selectedService = listView.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            try {
                ss.supprimer(selectedService.getId());
                observableList.remove(selectedService);
                // Afficher une boîte de dialogue de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Service deleted successfully.");
                alert.showAndWait();
            } catch (SQLException e) {
                // En cas d'erreur, afficher une boîte de dialogue d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de la suppression du service : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Si aucun service n'est sélectionné, afficher une boîte de dialogue d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un service à supprimer.");
            alert.showAndWait();
        }

    }
    // Méthode pour naviguer vers l'interface de mise à jour
    @FXML
    void navigatetoupdate(ActionEvent event) {
        service selectedService = listView.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierService.fxml"));
                Parent root = loader.load();

                ModifierService ModifierService = loader.getController();
                ModifierService.initData(selectedService);

                // Changer la scène pour afficher l'interface de mise à jour
                listView.getScene().setRoot(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Si aucun service n'est sélectionné, afficher une boîte de dialogue d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un service à mettre à jour.");
            alert.showAndWait();
        }
    }

    @FXML
    void navigatetoAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterService.fxml"));
            Parent root = loader.load();

            AjouterService AjouterService = loader.getController();


            // Changer la scène pour afficher l'interface de mise à jour
            listView.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    void naviguerCat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCategorie.fxml"));
            Parent root = loader.load();

            AfficherCategorieController AfficherCategorieController = loader.getController();


            // Changer la scène pour afficher l'interface de mise à jour
            listView.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void stat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chart.fxml"));
            Parent root = loader.load();

            MenuAppWithChartController MenuAppWithChartController = loader.getController();


            // Changer la scène pour afficher l'interface de mise à jour
            listView.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
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
    void userButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/displayBackEnd.fxml"));
            userB.getScene().setRoot(root);
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

}