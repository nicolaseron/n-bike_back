package com.example.n_bike.controller;

import com.example.n_bike.command.CreateOrderCommand;
import com.example.n_bike.services.SalesOrderService;
import com.itextpdf.text.DocumentException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SalesOrderController {

    private final SalesOrderService SalesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        SalesOrderService = salesOrderService;
    }

    @PostMapping("createSalesOrder")
    public void createSalesOrder(@RequestBody CreateOrderCommand command) throws DocumentException, IOException {
        SalesOrderService.createSalesOrder(command);
    }
}
