package controllers;

import entities.categorie_service;
import entities.service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import menusandcharts.MenuAppWithChartController;
import services.ServiceCategorie;
import services.ServiceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ourServices implements Initializable {

    @FXML
    private Button homeB;

    @FXML
    private Button myService;

    @FXML
    private Button serviceB;

    @FXML
    private HBox boxservices;

    @FXML
    private Pagination pagination;
    @FXML
    private ComboBox<categorie_service> filter;

    @FXML
    private TextField searchS;


    categorie_service selectedValue=null;




    private ServiceService serviceService = new ServiceService();

    private final int ITEMS_PER_PAGE = 3; // Nombre d'éléments par page

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadServices();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceCategorie serviceCategorie = new ServiceCategorie();

        try {
            List<categorie_service> categorieServices = serviceCategorie.afficher();
            filter.setItems(FXCollections.observableList(categorieServices));
            filter.getItems().add(new categorie_service("ALL","ALL"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadServices() throws SQLException {

        List<service> services =null;

        if(selectedValue!=null){
            if(selectedValue.toString().equals("ALL")){
                services=serviceService.afficher();
            }else{
                int id = selectedValue.getId();
                services=serviceService.getServiceByCategorie(id);
                selectedValue=null;}
        }else{
            services=serviceService.afficher();
        }


        int pageCount = (int) Math.ceil((double) services.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0); // Définir la page actuelle sur la première page

        List<service> finalServices = services;
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                loadPage(newValue.intValue(), finalServices);
            }
        });

        loadPage(0, services);
    }


    private void loadPage(int pageIndex, List<service> services) {
        boxservices.getChildren().clear();
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, services.size());

        for (int i = startIndex; i < endIndex; i++) {
            service service = services.get(i);
            try {
                // Chargement des cartes
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ourServicesCard.fxml"));
                HBox box = loader.load();
                ourServicesCard controller = loader.getController();
                controller.setService(service);
                boxservices.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void navigatetoAdd1(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterServiceFront.fxml"));
            Parent root = loader.load();
            AjouterServiceFront AjouterService = loader.getController();
            boxservices.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void search(ActionEvent event) {

    }
    @FXML
    void searchservice(KeyEvent event) throws SQLException {
        service.setSearchValue(((TextField) event.getSource()).getText());
        System.out.println("Recherche en cours: " + service.getSearchValue());

        // categId = -1;

        // Parent fxml = FXMLLoader.load(getClass().getResource("ProductsList.fxml"));


        List<service> services = null;
        services = serviceService.searchService(service.getSearchValue());
        loadPage(0, services);





    }
    @FXML
    void filterS(ActionEvent event) throws SQLException {
        selectedValue=filter.getValue();
        this.loadServices();


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
    void myServices(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ServicecardListView.fxml"));
            myService.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void serviceButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ourServices.fxml"));
            serviceB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}

