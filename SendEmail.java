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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SendEmail {

    @FXML
    private ImageView brandingImageViewR;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label labelInfo;

    @FXML
    private Button resetB;

    @FXML
    private Button sendB;

    @FXML
    private Button backB;

    private static final String EMAIL_TEMPLATE_PATH = "/mail.html";

    @FXML
    void resetButton(ActionEvent event) {
        emailTextField.clear();
    }

    @FXML
    void backButton(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage=new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root,569,400));
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void sendButton(ActionEvent event) throws MessagingException {

        String recipientEmail = emailTextField.getText();
        String generatedCode = generateRandomCode();
        if (emailTextField.getText().isBlank() == true ){
            labelInfo.setText("Invalid e-mail address");
            clearErrorMessageAfterDelay();
        }
        else if (!emailTextField.getText().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            labelInfo.setText("Invalid e-mail address");
            clearErrorMessageAfterDelay();
        }
        else {
            sendEmail(recipientEmail, generatedCode);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/receiveCode.fxml"));
                Parent root = loader.load();
                ReceiveCode controller = loader.getController();
                controller.setGeneratedCode(generatedCode);
                controller.setEmail(recipientEmail);
                sendB.getScene().setRoot(root);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public static void sendEmail(String recipientEmail, String generatedCode) throws MessagingException {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "benmabroukyassine399@gmail.com";
        String password = "wvwa dfpd rgsu cstm";

        Session session = Session.getInstance(properties,new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,password);
            }
        });

        String emailContent = loadEmailTemplate(generatedCode); // Load email template
        Message message = prepareMessage(session, myEmail, recipientEmail, emailContent);
        Transport.send(message);
    }

    private static String loadEmailTemplate(String generatedCode) {
        InputStream inputStream = SendEmail.class.getResourceAsStream(EMAIL_TEMPLATE_PATH);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String emailContent = scanner.hasNext() ? scanner.next() : ""; // Read content from the HTML file
        scanner.close();
        return emailContent.replace("{generatedCode}", generatedCode); // Replace placeholder with generated code
    }

    private static Message prepareMessage(Session session, String myEmail, String recipientEmail, String emailContent) {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(recipientEmail)
            });
            message.setSubject("Reset password");
            message.setContent(emailContent, "text/html; charset=utf-8"); // Set email content as HTML
            return message;
        } catch (Exception e){
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }
    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    private void clearErrorMessageAfterDelay() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> labelInfo.setText("")));
        timeline.play();
    }
}
