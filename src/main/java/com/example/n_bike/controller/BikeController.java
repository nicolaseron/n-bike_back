package com.example.n_bike.controller;

import com.example.n_bike.command.CreateOrderCommand;
import com.example.n_bike.dto.BikeDTO;
import com.example.n_bike.entity.Bikes;
import com.example.n_bike.services.BikeService;
import com.itextpdf.text.DocumentException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BikeController {
    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("bikes")
    public List<Bikes> bikes() {
        return bikeService.getAllBike();
    }

    @GetMapping("bikes/category/{categoryId}")
    public List<BikeDTO> bikes(@PathVariable int categoryId) {
        return bikeService.getDtoBikesByCategory(categoryId);
    }

    @GetMapping("margin")
    public List<Bikes> margin() {
        return bikeService.setMargin();
    }

    @GetMapping("/categories/{category}")
    public List<BikeDTO> getDTOBikeByCategoryAndParams(@PathVariable String category,
                                                       @RequestParam(required = false) String size,
                                                       @RequestParam(required = false) String price,
                                                       @RequestParam(required = false) String weight,
                                                       @RequestParam(required = false) boolean is_electric,
                                                       @RequestParam(required = false) String transmission,
                                                       @RequestParam(required = false) String frame) {
        return bikeService.getDTOBikeByCategoryAndParams(category, size, price, weight, is_electric, transmission, frame);
    }

    @GetMapping("bike/{bikeId}")
    public BikeDTO getBikeById(@PathVariable long bikeId) {
        return bikeService.toDto(bikeService.getBikeById(bikeId));
    }

    @GetMapping("bike/getBikeSizeByModel/{bikeId}")
    public List<BikeDTO> getBikeSizeByModel(@PathVariable long bikeId) {
        return bikeService.getBikeSizeByModel(bikeId);
    }

    @PostMapping("createSalesOrder")
    public void createOrder(@RequestBody CreateOrderCommand command) throws DocumentException, IOException {
        bikeService.createSalesOrder(command);
    }
}
