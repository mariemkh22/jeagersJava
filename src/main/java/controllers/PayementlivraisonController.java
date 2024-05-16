package controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class PayementlivraisonController {

    @FXML
    private Button homeB;

    @FXML
    private Button productB;

    @FXML
    private Button serviceB;

    @FXML
    private Button PurchaseBtn;

    @FXML
    private TextField cardNumberField;

    @FXML
    private Button backbtn;

    @FXML
    private TextField cvvField;

    @FXML
    private TextField exprMonthField;

    @FXML
    private TextField exprYearField;

    // Méthode de validation pour la date d'expiration (format MM/YY)
    private boolean isValidExpiryDate(String expiryDate) {
        return Pattern.matches("^(0[1-9]|1[0-2])/[0-9]{2}$", expiryDate);
    }

    // Méthode de validation pour le numéro de carte
    private boolean isValidCardNumber(String cardNumber) {
        // Ajoutez votre logique de validation ici
        return cardNumber.length() == 16; // Exemple de validation : 16 chiffres
    }

    // Méthode de validation pour le CVV/CVC
    private boolean isValidCVV(String cvv) {
        // Ajoutez votre logique de validation ici
        return Pattern.matches("^[0-9]{3}$", cvv); // Exemple de validation : 3 chiffres
    }

    // Clé d'API Stripe
    private static final String STRIPE_SECRET_KEY = "sk_test_51OqQysKZrAz2LN0A7ipHdO7hwCLMk5ezKvF085E84HoyVz6W2VJhAfrNYhpvsgd6ZfXTEzWzLMCZQsEpuV65zpFE00FDVKrTuy";
    private static final String ACCOUNT_SID = "ACb32b5dce2baf53ab832edbd2b8e8537a";
    private static final String AUTH_TOKEN = "2593e821e32cd0de65719d3f4e70c602";
    private static final String FROM_NUMBER = "+12403392414";

    private void sendTwilioMessage(String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                new PhoneNumber("+21693683973"),
                new PhoneNumber(FROM_NUMBER),
                message
        ).create();
    }

    @FXML
    void handlePayment(ActionEvent event) {
        String cardNumber = cardNumberField.getText();
        String expiryDate = exprMonthField.getText() + "/" + exprYearField.getText();
        String cvv = cvvField.getText();
        if (!isValidCardNumber(cardNumber)) {
            showAlert("Erreur de saisie", "Veuillez saisir un numéro de carte valide.");
            return;
        }
        /*if (!isValidExpiryDate(expiryDate)) {
            showAlert("Erreur de saisie", "Veuillez saisir une date d'expiration valide (format MM/YY).");
            return;
        }*/

        if (!isValidCVV(cvv)) {
            showAlert("Erreur de saisie", "Veuillez saisir un code CVV/CVC valide (3 ou 4 chiffres).");
            return;
        }
        try {
            // Initialisez Stripe avec votre clé secrète
            Stripe.apiKey = STRIPE_SECRET_KEY;

            // Créer un objet de paiement Stripe
            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setCurrency("eur") // Définissez la devise du paiement
                    .setAmount(1000L) // Définissez le montant du paiement en centimes
                    .setDescription("Paiement pour la livraison") // Description du paiement
                    .build();

            // Traiter le paiement avec Stripe
            PaymentIntent intent = PaymentIntent.create(createParams);


                showAlert("Paiement réussi", "Le paiement a été effectué avec succès.");
                sendTwilioMessage("Votre paiement a été confirmé.");

        } catch (StripeException e) {
            // Gérer les exceptions
            showAlert("Erreur", "Une erreur est survenue lors du traitement du paiement : " + e.getMessage());
        }

    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void backbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLivraisonFront.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void homeButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            homeB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void productButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cardListView.fxml"));
            productB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void serviceButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ourServices.fxml"));
            serviceB.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
