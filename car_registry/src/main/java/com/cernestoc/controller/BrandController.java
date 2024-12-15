package com.cernestoc.controller;

import com.cernestoc.controller.dtos.BrandResponse;
import com.cernestoc.controller.mapper.BrandMapper;
import com.cernestoc.domain.Brand;
import com.cernestoc.services.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/brands")
public class BrandController {

    private final BrandService brandService;
    private final BrandMapper brandMapper;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<Object> addBrand(@RequestBody Brand brandRequest){
        log.info("Saving Brand info");
        try{
            return ResponseEntity.ok().body(brandService.saveBrand(brandRequest));
        }
        catch (Exception e){

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<Object> getBrand(@PathVariable Integer id){
        log.info("Retrieving Brand info");
        try {
            return ResponseEntity.ok(brandService.getBrandById(id));
        }
        catch (Exception e){
            log.error("No se ha encontrado nada");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public CompletableFuture<Object> getAllBrands() throws ExecutionException{
        log.info("Retrieving brand info");
        try {
            CompletableFuture<List<Brand>> brands = brandService.getAllBrands();
            log.info("esto funciona");
            List<BrandResponse> brandResponses = new ArrayList<>();

            brands.get().forEach(brand ->
                brandResponses.add(brandMapper.toResponse(brand)) );

            return CompletableFuture.completedFuture(ResponseEntity.ok(brandResponses));
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture(ResponseEntity.notFound());
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Object> updateBrandById(@PathVariable Integer id, @RequestBody Brand brandRequest){

        try{
            brandService.updateById(id,brandRequest);
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
            brandService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
