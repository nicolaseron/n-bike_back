package com.example.n_bike.services;

import com.example.n_bike.command.BikeOrderCommand;
import com.example.n_bike.command.CreateOrderCommand;
import com.example.n_bike.utils.CreateFile;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MailSenderService {

    private final PDFService pdfService;
    private final JavaSmtpGmailSenderService senderService;


    public MailSenderService(PDFService pdfService, JavaSmtpGmailSenderService senderService) {
        this.pdfService = pdfService;
        this.senderService = senderService;
    }

    public void sendPDFMail(CreateOrderCommand command, List<BikeOrderCommand> bikeOrders) throws DocumentException, IOException {
        Document document = CreateFile.createPDF("sales_order_confirmation", command.getOrderNumber());
        pdfService.createSalesOrderConfirmation(document, command, bikeOrders);
        String bodyText = """
                Bonjour,

                Merci pour votre commande ! Vous trouverez en pièce jointe la confirmation de votre achat.

                Pour toute question, n'hésitez pas à nous contacter à l'adresse info@n-bike.be.

                Cordialement,

                L'équipe N-Bike.
                """;

        senderService.sendEmail(command.getEmail(), "Confirmation de commande " + command.getOrderNumber(), bodyText, "sales_order_confirmation/" + command.getOrderNumber() + ".pdf");
    }
}
