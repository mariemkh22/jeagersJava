package controllers;
import com.mysql.cj.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import services.serviceUser;
import utils.myDatabase;
import org.mindrot.jbcrypt.BCrypt;
import javafx.scene.control.CheckBox;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javax.speech.EngineException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

public class Login {

    @FXML
    private Button cancelB;

    @FXML
    private Button forgotB;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button loginB;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private PasswordField passwordTextField;
    private final serviceUser px = new serviceUser();

    private static User currentUser = null;

    @FXML
    private Button signupB;

    @FXML
    private CheckBox showPass;

    @FXML
    private TextField visiblePassTF;

    static Connection connection;
    Boolean result;


    @FXML
    void cancelButton(ActionEvent event) {
        emailTextField.clear();
        passwordTextField.clear();
    }

    @FXML
    void loginButton(ActionEvent event) {
        if(emailTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false){
            validateLogin();
            try {
                if (currentUser!=null && !isUserBlocked(emailTextField.getText())) {
                    TextToSpeech.main(new String[]{});
                    Parent root = FXMLLoader.load(getClass().getResource("/backendHome.fxml"));
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(new Scene(root, 1550, 820));
                    stage.show();
                    ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                }
                else if (isUserBlocked(emailTextField.getText())){
                    loginMessageLabel.setText("Your account is blocked");
                }
                }
            catch (IOException e){
                throw new RuntimeException(e);
            } catch (EngineException e) {
                throw new RuntimeException(e);
            }
        } else {
            loginMessageLabel.setText("Please try again.");
            clearErrorMessageAfterDelay();
        }
    }

    public void validateLogin() {
        myDatabase database = myDatabase.getInstance();
        Connection connection = database.getConnection();

        String verifyLogin = "SELECT * FROM user WHERE email = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(verifyLogin);
            statement.setString(1, emailTextField.getText());
            ResultSet queryResult = statement.executeQuery();

            if (queryResult.next()) {
                String hashedPasswordFromDB = queryResult.getString("password");
                String hashedInputPassword = passwordTextField.getText();

                if (BCrypt.checkpw(hashedInputPassword,hashedPasswordFromDB)) {
                    currentUser = new User(
                            queryResult.getInt("id"),
                            queryResult.getString("email"),
                            queryResult.getString("full_name"),
                            queryResult.getString("phone_number"),
                            queryResult.getString("date_of_birth")
                    );
                    System.out.println("Login successful!");
                    return;
                }
            }
            currentUser = null;
            loginMessageLabel.setText("Invalid email or password.");
            clearErrorMessageAfterDelay();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static User clearUserSession(){
        return currentUser = null;
    }

    private void clearErrorMessageAfterDelay() {
        // Set a timeline to clear the label after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> loginMessageLabel.setText("")));
        timeline.play(); // Start the timeline
    }

    @FXML
    void signupButton(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/signUp.fxml"));
            Stage stage=new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root,544,450));
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showPassword(ActionEvent event) {
        if(showPass.isSelected()){
            visiblePassTF.setText(passwordTextField.getText());
            visiblePassTF.setVisible(true);
            passwordTextField.setVisible(false);
        }
        else {
            passwordTextField.setText(visiblePassTF.getText());
            passwordTextField.setVisible(true);
            visiblePassTF.setVisible(false);
        }
    }

    @FXML
    void forgotButton(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/sendEmail.fxml"));
            Stage stage=new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root,412,450));
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static boolean isUserBlocked(String email) {
        try  {
            myDatabase.getInstance().getConnection();
            connection = myDatabase.getInstance().getConnection();
            String query = "SELECT enabled FROM user WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int enabled = resultSet.getInt("enabled");
                        return (enabled == 1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
