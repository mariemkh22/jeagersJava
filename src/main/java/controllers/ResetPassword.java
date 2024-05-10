package controllers;

import entities.User;
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

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import services.serviceUser;
import utils.MyDatabase;

public class ResetPassword {

    @FXML
    private ImageView brandingImageViewR;

    @FXML
    private Button changeB;

    @FXML
    private TextField confirmNewPass;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField newPass;

    @FXML
    private Button resetB;
    User userChanged;
    private String newEmail;

    private final serviceUser px=new serviceUser();

    Connection connection = MyDatabase.getInstance().getConnection();

    public void setUserChanged(User user) {
        this.userChanged = user;
    }

    public void getData(User user){
        userChanged=user;
        userChanged.setPassword(newPass.getText());
    }

    public void setEmail(String email) {
        this.newEmail = email;
    }

    @FXML
    void changeButton(ActionEvent event) {
        String firstPass = newPass.getText();
        String secondPass = confirmNewPass.getText();
        if (newPass.getText().isBlank()==true && confirmNewPass.getText().isBlank()==true){
            loginMessageLabel.setText("Please write a password");
            clearErrorMessageAfterDelay();
        } else if (newPass.getText().isBlank()==true || confirmNewPass.getText().isBlank()==true) {
            loginMessageLabel.setText("Please fill the password field");
            clearErrorMessageAfterDelay();
        } else if (newPass.getText().length()<6) {
            loginMessageLabel.setText("Passowrd should be at least 6 letters");
            clearErrorMessageAfterDelay();
        } else if (!firstPass.equals(secondPass)) {
            loginMessageLabel.setText("Write the same password please");
            clearErrorMessageAfterDelay();
        } else {
            String finalPass = hashPassword(firstPass);
            try{
                String req ="update user set password=? where email=?";

                PreparedStatement preparedStatement= connection.prepareStatement(req);
                preparedStatement.setString(1,finalPass);
                preparedStatement.setString(2,newEmail); // Set the email value

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try{
                        Parent root= FXMLLoader.load(getClass().getResource("/login.fxml"));
                        Stage stage=new Stage();
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setScene(new Scene(root,569,400));
                        stage.show();
                        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                    } catch (Exception e){
                        e.printStackTrace();
                        e.getCause();
                    }
                } else {
                    loginMessageLabel.setText("Invalid password. Please try again");
                    clearErrorMessageAfterDelay();
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }



    @FXML
    void resetButton(ActionEvent event) {
        newPass.clear();
        confirmNewPass.clear();
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void clearErrorMessageAfterDelay() {
        // Set a timeline to clear the label after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> loginMessageLabel.setText("")));
        timeline.play(); // Start the timeline
    }

}
