package com.example.n_bike.repository;

import com.example.n_bike.entity.Bikes;
import com.example.n_bike.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bikes, Long> {
    List<Bikes> findBikesByCategory(Categories category);

    List<Bikes> findBikesByModel(String model);
}
