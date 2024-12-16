package com.cernestoc.services.impl;


import com.cernestoc.controller.dtos.CarRequest;
import com.cernestoc.domain.Brand;
import com.cernestoc.domain.Car;
import com.cernestoc.entity.BrandEntity;
import com.cernestoc.entity.CarEntity;
import com.cernestoc.repository.BrandRepository;
import com.cernestoc.repository.CarRepository;
import com.cernestoc.services.CarService;
import com.cernestoc.services.converters.BrandConverter;
import com.cernestoc.services.converters.CarConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final CarConverter carConverter;
    private final BrandConverter brandConverter;

    @Override
    public void saveCar(CarRequest carRequest){
        log.info("Adding car to database....");

        Brand brand = brandConverter.toBrand(brandRepository.findByName(carRequest.getBrand()));

        Car car = new Car();
        car.setId(carRequest.getId());
        car.setDescription(carRequest.getDescription());
        car.setPrice(carRequest.getPrice());
        car.setColor(carRequest.getColor());
        car.setBrand(brand);
        car.setNumDoors(carRequest.getNumDoors());
        car.setFuelType(carRequest.getFuelType());
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());

        CarEntity entity = carConverter.toEntity(car);
        carConverter.toCar(carRepository.save(entity));
    }

    @Override
    public Car getCarById(Integer id){
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);

        return carEntityOptional.map(carConverter::toCar).orElse(null);

    }

    @Override
    public Car updateById(Integer id, Car carRequest) {
        log.info("Updating car with id: {}", id);
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);

        if(carEntityOptional.isPresent()) {
            CarEntity entity = carConverter.toEntity(carRequest);
            entity.setId(id);
            return carConverter.toCar(carRepository.save(entity));
        }

        return null;
    }

    @Override
    public void deleteById(Integer id){
        log.info("Deleting car with id: {}", id);
        carRepository.deleteById(id);
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> getAllCars(){
        //Listas de carros y entidades
        List<CarEntity> carEntities = carRepository.findAll();

        List<Car> carList = new ArrayList<>();

        //Se añaden autos a la lista de autos a partir de la
        carEntities.forEach(entity -> carList.add(carConverter.toCar(entity)));

        return CompletableFuture.completedFuture(carList);
    }

    @Override
    public String carCsv() {
        List<CarEntity> carEntities = carRepository.findAll();
        StringBuilder csvContent = new StringBuilder();

        carEntities.forEach(carEntity -> csvContent.append(carEntity.getBrand()).append(",")
                .append(carEntity.getModel()).append(",")
                .append(carEntity.getColor()).append(",")
                .append(carEntity.getDescription()).append(",")
                .append(carEntity.getYear()).append(",")
                .append(carEntity.getPrice()).append(",")
                .append("\n"));

        return csvContent.toString();
    }

    @Override
    public void uploadCar(MultipartFile file) throws IOException {
        List<CarEntity> carEntities = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
        )
        {
            CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setTrim(true).build());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for(CSVRecord r : csvRecords){
                CarEntity car = new CarEntity();
                car.setBrand(brandRepository.findByName(r.get("brand")));
                car.setModel(r.get("model"));
                car.setYear(Integer.parseInt(r.get("year")));
                car.setColor(r.get("color"));
                car.setMilleage(Integer.parseInt(r.get("milleage")));

                carEntities.add(car);
            }

            carEntities = carRepository.saveAll(carEntities);
        }
        catch (IOException e){
            log.error("Failed to load Users");
            throw new IOException("Failed to load Users");
        }
    }


}
