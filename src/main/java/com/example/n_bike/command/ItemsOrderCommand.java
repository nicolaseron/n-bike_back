package com.example.n_bike.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsOrderCommand {
    private Integer bikeId;
    private Integer quantity;
}
