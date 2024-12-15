package com.cernestoc.controller;

import com.cernestoc.controller.dtos.CarResponse;
import com.cernestoc.controller.mapper.CarMapper;
import com.cernestoc.domain.Car;
import com.cernestoc.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/cars")
@Slf4j
@RequiredArgsConstructor
public class CarController {


    private final CarService carService;
    private final CarMapper carMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Object> addCar(@RequestBody Car carRequest){
        try{

            return ResponseEntity.ok().body(carService.saveCar(carRequest));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<Object> getCar(@PathVariable Integer id){
        log.info("Retrieving car info");
        try {
            return ResponseEntity.ok(carService.getCarById(id));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public CompletableFuture<Object> getAllCars(){
        log.info("Retrieving info of  all cars");
            try {
                CompletableFuture<List<Car>> cars = carService.getAllCars();
                List<CarResponse> carResponses = new ArrayList<>();

                cars.get().forEach(car ->
                    carResponses.add(carMapper.toResponse(car))
                );

                return CompletableFuture.completedFuture(ResponseEntity.ok(carResponses));
            }
            catch (ExecutionException | InterruptedException e){
                log.warn("Interrupted");
                Thread.currentThread().interrupt();
                return CompletableFuture.completedFuture(ResponseEntity.notFound());
            }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Object> updateCarById(@PathVariable Integer id, @RequestBody Car carRequest){

        try{
            carService.updateById(id,carRequest);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //Ruta para borrar auto de lista de autos
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Object> deleteCarById(@PathVariable Integer id){
        log.info("Deleting car info identified by id");
        try {
            carService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/donwloadCars")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<Object> donloadCars() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment","user.csv");

        byte[] csvBytes = carService.carCsv().getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/uploadCsv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<String> uploadCsv(@RequestParam(value = "file")MultipartFile file) throws IOException {

        if(file.isEmpty()){
            log.error("File is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(file.getOriginalFilename().contains(".csv")){
            carService.uploadCar(file);
            return ResponseEntity.ok("CSV succefully loaded");
        }

        log.error("File is not an CSV file");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is not an CSV file");
    }

}
