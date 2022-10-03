package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.ProductGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductGroupRepository extends CrudRepository<ProductGroup, Long> {
    Optional<ProductGroup> findByName(String name);
}
