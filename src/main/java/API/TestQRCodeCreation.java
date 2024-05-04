package API;

import entities.service;

public class TestQRCodeCreation {

    public static void main(String[] args) {
        // Créer un objet service fictif avec des données fictives
        service service = new service();

        service.setDescription_s("Description of the service");


        // Appeler la méthode pour générer le QR Code
        try {
            GenerateQRCode.generateQRcode(service, "test_qr_code1.png");
            System.out.println("QR Code créé avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du QR Code : " + e.getMessage());
        }
    }
}
