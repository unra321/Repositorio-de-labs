package com.cernestoc.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {

    private Integer id;
    private String brand;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String color;
    private String fuelType;
    private Integer numDoors;
}
