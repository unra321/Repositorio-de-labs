package com.cernestoc.controller.mapper;

import com.cernestoc.controller.dtos.CarResponse;
import com.cernestoc.domain.Car;
import com.cernestoc.entity.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarMapper {

    @Autowired
    private BrandMapper converter;

    //Convert Car to CarResponse
    public CarResponse toResponse(Car car){
        CarResponse carResponse = new CarResponse();
        carResponse.setId(car.getId());
        carResponse.setBrand(car.getBrand().getName());
        carResponse.setModel(car.getModel());
        carResponse.setMilleage(car.getMilleage());
        carResponse.setPrice(car.getPrice());
        carResponse.setYear(car.getYear());
        carResponse.setDescription(car.getDescription());
        carResponse.setColor(car.getColor());
        carResponse.setFuelType(car.getFuelType());
        carResponse.setNumDoors(car.getNumDoors());

        return carResponse;
    }

    //Convert Car to CarEntity
    public CarEntity toModel(Car car){
        CarEntity entity = new CarEntity();
        entity.setId(car.getId());
        entity.setBrand(converter.toModel(car.getBrand()));
        entity.setModel(car.getModel());
        entity.setMilleage(car.getMilleage());
        entity.setPrice(car.getPrice());
        entity.setYear(car.getYear());
        entity.setDescription(car.getDescription());
        entity.setColor(car.getColor());
        entity.setFuelType(car.getFuelType());
        entity.setNumDoors(car.getNumDoors());

        return entity;
    }

    //convert List to ResponseList
    public List<CarResponse> toList(List<Car> carList){
        List<CarResponse> carResponses = new ArrayList<>();
        carList.forEach(car ->
                carResponses.add(toResponse(car))
        );
        return carResponses;
    }
}
