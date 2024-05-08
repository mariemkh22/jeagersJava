package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class ReceiveCode {

    @FXML
    private ImageView brandingImageViewR;

    @FXML
    private TextField codeTF;

    @FXML
    private Button confirmB;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Button resetB;

    private String generatedCode;

    private String userEmail;

    @FXML
    void resetButton(ActionEvent event) {
        codeTF.clear();
    }

    @FXML
    void confirmButton(ActionEvent event) {
        if (codeTF.getText().isBlank() == true){
            loginMessageLabel.setText("Invalid code");
            clearErrorMessageAfterDelay();
        }
        if(validateCode()==true){
            try {
                FXMLLoader resetPasswordLoader = new FXMLLoader(getClass().getResource("/resetPassword.fxml"));
                Parent resetPasswordRoot = resetPasswordLoader.load();
                ResetPassword resetPasswordController = resetPasswordLoader.getController();
                resetPasswordController.setEmail(userEmail);
                confirmB.getScene().setRoot(resetPasswordRoot);
                System.out.println(userEmail);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        else {
            loginMessageLabel.setText("Invalid code !");
            clearErrorMessageAfterDelay();
        }
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    private boolean validateCode(){
        String enteredCode = codeTF.getText();
        return enteredCode.equals(generatedCode);
    }

    private void clearErrorMessageAfterDelay() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> loginMessageLabel.setText("")));
        timeline.play(); // Start the timeline
    }
}
