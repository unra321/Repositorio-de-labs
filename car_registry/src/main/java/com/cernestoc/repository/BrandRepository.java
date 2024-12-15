package com.cernestoc.repository;

import com.cernestoc.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {

    BrandEntity findByName(String name);
}
