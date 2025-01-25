package com.example.n_bike.utils;

import com.example.n_bike.command.BikeOrderCommand;
import com.example.n_bike.command.CreateOrderCommand;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PDFUtils {

    private static final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static final Font paragraphFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public static void addTitlePage(Document document, String text) throws DocumentException {
        Paragraph title = new Paragraph(text);
        title.setAlignment(Element.TITLE);
        title.setFont(titleFont);
        document.add(title);
        addEmptyLine(document, 1);
    }

    public static void addEmptyLine(Document document, int number) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
        document.add(paragraph);
    }

    public static void addContactDetails(Document document, CreateOrderCommand command) throws DocumentException {

        String personName = command.getLastName() + " " + command.getFirstName();
        String personStreet = command.getStreetName() + ", " + command.getStreetNumber();
        String personCity = command.getPostalCode() + ", " + command.getCity();
        String[] personInfo = new String[]{personName, personStreet, personCity};

        for (String info : personInfo) {
            Paragraph paragraph = new Paragraph();
            paragraph.add(info);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
        }
        addEmptyLine(document, 1);
    }

    public static void addText(Document document, String text) throws DocumentException {
        Paragraph paragraph = new Paragraph(text, paragraphFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
    }

    public static void addSalesItems(Document document, List<BikeOrderCommand> bikeOrderCommands) throws DocumentException, IOException {

        PdfPTable table = new PdfPTable(6);
        table.setWidths(new float[]{1, 2, 3, 1, 2, 3});

        List<String> headers = Arrays.asList("N°", "Image", "Modèle", "Q.", "Prix Unit.", "Prix Total");

        for (String header : headers) {
            addCell(table, header, FontFactory.HELVETICA_BOLD, 12, BaseColor.CYAN);
        }

        double totalOrderPrice = 0;
        int i = 1;

        for (BikeOrderCommand items : bikeOrderCommands) {
            addCell(table, String.valueOf(i), FontFactory.HELVETICA, 10, BaseColor.WHITE);

            Image img = Image.getInstance(items.getBike().getBikeImages().get(0).getImage().getUrl());
            img.scaleToFit(50, 50);
            PdfPCell imageCell = new PdfPCell(img);
            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            imageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            imageCell.setPadding(5);
            table.addCell(imageCell);

            addCell(table, items.getBike().getBrand().getName() + " " + items.getBike().getModel() + " (" + items.getBike().getSize() + ")", FontFactory.HELVETICA, 10, BaseColor.WHITE);
            addCell(table, String.valueOf(items.getQuantity()), FontFactory.HELVETICA, 10, BaseColor.WHITE);

            Double price = items.getBike().getSalesPrice();
            addCell(table, String.format("%.2f", price) + " €", FontFactory.HELVETICA, 10, BaseColor.WHITE);

            double totalPrice = price * items.getQuantity();
            addCell(table, String.format("%.2f", totalPrice) + " €", FontFactory.HELVETICA, 10, BaseColor.WHITE);

            totalOrderPrice += totalPrice;
            i++;
        }

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");

        addCell(table, "Prix Total", FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

        PdfPCell priceCell = new PdfPCell(new Phrase(String.format("%.2f", totalOrderPrice) + " €", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        priceCell.setBackgroundColor(BaseColor.GREEN);
        priceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        priceCell.setPadding(10);
        table.addCell(priceCell);

        document.add(table);
    }

    private static void addCell(PdfPTable table, String content, String fontName, int fontSize, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(fontName, fontSize)));
        cell.setBackgroundColor(backgroundColor);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    public static void addDeliveryInfo(Document document, CreateOrderCommand command) throws DocumentException {

        String deliveryAddress = "Livraison prévue à l'intention de :\n" +
                command.getLastName() + " " + command.getFirstName() + "\n" +
                command.getPhone() + "\n" +
                command.getStreetName() + " " + command.getStreetNumber() + "\n" +
                command.getPostalCode() + " " + command.getCity();

        String deliveryInfo = "";
        if (command.getDeliveryInfo() != null && !command.getDeliveryInfo().isEmpty()) {
            deliveryInfo += "Instructions de livraison : " + command.getDeliveryInfo();
        }

        Paragraph nameParagraph = new Paragraph(deliveryAddress, paragraphFont);
        nameParagraph.setAlignment(Element.ALIGN_LEFT);
        nameParagraph.setIndentationLeft(50);
        document.add(nameParagraph);

        addEmptyLine(document, 1);

        if (!deliveryInfo.isEmpty()) {
            Paragraph infoParagraph = new Paragraph(deliveryInfo, paragraphFont);
            infoParagraph.setAlignment(Element.ALIGN_LEFT);
            infoParagraph.setIndentationLeft(50);
            document.add(infoParagraph);
        }
    }

    public static void addPicture(Document document, String picture, float sizeX, float sizeY, Float posX, Float posY) throws DocumentException, IOException {
        Image img = Image.getInstance(picture);
        img.scaleToFit(sizeX, sizeY);
        img.setAbsolutePosition(posX, posY);
        document.add(img);
    }

    public static void addPurchaseItems(Document document, List<BikeOrderCommand> bikeOrderCommands) throws DocumentException, IOException {

        PdfPTable table = new PdfPTable(6);
        table.setWidths(new float[]{1, 2, 3, 1, 2, 3});

        List<String> headers = Arrays.asList("N°", "Réf. Article", "Modèle", "Q.", "Prix Unit.", "Prix Total");

        for (String header : headers) {
            addCell(table, header, FontFactory.HELVETICA_BOLD, 12, BaseColor.CYAN);
        }

        double totalOrderPrice = 0;
        int i = 1;

        for (BikeOrderCommand items : bikeOrderCommands) {
            addCell(table, String.valueOf(i), FontFactory.HELVETICA, 10, BaseColor.WHITE);

            addCell(table, items.getBike().getArticle_id(), FontFactory.HELVETICA, 10, BaseColor.WHITE);
            addCell(table, items.getBike().getBrand().getName() + " " + items.getBike().getModel() + " (" + items.getBike().getSize() + ")", FontFactory.HELVETICA, 10, BaseColor.WHITE);
            addCell(table, String.valueOf(items.getQuantity()), FontFactory.HELVETICA, 10, BaseColor.WHITE);

            Double price = items.getBike().getPurchase_price();
            addCell(table, String.format("%.2f", price) + " €", FontFactory.HELVETICA, 10, BaseColor.WHITE);

            double totalPrice = price * items.getQuantity();
            addCell(table, String.format("%.2f", totalPrice) + " €", FontFactory.HELVETICA, 10, BaseColor.WHITE);

            totalOrderPrice += totalPrice;
            i++;
        }

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");

        addCell(table, "Prix Total", FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

        PdfPCell priceCell = new PdfPCell(new Phrase(String.format("%.2f", totalOrderPrice) + " €", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        priceCell.setBackgroundColor(BaseColor.GREEN);
        priceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        priceCell.setPadding(10);
        table.addCell(priceCell);

        document.add(table);
    }
}