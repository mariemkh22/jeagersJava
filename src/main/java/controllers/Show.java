package controllers;

import entities.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import controllers.Addmsg;
import javafx.scene.control.ProgressBar;

import java.util.HashMap;
import java.util.Map;

public class Show {
    @FXML
    private Label dislikesLabel;

    @FXML
    private Label likesLabel;
    @FXML
    private ProgressBar dislikeProgressBar;
    @FXML
    private ProgressBar likeProgressBar;
    @FXML
    private ListView<String> listView;
    private ObservableList<String> msgs = FXCollections.observableArrayList();

    private Map<Message, Integer> likesMap = new HashMap<>();
    private Map<Message, Integer> dislikesMap = new HashMap<>();
    private ObservableList<String> messages = FXCollections.observableArrayList();
    public void initialize(int likesCount, int dislikesCount) {
        likesLabel.setText("Likes: " + likesCount);
        dislikesLabel.setText("Dislikes: " + dislikesCount);

    }
    private void refreshShowInterface() {
        // Mettre à jour l'affichage dans l'interface de visualisation (show)
        try {
            // Obtenez le contrôleur de l'interface de visualisation (show) si nécessaire

        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions si nécessaire
        }
    }
    // Méthode pour mettre à jour l'affichage des messages
    public void updateMessagesDisplay() {
        msgs.clear();
        for (Message msg : likesMap.keySet()) {
            int likes = likesMap.getOrDefault(msg, 0);
            int dislikes = dislikesMap.getOrDefault(msg, 0);
            msgs.add(msg.getContenue() + " - Likes: " + likes + ", Dislikes: " + dislikes);
        }
        listView.setItems(msgs);
    }

    public void updateLikes(int likes) {
        likesLabel.setText(String.valueOf(likes));
    }
    public double calculateLikePercentage(int totalLikes, int totalDislikes) {
        if (totalLikes + totalDislikes == 0) {
            return 0.0;
        }
        return (double) totalLikes / (totalLikes + totalDislikes) * 100.0;
    }

    public double calculateDislikePercentage(int totalLikes, int totalDislikes) {
        if (totalLikes + totalDislikes == 0) {
            return 0.0;
        }
        return (double) totalDislikes / (totalLikes + totalDislikes) * 100.0;
    }
    public void updatePercentageLabels(int totalLikes, int totalDislikes) {
        likesLabel.setText("Likes: " + totalLikes + " (" + calculateLikePercentage(totalLikes, totalDislikes) + "%)");
        dislikesLabel.setText("Dislikes: " + totalDislikes + " (" + calculateDislikePercentage(totalLikes, totalDislikes) + "%)");
    }
    public void updatePercentageBars(int totalLikes, int totalDislikes) {
        int total = totalLikes + totalDislikes;
        double scale = 100.0 / total; // calculer le facteur d'échelle

        // Mettre à jour les barres de progression avec les pourcentages ajustés
        likeProgressBar.setProgress(totalLikes * scale / 100.0);
        dislikeProgressBar.setProgress(totalDislikes * scale / 100.0);
    }
}
