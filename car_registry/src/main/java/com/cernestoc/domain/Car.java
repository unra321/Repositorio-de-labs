package com.cernestoc.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private int id;

    private Brand brand;

    private String model;
    private int milleage;
    private double price;
    private int year;
    private String description;
    private String color;
    private String fuelType;
    private int numDoors;
}
