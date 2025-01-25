package com.example.n_bike.repository;

import com.example.n_bike.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    Categories findByName(String name);
}