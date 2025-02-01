package com.example.n_bike.services;

import com.example.n_bike.command.BikeOrderCommand;
import com.example.n_bike.command.CreateOrderCommand;
import com.example.n_bike.command.ItemsOrderCommand;
import com.example.n_bike.dto.BikeDTO;
import com.example.n_bike.entity.*;
import com.example.n_bike.mapper.BikeMapper;
import com.example.n_bike.repository.BikeRepository;
import com.example.n_bike.repository.BrandsRepository;
import com.example.n_bike.repository.PendingOrderRepository;
import com.example.n_bike.utils.CreateFile;
import com.example.n_bike.utils.MathUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class BikeService {
    private final BikeRepository bikeRepository;
    private final CategoriesService categoryService;
    private final PDFService pdfService;
    private final JavaSmtpGmailSenderService senderService;
    private final PendingOrderRepository pendingOrderRepository;

    public BikeService(BikeRepository bikeRepository, CategoriesService categoryService, PDFService pdfService, JavaSmtpGmailSenderService senderService, PendingOrderRepository pendingOrderRepository) {
        this.bikeRepository = bikeRepository;
        this.categoryService = categoryService;
        this.pdfService = pdfService;
        this.senderService = senderService;
        this.pendingOrderRepository = pendingOrderRepository;
    }

    public List<Bikes> getAllBike() {
        return bikeRepository.findAll();
    }

    public List<BikeDTO> getDtoBikesByCategory(int categoryId) {
        Categories categories = categoryService.findCategoryById(categoryId);
        List<Bikes> bikeList = bikeRepository.findBikesByCategory(categories);
        List<BikeDTO> bikeDTOList = new ArrayList<>();
        for (Bikes bike : bikeList) {
            BikeDTO bikeDTO = BikeMapper.toDto(bike);
            bikeDTOList.add(bikeDTO);
        }
        return bikeDTOList;
    }

    public Bikes getBikeById(long bikeId) {
        return bikeRepository.findById(bikeId)
                .orElseThrow(() -> new NoSuchElementException("Aucun vélo trouvé avec l'ID : " + bikeId));
    }

    public BikeDTO toDto(Bikes bike) {
        return BikeMapper.toDto(bike);
    }

    public List<Bikes> setMargin() {
        List<Bikes> bikes = bikeRepository.findAll();
        for (Bikes bike : bikes) {
            double purchasePrice = bike.getPurchase_price();
            double salesPrice = bike.getSalesPrice();
            double margin = MathUtils.round(((salesPrice - purchasePrice) / salesPrice) * 100, 2);
            bike.setMargin(margin);
            bikeRepository.save(bike);
        }
        return bikes;
    }

    public List<BikeDTO> getDTOBikeByCategoryAndParams(String category, String size, String price, String weight, boolean isElectric, String transmission, String frame) {
        Categories currentCategory = categoryService.findCategoryByName(category);
        List<Bikes> bikesByCategory = bikeRepository.findBikesByCategory(currentCategory);

        Set<Bikes> bikesSet = new LinkedHashSet<>(bikesByCategory);

        if (size != null) {
            String[] sizeArray = size.split(",");
            bikesSet = bikesSet.stream()
                    .filter(bike -> Arrays.asList(sizeArray).contains(bike.getSize()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if (price != null) {
            String[] priceArray = price.split(",");
            Set<Bikes> filteredByPrice = new LinkedHashSet<>();
            for (String range : priceArray) {
                int firstValue = Integer.parseInt(range.split("-")[0]);
                int secondValue = Integer.parseInt(range.split("-")[1]);

                filteredByPrice.addAll(bikesSet.stream()
                        .filter(bike -> bike.getSalesPrice() >= firstValue && bike.getSalesPrice() <= secondValue)
                        .collect(Collectors.toCollection(LinkedHashSet::new)));
            }
            bikesSet = filteredByPrice;
        }

        if (weight != null) {
            String[] weightArray = weight.split(",");
            Set<Bikes> filteredByWeight = new LinkedHashSet<>();
            for (String range : weightArray) {
                int firstValue = Integer.parseInt(range.split("-")[0]);
                int secondValue = Integer.parseInt(range.split("-")[1]);

                filteredByWeight.addAll(bikesSet.stream()
                        .filter(bike -> bike.getWeight() >= firstValue && bike.getWeight() <= secondValue)
                        .collect(Collectors.toCollection(LinkedHashSet::new)));
            }
            bikesSet = filteredByWeight;
        }

        if (isElectric) {
            bikesSet = bikesSet.stream()
                    .filter(Bikes::isElectric)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if (transmission != null) {
            String[] transmissionArray = transmission.split(",");
            bikesSet = bikesSet.stream()
                    .filter(bike -> Arrays.asList(transmissionArray).contains(bike.getTransmission()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        if (frame != null) {
            String[] frameArray = frame.split(",");
            bikesSet = bikesSet.stream()
                    .filter(bike -> Arrays.asList(frameArray).contains(bike.getFrame()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        List<Bikes> bikesList = new ArrayList<>(bikesSet);

        return bikesList.stream()
                .map(BikeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BikeDTO> getBikeSizeByModel(long bikeId) {
        Bikes bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new NoSuchElementException("Aucun vélo trouvé avec l'ID : " + bikeId));
        List<Bikes> bikeList = bikeRepository.findBikesByModel(bike.getModel());
        return bikeList.stream().map(BikeMapper::toDto).collect(Collectors.toList());
    }

    public void createPendingOrder(Bikes bike, int quantity) {
        PendingOrder newPending = new PendingOrder();
        newPending.setBike(bike);
        newPending.setQuantity(quantity);
        newPending.setBrands(bike.getBrand());
        pendingOrderRepository.save(newPending);
    }
}