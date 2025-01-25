package com.example.n_bike.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String contact;
    private int minimumOrderPrice;
}
