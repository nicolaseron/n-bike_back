package com.example.n_bike.services;

import com.example.n_bike.entity.Categories;
import com.example.n_bike.repository.CategoriesRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public Categories findCategoryById(int id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    public Categories findCategoryByName(String name) {
        return categoriesRepository.findByName(name);
    }
}