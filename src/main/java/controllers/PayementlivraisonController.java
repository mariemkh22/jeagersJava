package controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class PayementlivraisonController {
    @FXML
    private Button PurchaseBtn;

    @FXML
    private TextField cardNumberField;

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

            // Gérer la réponse du paiement
            if ("Completed".equals(intent.getStatus())) {

                showAlert("Paiement réussi", "Le paiement a été effectué avec succès.");
            } else {
                showAlert("Erreur de paiement", "Le paiement a échoué. Veuillez réessayer.");

            }
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
}
