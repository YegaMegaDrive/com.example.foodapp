package com.example.to.controller;

import com.example.to.enums.MealEnum;
import lombok.Data;

@Data
public class AddOrDeleteProductRq {
    private String userName;
    private String productName;
    private MealEnum meal;
}
