package com.example.n_bike.services;

import com.example.n_bike.command.BikeOrderCommand;
import com.example.n_bike.command.CreateOrderCommand;
import com.example.n_bike.command.ItemsOrderCommand;
import com.example.n_bike.entity.Bikes;
import com.example.n_bike.entity.Brands;
import com.example.n_bike.entity.PendingOrder;
import com.example.n_bike.repository.BikeRepository;
import com.example.n_bike.repository.PendingOrderRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SalesOrderService {

    private final BikeService bikeService;
    private final BikeRepository bikeRepository;
    private final PendingOrderRepository pendingOrderRepository;
    private final MailSenderService mailSenderService;
    private final PurchaseOrderService purchaseOrderService;

    public SalesOrderService(BikeService bikeService, BikeRepository bikeRepository, PendingOrderRepository pendingOrderRepository, MailSenderService mailSenderService, PurchaseOrderService purchaseOrderService) {
        this.bikeService = bikeService;
        this.bikeRepository = bikeRepository;
        this.pendingOrderRepository = pendingOrderRepository;
        this.mailSenderService = mailSenderService;
        this.purchaseOrderService = purchaseOrderService;
    }


    public void createSalesOrder(CreateOrderCommand command) throws DocumentException, IOException {
        List<BikeOrderCommand> bikeOrders = new ArrayList<>();
        Set<Brands> brandsSet = new HashSet<>();
        for (ItemsOrderCommand item : command.getItems()) {
            Bikes bike = bikeService.getBikeById(item.getBikeId());
            BikeOrderCommand bikeOrder = new BikeOrderCommand(bike, item.getQuantity());
            bikeOrders.add(bikeOrder);
            int newQuantity = bike.getStock() - item.getQuantity();
            bike.setStock(newQuantity);
            bikeRepository.save(bike);
            if (newQuantity < bike.getStock_mini()) {
                PendingOrder pendingOrder = pendingOrderRepository.findByBike(bike);

                if (pendingOrder != null) {
                    pendingOrder.setQuantity(pendingOrder.getQuantity() + item.getQuantity());
                } else {
                    bikeService.createPendingOrder(bike, item.getQuantity());
                }
                brandsSet.add(bike.getBrand());
            }
        }
        mailSenderService.sendPDFMail(command, bikeOrders);
        purchaseOrderService.createPurchaseOrder(brandsSet);
    }
}
