package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    Optional<ProductGroup> findByName(String name);
}
