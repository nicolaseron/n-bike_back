package com.example.n_bike.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommand {

    private String firstName;
    private String lastName;
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String city;
    private String phone;
    private String email;
    private String deliveryInfo;
    private String orderNumber;
    private List<ItemsOrderCommand> items;

}