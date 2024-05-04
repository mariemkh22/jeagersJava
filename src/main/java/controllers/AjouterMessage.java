package controllers;

import entities.Message;
import entities.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.ServiceMessage;
import services.ServiceNotification;

import java.sql.SQLException;

public class AjouterMessage {

    @FXML
    private TextField sendmsgTF;
    @FXML
    private Text dateenvoieTF;

    @FXML
    private Text daterecepTF;

    @FXML
    private TextArea messagesTextArea;
    @FXML
    private ScrollPane scrollFT;

    private final ServiceMessage serviceMessage=new ServiceMessage();
    public void initialize() {
        // Assurez-vous que le TextArea ne peut pas être modifié par l'utilisateur
        messagesTextArea.setEditable(false);
        // Permettez au texte de se dérouler automatiquement
        messagesTextArea.setWrapText(true);
        scrollFT.setContent(messagesTextArea);
        messagesTextArea.scrollTopProperty().bind(messagesTextArea.heightProperty());
    }
    @FXML
    void addmsgAction(ActionEvent event) {

        String message = sendmsgTF.getText().trim();
        if (!message.isEmpty()) {
            try {
                serviceMessage.ajouter(new Message(dateenvoieTF.getText(),daterecepTF.getText(),sendmsgTF.getText()));
                messagesTextArea.appendText(message + "\n");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Message ajouté : " + message);
            sendmsgTF.clear();
        } else {
            // Gérer le cas où le champ de texte est vide
            // Vous pouvez afficher un message d'erreur ou effectuer une action appropriée
        }
    }


}
