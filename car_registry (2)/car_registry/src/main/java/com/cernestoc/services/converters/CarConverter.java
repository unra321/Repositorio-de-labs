package com.cernestoc.services.converters;


import com.cernestoc.domain.Car;
import com.cernestoc.entity.CarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarConverter {

    private final BrandConverter converter;

    public Car toCar(CarEntity car){
        Car newCar = new Car();
        newCar.setId(car.getId());
        newCar.setBrand(converter.toBrand(car.getBrand()));
        newCar.setModel(car.getModel());
        newCar.setMilleage(car.getMilleage());
        newCar.setPrice(car.getPrice());
        newCar.setYear(car.getYear());
        newCar.setDescription(car.getDescription());
        newCar.setColor(car.getColor());
        newCar.setFuelType(car.getFuelType());
        newCar.setNumDoors(car.getNumDoors());

        return newCar;
    }

    //Convertir carro a entidadcarro
    public CarEntity toEntity(Car car){
        CarEntity entity = new CarEntity();
        entity.setId(car.getId());
        entity.setBrand(converter.toEntity(car.getBrand()));
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
}
