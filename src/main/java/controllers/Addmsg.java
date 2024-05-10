package controllers;

import entities.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.zone.ZoneRulesProvider.refresh;

public class Addmsg {

    @FXML
    private ListView<String> listview;
    @FXML
    private TextField dateenvTF;

    @FXML
    private TextField daterecTF;
    @FXML
    private Label homeB;

    @FXML
    private TextField sendmsgTF;

    private Show showController;

    private ServiceMessage serviceMessage = new ServiceMessage();

    private ObservableList<String> msgs = FXCollections.observableArrayList();

    private Map<Message, Integer> likesMap = new HashMap<>();
    private Map<Message, Integer> dislikesMap = new HashMap<>();


    @FXML
    void addmsgAction(ActionEvent event) {

        String date_reception = daterecTF.getText();
        String date_envoie = dateenvTF.getText();
        String contenue = sendmsgTF.getText();

        Message msg = new Message(date_envoie, date_reception, contenue);
        if (containsBadWord(contenue)) {
            showAlert("Your message contains inappropriate terms. Please modify it to respect the rights of this application.");
            return;
        }
        try {
            serviceMessage.ajouter(msg); // Ajouter le message à la base de données
            msgs.add(msg.getContenue()); // Ajouter le contenu du message à la ListView
            listview.setItems(msgs); // Mettre à jour la ListView avec la nouvelle liste de messages
            sendmsgTF.clear(); // Effacer le champ de texte après l'ajout
            // Afficher un message de confirmation
            showAlert("Message sent to username .");
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions si nécessaire
        }


    }
    List<String> badWords = Arrays.asList("shit", "hell", "badword3");

    public boolean containsBadWord(String message) {
        for (String badWord : badWords) {
            if (message.toLowerCase().contains(badWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }




    @FXML
    void dislikeAction(ActionEvent event) throws SQLException {
        int selectedIndex = listview.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            Message selectedMessage = null;
            try {
                selectedMessage = serviceMessage.afficher().get(selectedIndex);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int likes = dislikesMap.getOrDefault(selectedMessage, 0);
            dislikesMap.put(selectedMessage, likes + 1);

            // Mettre à jour le compteur de likes dans l'interface Show
            refreshShowInterface();
        }

        }



    @FXML
    void likeAction(ActionEvent event) {
        int selectedIndex = listview.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            Message selectedMessage = null;
            try {
                selectedMessage = serviceMessage.afficher().get(selectedIndex);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int likes = likesMap.getOrDefault(selectedMessage, 0);
            likesMap.put(selectedMessage, likes + 1);

            // Mettre à jour le compteur de likes dans l'interface Show
            refreshShowInterface();
            int totalLikes = getTotalLikes();
            int totalDislikes = getTotalDislikes();
            showController.updatePercentageLabels(totalLikes, totalDislikes);
            showController.updatePercentageBars( totalLikes, totalDislikes);
        }

    }
    private void refreshShowInterface() {
        // Récupérer le contrôleur de la vue Show
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Show showController = loader.getController();

        // Mettre à jour l'affichage des likes en utilisant la méthode du contrôleur ShowController
        showController.updateLikes(likesMap.getOrDefault(msgs, 0));
    }


    @FXML
    void Addnotification(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterNotification.fxml"));
            Parent root = loader.load();

            // Obtenez la fenêtre actuelle à partir de l'image cliquée
            Stage currentStage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();

            // Changez la scène de la fenêtre actuelle
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Message getMessageFromListView() {
        int selectedIndex = listview.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedMessageContent = listview.getSelectionModel().getSelectedItem();
            // Extrayez le contenu du message de la ListView
            String messageContent = selectedMessageContent.split(" - ")[0];
            // Parcourez vos messages pour trouver le message correspondant à ce contenu
            try {
                for (Message msg : serviceMessage.afficher()) {
                    if (msg.getContenue().equals(messageContent)) {
                        return msg;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }




    @FXML
    void deleteAction(ActionEvent event) {
        // Étape 1 : Vérification de la sélection dans la ListView
        Message selectedMessage = getMessageFromListView();
        if (selectedMessage != null) {
            try {
                // Étape 2 : Vérification de la suppression dans la base de données
                System.out.println("Message à supprimer : " + selectedMessage);
                serviceMessage.supprimer(selectedMessage.getId());
                System.out.println("Message supprimé de la base de données.");

                // Étape 3 : Mise à jour de la liste affichée
                System.out.println("Taille avant suppression : " + msgs.size());
                msgs.remove(selectedMessage.getContenue()); // Supprimer le contenu du message de la liste observable
                System.out.println("Taille après suppression : " + msgs.size());

                // Rafraîchir l'affichage de la ListView
                listview.setItems(msgs); // Réinitialiser les éléments de la ListView
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer les exceptions si nécessaire
                // Afficher un message d'erreur en cas d'échec de suppression
                showAlert("Erreur lors de la suppression du message.");
            }
        } else {
            // Afficher un message si aucun message n'est sélectionné dans la ListView
            showAlert("Veuillez sélectionner un message à supprimer.");
        }

    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();


    }
    @FXML
    void showAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/show.fxml"));
            Parent root = loader.load();

            // Récupérez le contrôleur de la nouvelle interface
            Show controller = loader.getController();
            // Calculez les statistiques
            int totalLikes = getTotalLikes();
            int totalDislikes = getTotalDislikes();
            // Initialisez la nouvelle interface avec les statistiques
            controller.initialize(totalLikes, totalDislikes);
            controller.updatePercentageLabels(totalLikes, totalDislikes);
            showController = controller;

            // Affichez la nouvelle interface dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private int getTotalLikes() {
        int total = 0;
        for (int likes : likesMap.values()) {
            total += likes;
        }
        return total;
    }
    private int getTotalDislikes() {
        int total = 0;
        for (int dislikes : dislikesMap.values()) {
            total += dislikes;
        }
        return total;
    }
    private void chargerMessages() {
        try {
            msgs.clear(); // Effacer la liste actuelle de messages
            for (Message msg : serviceMessage.afficher()) {
                int likes = likesMap.getOrDefault(msg, 0);
                int dislikes = dislikesMap.getOrDefault(msg, 0);
                msgs.add(msg.getContenue() + " - Likes: " + likes + ", Dislikes: " + dislikes);
                msgs.add(msg.getContenue()) ;// Ajouter chaque message à la ListView
            }
            listview.setItems(msgs); // Définir la liste des messages dans la ListView
            listview.scrollTo(msgs.size() - 1); // Faire défiler la ListView pour afficher le nouveau message ajouté
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions si nécessaire
        }
    }

    @FXML
    void homeButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
            System.out.println(Login.getCurrentUser().getFull_name());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}



