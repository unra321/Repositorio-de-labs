package com.cernestoc.service.converter;

import com.cernestoc.domain.Brand;
import com.cernestoc.domain.Car;
import com.cernestoc.entity.BrandEntity;
import com.cernestoc.entity.CarEntity;
import com.cernestoc.services.converters.BrandConverter;
import com.cernestoc.services.converters.CarConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarConverterTest {

    @InjectMocks
    private CarConverter carConverter;

    @Mock
    private BrandConverter converter;

    @Test
    void toCar_Test(){
        //Given
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Seat");

        CarEntity carEntity =  new CarEntity();
        carEntity.setId(1);
        carEntity.setYear(2000);
        carEntity.setMilleage(2000);
        carEntity.setModel("Arona");
        carEntity.setBrand(brandEntity);
        carEntity.setColor("Red");
        carEntity.setDescription("Desc");

        Brand brand = new Brand();
        brand.setName("Seat");

        Car car = new Car();
        car.setId(1);
        car.setYear(2000);
        car.setMilleage(2000);
        car.setModel("Arona");
        car.setBrand(brand);
        car.setColor("Red");
        car.setDescription("Desc");

        //When
        when(converter.toBrand(brandEntity)).thenReturn(brand);

        //Then
        Car result = carConverter.toCar(carEntity);
        assertEquals(result.getId(), car.getId());
        assertEquals(result.getYear(), car.getYear());
        assertEquals(result.getMilleage(), car.getMilleage());
        assertEquals(result.getModel(), car.getModel());
        assertEquals(result.getBrand(), car.getBrand());
        assertEquals(result.getColor(), car.getColor());
        assertEquals(result.getDescription(), car.getDescription());
        assertEquals(result.getBrand(),car.getBrand());
    }
}
