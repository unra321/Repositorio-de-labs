package com.cernestoc.controller.mapper;

import com.cernestoc.controller.dtos.BrandResponse;
import com.cernestoc.domain.Brand;
import com.cernestoc.entity.BrandEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    public BrandResponse toResponse(Brand brand){
        BrandResponse newBrand = new BrandResponse();
        newBrand.setId(brand.getId());
        newBrand.setName(brand.getName());
        newBrand.setCountry(brand.getCountry());
        newBrand.setWarranty(brand.getWarranty());

        return newBrand;
    }

    public BrandEntity toModel(Brand brand){
        BrandEntity entity = new BrandEntity();
        entity.setId(brand.getId());
        entity.setName(brand.getName());
        entity.setCountry(brand.getCountry());
        entity.setWarranty(brand.getWarranty());

        return entity;
    }
}
