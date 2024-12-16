package com.cernestoc.services;

import com.cernestoc.controller.dtos.CarRequest;
import com.cernestoc.domain.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CarService {

    Car getCarById(Integer id) throws IllegalArgumentException;

    void deleteById(Integer id) throws Exception;

    Car updateById(Integer id, Car carRequest) throws Exception;

    void saveCar(CarRequest carRequest) throws Exception;

    CompletableFuture<List<Car>> getAllCars();

    String carCsv();

    void uploadCar(MultipartFile file) throws IOException;
}
