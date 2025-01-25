package com.example.n_bike.dto;

import com.example.n_bike.entity.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeDTO {
    private int id;
    private double sales_price;
    private Categories category;
    private int stock;
    private String size;
    private String description;
    private String brandName;
    private String model;
    private String transmission;
    private String frame;
    private int weight;
    private boolean isElectric;
    private List<ImageDTO> images;
    private String geometry_img_url;
}
