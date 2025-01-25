package com.example.n_bike.repository;

import com.example.n_bike.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandsRepository extends JpaRepository<Brands, Integer> {
}
