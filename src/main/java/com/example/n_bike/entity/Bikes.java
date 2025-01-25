package com.example.n_bike.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Bikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "brand")
    private Brands brand;
    private String model;
    private double salesPrice;
    @ManyToOne
    @JoinColumn(name = "category")
    private Categories category;
    private int stock;
    private int stock_mini;
    private String size;
    private int weight;
    private String description;
    private double purchase_price;
    private double margin;
    private String transmission;
    private String frame;
    private boolean isElectric;
    @OneToMany(mappedBy = "bike", fetch = FetchType.EAGER)
    private List<Bikes_images> bikeImages;
    private String geometry_img_url;
    private String article_id;
}