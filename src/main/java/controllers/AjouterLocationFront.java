package controllers;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.JSONObject;

import Entity.LocalisationGeographique;
import Services.Servicelocation;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.gluonhq.maps.MapPoint;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.sql.SQLException;

public class AjouterLocationFront {

    @FXML
    private Label Adresstfcont;

    @FXML
    private Label IntPostalcodetfcont;

    @FXML
    private Label Postalcodetfcont;

    @FXML
    private Label RgiontfCont;

    @FXML
    private TextField tfaddress;

    @FXML
    private TextField tfpostalcode;
    @FXML
    private Button adddeliverybtn;
    @FXML
    private TextField tfregion;
    private final Servicelocation sl = new Servicelocation();
    private MapPoint myPoint = new MapPoint(36.81749239416136, 10.18712253544902);
    @FXML
    private VBox address;

    public void initialize(){
        MapView mapView = createMapView();
        address.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);

        // Pass tfregion to addClickHandler method
        new CustomMapLayer().addClickHandler(mapView, tfregion);
    }
    private MapView createMapView(){
        MapView mapView = new MapView();
        mapView.setPrefSize(500, 400);
        CustomMapLayer customMapLayer = new CustomMapLayer();
        mapView.addLayer(customMapLayer);
        mapView.setZoom(5);
        mapView.flyTo(0, myPoint,0.1);
        customMapLayer.addClickHandler(mapView, tfregion);
        return mapView;
    }
    public class CustomMapLayer extends MapLayer {
        private final Node marker;

        public CustomMapLayer() {
            marker = new Circle(5, Color.RED);
            getChildren().add(marker);
        }

        @Override
        public void layoutLayer() {
            Point2D point = getMapPoint(myPoint.getLatitude(), myPoint.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
        }

        // Method to update the marker location
        public void updateMarkerLocation(double latitude, double longitude) {
            Point2D point = getMapPoint(latitude, longitude);
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());

        }


        public void addClickHandler(MapView mapView, TextField tfregion) {
            mapView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                // Get the coordinates of the mouse click relative to the scene
                double mouseX = event.getX();
                double mouseY = event.getY();

                // Convert the mouse coordinates to map coordinates
                MapPoint mapPoint = mapView.getMapPosition(mouseX,mouseY);

                // Check if the mapPoint is not null
                if (mapPoint != null) {
                    double latitude = mapPoint.getLatitude();
                    double longitude = mapPoint.getLongitude();
                    System.out.println("Clicked on map - Latitude: " + latitude + ", Longitude: " + longitude);

                    // Update marker position on the map
                    this.updateMarkerLocation(latitude, longitude);
                    myPoint=mapPoint;
                    mapView.flyTo(0, myPoint,0.1);

                    // Retrieve the region based on the clicked coordinates
                    String region = null;
                    try {
                        region = retrieveRegion(latitude, longitude);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Set the retrieved region name in the TextField
                    AjouterLocationFront.this.tfregion.setText(region);
                }
            });
        }
    }
    private String retrieveRegion(double latitude, double longitude) throws IOException {
        // URL du service de géocodage inverse
        String reverseGeocodingURL = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude + "&lon=" + longitude;

        // Initialiser une chaîne pour stocker la région récupérée
        String region = "";

        // Ouvrir une connexion avec l'URL du service de géocodage inverse
        URL url = new URL(reverseGeocodingURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Définir la méthode de requête HTTP sur GET
        conn.setRequestMethod("GET");

        // Lire la réponse JSON du service de géocodage inverse
        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            // Lire toute la réponse JSON dans une chaîne
            String responseBody = scanner.useDelimiter("\\A").next();

            // Convertir la réponse JSON en un objet JSON
            JSONObject responseJson = new JSONObject(responseBody);

            // Extraire le nom de la région de l'objet JSON
            region = responseJson.getJSONObject("address").getString("state");
        } finally {
            // Fermer la connexion
            conn.disconnect();
        }

        // Retourner le nom de la région
        return region;
    }

    @FXML
    void AddlocationBtn(ActionEvent event) {
        String address = tfaddress.getText().trim();
        String postalCode = tfpostalcode.getText().trim();
        String region = tfregion.getText().trim();

        boolean isValid = true;

        if (address.isEmpty()) {
            Adresstfcont.setText("Chose Adresse");
            isValid = false;
        } else {
            Adresstfcont.setText("");
        }

        if (postalCode.isEmpty() || !postalCode.matches("\\d+")) {
            Postalcodetfcont.setText("Code postal invalide");
            isValid = false;
        } else {
            Postalcodetfcont.setText("");
        }


        if (region.isEmpty()) {
            RgiontfCont.setText("Chose Region");
            isValid = false;
        } else {
            RgiontfCont.setText("");
        }

        if (isValid) {
            try {
                int postalCodeInt = Integer.parseInt(postalCode);
                if (postalCodeInt < 0) {
                    throw new NumberFormatException("Le code postal doit être un entier naturel positif.");
                }
                sl.ajouterLocation(new LocalisationGeographique(region, postalCodeInt, address));
                // Afficher une alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("La localisation a été ajoutée avec succès.");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                Postalcodetfcont.setText("Le code postal doit être un entier naturel positif.");
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        else {
            // Afficher une alerte pour informer l'utilisateur qu'il y a des erreurs dans le formulaire
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez corriger les erreurs dans le formulaire.");
            alert.showAndWait();
        }
    }

    @FXML
    void adddeliverybtn(ActionEvent event) {

        try {
            // Charger le fichier FXML de la fenêtre ajoutLocation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLivraisonFront.fxml"));
            Parent root = loader.load();

            // Récupérer la fenêtre actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Changer la scène de la fenêtre actuelle
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception s'il y a une erreur de chargement du fichier FXML
        }
    }


}
