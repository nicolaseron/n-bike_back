package com.example.n_bike.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateFile {

    public static Document createPDF(String directory, String orderNumber) throws IOException, DocumentException {
        Document document = new Document();
        new File(orderNumber);
        new File(directory).mkdirs();

        PdfWriter.getInstance(document, new FileOutputStream(directory + "/" + orderNumber + ".pdf"));
        return document;
    }
}
