package com.example.n_bike.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bikes_images")
public class Bikes_images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_bike")
    private Bikes bike;
    @ManyToOne
    @JoinColumn(name = "id_image")
    private Images image;

}
