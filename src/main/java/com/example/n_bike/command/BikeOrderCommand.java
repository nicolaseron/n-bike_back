package com.example.n_bike.command;

import com.example.n_bike.entity.Bikes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeOrderCommand {
    private Bikes bike;
    private Integer quantity;
}
