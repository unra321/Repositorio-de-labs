package com.cernestoc.service.impl;

import com.cernestoc.domain.Car;
import com.cernestoc.entity.CarEntity;
import com.cernestoc.repository.BrandRepository;
import com.cernestoc.repository.CarRepository;
import com.cernestoc.services.converters.CarConverter;
import com.cernestoc.services.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarConverter carConverter;

    @Test
    void getCarById_Test() {
        //Given
        CarEntity carEntity = new CarEntity();
        carEntity.setId(1);

        Car car = new Car();
        car.setId(1);

        //When
        when(carRepository.findById(1)).thenReturn(Optional.of(carEntity));
        when(carConverter.toCar(carEntity)).thenReturn(car);
        //Then

        Car result = carService.getCarById(1);
        assertEquals(result,car);

    }

    @Test
    void getCarById_Test_ko() {
        //Given
        //When
        when(carRepository.findById(1)).thenReturn(Optional.empty());
        //Then

        Car result = carService.getCarById(1);
        assertNull(result);
    }

    @Test
    void saveCar_Test() {
        //Given
        Car car = new Car();
        car.setId(1);

        CarEntity entity = new CarEntity();
        entity.setId(1);
        //When
        when(carConverter.toEntity(car)).thenReturn(entity);
        when(carConverter.toCar(carRepository.save(entity))).thenReturn(car);
        //Then
        Car result = carService.saveCar(car);

        assertEquals(result,car);
    }

    @Test
    void updateCarById_Test(){
        //Given
        Integer id = 1;

        Car carRequest = new Car();
        carRequest.setId(1);

        CarEntity entity = new CarEntity();
        entity.setId(1);
        //When
        when(carRepository.findById(1)).thenReturn(Optional.of(entity));
        when(carConverter.toEntity(carRequest)).thenReturn(entity);
        when(carConverter.toCar(carRepository.save(entity))).thenReturn(carRequest);
        //Then
        Car result = carService.updateById(id,carRequest);

        assertEquals(result,carRequest);
    }

    @Test
    void deleteById_Test(){
        //Given
        int id = 1;
        //When

        //Then
        carService.deleteById(id);
        verify(carRepository).deleteById(id);
    }

    @Test
    void getAllCars_Test() throws ExecutionException, InterruptedException{
        //Given
        List<CarEntity> entities = new ArrayList<>();

        CarEntity entity1 = new CarEntity();
        entity1.setId(1);
        entity1.setModel("Fiesta");
        entities.add(entity1);

        Car car1 = new Car();
        car1.setId(1);
        car1.setModel("Fiesta");


        //When
        when(carRepository.findAll()).thenReturn(entities);
        when(carConverter.toCar(any(CarEntity.class))).thenReturn(car1);
        //Then
        List<Car> result = carService.getAllCars().get();

        assertEquals(result.get(0).getId(), car1.getId());


    }

    @Test
    void carCsv_Test(){
        //Given
        List<CarEntity> carEntities = new ArrayList<>();

        CarEntity carEntity1 = new CarEntity();
        carEntity1.setId(1);

        CarEntity carEntity2 = new CarEntity();
        carEntity2.setId(2);

        carEntities.add(carEntity1);
        carEntities.add(carEntity2);

        StringBuilder csvContent = new StringBuilder();

        carEntities.forEach(carEntity -> csvContent.append(carEntity.getBrand()).append(",")
                .append(carEntity.getModel()).append(",")
                .append(carEntity.getColor()).append(",")
                .append(carEntity.getDescription()).append(",")
                .append(carEntity.getYear()).append(",")
                .append(carEntity.getPrice()).append(",")
                .append("\n"));
        //When
        when(carRepository.findAll()).thenReturn(carEntities);
        //Then
        String result = carService.carCsv();
        assertEquals(result,csvContent.toString());
    }

    @Test
    void uploadCar_Test() throws IOException {
        //Given
        List<CarEntity> carEntities = new ArrayList<>();
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        //When

        //Then
        carService.uploadCar(firstFile);
        verify(carRepository).saveAll(carEntities);
    }
}
