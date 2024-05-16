package controllers;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;


import entities.Commande;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PDFGenerator {

    public void generateCmdPDF(List<Commande> cmds) {

        String downloadsFolder = System.getProperty("user.home") + "/Downloads/";


        String fileName = downloadsFolder + "liste_commandes.pdf";

        Document document = new Document();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));


            addWatermark(writer);


            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

            // Add title
            Paragraph title = new Paragraph("Your Orders", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            addLogo(writer);

            // Add greeting message
            Paragraph greeting = new Paragraph("Dear Valued Customer,", normalFont);
            greeting.setAlignment(Element.ALIGN_LEFT);
            greeting.setSpacingAfter(10f);
            document.add(greeting);

            // Add commands
            for (Commande cmd : cmds) {


                String imageUrl = cmd.getProduit().getImageFile(); // Récupérer le nom du fichier de l'image du produit
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Image productImage = Image.getInstance(imageUrl);
                    productImage.scaleToFit(100, 100);
                    document.add(productImage);
                }

                // Add product details
                Chunk productName = new Chunk("Product: " + cmd.getProduit().getNomProduit(), normalFont);
                Chunk orderDate = new Chunk("Order Date: " + cmd.getDateCmd(), normalFont);
                Chunk deliveryMethod = new Chunk("Delivery Method: " + cmd.getMethode_livraison(), normalFont);
                Chunk city = new Chunk("City: " + cmd.getVille(), normalFont);

                // Create a list for better organization
                com.itextpdf.text.List orderDetails = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
                orderDetails.add(new ListItem(productName));
                orderDetails.add(new ListItem(orderDate));
                orderDetails.add(new ListItem(deliveryMethod));
                orderDetails.add(new ListItem(city));


                document.add(orderDetails);


                document.add(Chunk.NEWLINE);
            }

            document.close();

            // Open the generated PDF file
            openPDFFile(fileName);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addWatermark(PdfWriter writer) throws IOException, DocumentException {
        // Load watermark image
        URL imageUrl = getClass().getResource("/product.png");
        Image watermarkImage = Image.getInstance(imageUrl); // Provide the path to your watermark image

        // Set watermark properties
        watermarkImage.setAbsolutePosition(200, 400);
        watermarkImage.scaleAbsolute(200, 200);

        // Add watermark to the writer
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte contentByte = writer.getDirectContentUnder();
                try {
                    contentByte.addImage(watermarkImage);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void addLogo(PdfWriter writer) throws IOException, DocumentException {
        // Load logo image
        URL logoUrl = getClass().getResource("/LOGO.png");
        Image logoImage = Image.getInstance(logoUrl);

        // Set logo position and size
        float pageWidth = PageSize.A4.getWidth();
        float logoWidth = 100;
        float logoHeight = 100;
        float logoX = pageWidth - logoWidth - 50; // Adjust the margin as needed
        float logoY = PageSize.A4.getHeight() - logoHeight - 50; // Adjust the margin as needed

        logoImage.setAbsolutePosition(logoX, logoY);
        logoImage.scaleToFit(logoWidth, logoHeight);

        // Add logo to the writer
        writer.getDirectContent().addImage(logoImage);
    }






    private void openPDFFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
