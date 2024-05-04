package API;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import entities.service;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateQRCode {

    public static void generateQRcode(service p, String fileName) throws WriterException, IOException {
        // Créer les données à encoder dans le QR Code
        String data = "Description: " + p.getDescription_s() + "\n";


        // Afficher les données
        System.out.println("Deescription of the service :");
        System.out.println(data);

        // Définir les paramètres du QR Code
        int size = 300;
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 1); // Marge du QR Code

        // Générer le QR Code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size, hints);

        // Convertir le BitMatrix en BufferedImage
        BufferedImage qrCodeImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        qrCodeImage.createGraphics();

        Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRect(0, 0, size, size);
        graphics.setColor(java.awt.Color.BLACK);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        // Chemin où enregistrer le QR Code
        String filePath = "C:\\Users\\khadi\\IdeaProjects\\Pi\\src\\main\\resources\\" + fileName;

        // Enregistrer le QR Code dans le fichier spécifié
        File outputFile = new File(filePath);
        if (!outputFile.exists()) {
            boolean created = outputFile.createNewFile();
            if (!created) {
                System.err.println("Impossible de créer le fichier QR Code.");
                return;
            }
        }
        ImageIO.write(qrCodeImage, "png", outputFile);
        System.out.println("QR Code créé avec succès : " + filePath);
    }

}
