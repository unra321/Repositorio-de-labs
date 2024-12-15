package com.cernestoc.services;

import com.cernestoc.domain.Brand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BrandService {

    Brand getBrandById(Integer id);

    void deleteById(Integer id);

    Brand updateById(Integer id, Brand brandRequest);

    Brand saveBrand(Brand brandRequest);

    CompletableFuture<List<Brand>> getAllBrands();

}
