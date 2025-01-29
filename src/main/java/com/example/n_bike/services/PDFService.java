package com.example.n_bike.services;

import com.example.n_bike.command.BikeOrderCommand;
import com.example.n_bike.command.CreateOrderCommand;
import com.example.n_bike.entity.Bikes;
import com.example.n_bike.utils.PDFUtils;
import com.itextpdf.text.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PDFService {

    private final String resourcesImg = "src/main/resources/n-bike-logo.png";

    public void createSalesOrderConfirmation(Document document, CreateOrderCommand command, List<BikeOrderCommand> bikeOrderCommands) throws DocumentException, IOException {

        document.open();
        PDFUtils.addEmptyLine(document, 1);
        PDFUtils.addPicture(document, resourcesImg, 150, 150, 20f, 775f);
        PDFUtils.addContactDetails(document, command);
        PDFUtils.addTitlePage(document, "Confirmation de commande - " + command.getOrderNumber());
        PDFUtils.addText(document, "Merci pour votre commande ! Ci-dessous vous trouverer le résumé de celle-ci :");
        PDFUtils.addEmptyLine(document, 2);
        PDFUtils.addSalesItems(document, bikeOrderCommands);
        PDFUtils.addEmptyLine(document, 2);
        PDFUtils.addDeliveryInfo(document, command);
        document.close();
    }

    public void createPurchaseOrderConfirmation(Document document, List<BikeOrderCommand> bikeOrderCommand, String orderNumber) throws DocumentException, IOException {
        document.open();
        PDFUtils.addEmptyLine(document, 1);
        PDFUtils.addPicture(document, resourcesImg, 150, 150, 20f, 775f);
        PDFUtils.addTitlePage(document, "Nouvelle commande - " + orderNumber);
        PDFUtils.addText(document, "Ci-dessous vous trouverer le résumé de celle-ci :");
        PDFUtils.addPurchaseItems(document, bikeOrderCommand);
        document.close();
    }
}
