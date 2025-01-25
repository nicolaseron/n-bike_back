package com.example.n_bike.mapper;

import com.example.n_bike.dto.BikeDTO;
import com.example.n_bike.dto.ImageDTO;
import com.example.n_bike.entity.Bikes;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BikeMapper {
    public static BikeDTO toDto(Bikes bike) {
        BikeDTO dto = new BikeDTO();
        dto.setId(bike.getId());
        dto.setDescription(bike.getDescription());
        dto.setSales_price(bike.getSalesPrice());
        dto.setSize(bike.getSize());
        dto.setStock(bike.getStock());
        dto.setCategory(bike.getCategory());
        dto.setBrandName(bike.getBrand().getName());
        dto.setModel(bike.getModel());
        dto.setTransmission(bike.getTransmission());
        dto.setFrame(bike.getFrame());
        dto.setWeight(bike.getWeight());
        dto.setElectric(bike.isElectric());
        dto.setImages(bike.getBikeImages().stream()
                .map(bikeImage -> new ImageDTO(bikeImage.getImage().getUrl(), bikeImage.getImage().getDescription()))
                .collect(Collectors.toList()));
        dto.setGeometry_img_url(bike.getGeometry_img_url());
        return dto;
    }
}
