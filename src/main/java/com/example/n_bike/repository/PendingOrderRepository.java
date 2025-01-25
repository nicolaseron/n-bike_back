package com.example.n_bike.repository;

import com.example.n_bike.entity.Bikes;
import com.example.n_bike.entity.Brands;
import com.example.n_bike.entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingOrderRepository extends JpaRepository<PendingOrder, Integer> {
    List<PendingOrder> findAllByBrands(Brands brands);

    PendingOrder findByBike(Bikes bikes);

    void deleteAllByBrands(Brands brands);
}
