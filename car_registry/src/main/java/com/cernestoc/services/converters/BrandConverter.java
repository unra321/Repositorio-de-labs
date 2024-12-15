package com.cernestoc.services.converters;

import com.cernestoc.domain.Brand;
import com.cernestoc.entity.BrandEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter {

    public Brand toBrand(BrandEntity brand){
        Brand newBrand = new Brand();
        newBrand.setId(brand.getId());
        newBrand.setName(brand.getName());
        newBrand.setCountry(brand.getCountry());
        newBrand.setWarranty(brand.getWarranty());

        return newBrand;
    }

    public BrandEntity toEntity(Brand brand){
        BrandEntity entity = new BrandEntity();
        entity.setId(brand.getId());
        entity.setName(brand.getName());
        entity.setCountry(brand.getCountry());
        entity.setWarranty(brand.getWarranty());

        return entity;
    }
}
