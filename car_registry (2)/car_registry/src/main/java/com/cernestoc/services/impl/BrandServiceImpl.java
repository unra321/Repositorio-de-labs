package com.cernestoc.services.impl;

import com.cernestoc.domain.Brand;
import com.cernestoc.entity.BrandEntity;
import com.cernestoc.repository.BrandRepository;
import com.cernestoc.services.BrandService;
import com.cernestoc.services.converters.BrandConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandConverter brandConverter;

    @Override
    public Brand getBrandById(Integer id) {
        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(id);

        return brandEntityOptional.map(brandConverter::toBrand).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Brand updateById(Integer id, Brand brandRequest) {
        log.info("Updating car with id: {}", id);
        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(id);

        if(brandEntityOptional.isPresent()) {
            BrandEntity entity = brandConverter.toEntity(brandRequest);
            entity.setId(id);
            return brandConverter.toBrand(brandRepository.save(entity));
        }

        return null;
    }

    @Override
    public Brand saveBrand(Brand brandRequest) {
        log.info("Adding brand to database");
        BrandEntity brand = brandConverter.toEntity(brandRequest);

        return brandConverter.toBrand(brandRepository.save(brand));
    }

    @Override
    public CompletableFuture<List<Brand>> getAllBrands() {
        List<BrandEntity> brandEntities = brandRepository.findAll();

        List<Brand> brands = new ArrayList<>();

        brandEntities.forEach(brand -> brands.add(brandConverter.toBrand(brand)));

        return CompletableFuture.completedFuture(brands);
    }
}
