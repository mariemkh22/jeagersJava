package controllers;

import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RatingManager {

    private static final String FILE_PATH = "ratings.txt";

    public static void saveRating(int serviceId, int rating) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(serviceId + "," + rating);
            writer.newLine();
            System.out.println("Rating enregistré avec succès !");
            showAlert("Succès", "Rating enregistré avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement du rating : " + e.getMessage());
            showAlert("Erreur", "Une erreur s'est produite lors de l'enregistrement du rating.");
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
