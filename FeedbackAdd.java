package controllers;

import entities.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import utils.myDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class FeedbackAdd {

    @FXML
    private ChoiceBox<String> feedbackType;

    @FXML
    private TextArea feedbackArea;

    @FXML
    private Button homeB;

    @FXML
    private ImageView profileB;

    @FXML
    private Button sendB;

    @FXML
    private Label messageLabel;

    Connection connection = myDatabase.getInstance().getConnection();

    @FXML
    void initialize() {
        // Initialize the choice box with feedback types
        feedbackType.getItems().addAll("Choose your feedback", "Bug Report", "Product or Service feedback", "Feature Request");
        feedbackType.setValue("General"); // Set default value
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
    void profileButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/profile.fxml"));
            profileB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void sendFeedbackButton(ActionEvent event) {
        String feedbackTp = feedbackType.getValue();
        String feedback = feedbackArea.getText();
            try{
                String req ="update user set feedback=?, feedbackType=? where id=?";

                PreparedStatement preparedStatement= connection.prepareStatement(req);
                preparedStatement.setString(1,feedback);
                preparedStatement.setString(2,feedbackTp);
                preparedStatement.setInt(3,Login.getCurrentUser().getId());

                int rowsAffected = preparedStatement.executeUpdate();
                if(rowsAffected>0){
                    messageLabel.setText(Login.getCurrentUser().getFull_name()+" has done a feedback !");
                    clearErrorMessageAfterDelay();
                }

            }catch (Exception e){
                throw new RuntimeException(e);
            }
    }

    private void clearErrorMessageAfterDelay() {
        // Set a timeline to clear the label after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> messageLabel.setText("")));
        timeline.play(); // Start the timeline
    }
}
