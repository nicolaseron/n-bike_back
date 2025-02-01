package com.example.n_bike.services;

import com.example.n_bike.command.BikeOrderCommand;
import com.example.n_bike.entity.Brands;
import com.example.n_bike.entity.PendingOrder;
import com.example.n_bike.repository.PendingOrderRepository;
import com.example.n_bike.utils.CreateFile;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PurchaseOrderService {

    private final PendingOrderRepository pendingOrderRepository;
    private final PDFService pdfService;

    public PurchaseOrderService(PendingOrderRepository pendingOrderRepository, PDFService pdfService) {
        this.pendingOrderRepository = pendingOrderRepository;
        this.pdfService = pdfService;
    }

    public void createPurchaseOrder(Set<Brands> brandsSet) throws DocumentException, IOException {

        for (Brands brand : brandsSet) {
            List<PendingOrder> pendingOrdersByBrands = pendingOrderRepository.findAllByBrands(brand);
            double totalPrice = 0;
            List<BikeOrderCommand> bikesOrdersOfCurrentBrands = new ArrayList<>();

            for (PendingOrder pendingOrder : pendingOrdersByBrands) {
                totalPrice += pendingOrder.getBike().getPurchase_price() * pendingOrder.getQuantity();
                bikesOrdersOfCurrentBrands.add(new BikeOrderCommand(pendingOrder.getBike(), pendingOrder.getQuantity()));
            }

            if (totalPrice >= brand.getMinimumOrderPrice() && !pendingOrdersByBrands.isEmpty()) {
                String orderNumber = "CMD" + UUID.randomUUID();
                Document document = CreateFile.createPDF("purchase_order_confirmation", orderNumber);
                pdfService.createPurchaseOrderConfirmation(document, bikesOrdersOfCurrentBrands, orderNumber);
                pendingOrderRepository.deleteAllByBrands(brand);
            }
        }
    }
}
