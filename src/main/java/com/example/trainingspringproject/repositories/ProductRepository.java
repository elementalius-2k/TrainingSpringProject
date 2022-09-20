package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findAllByNameLike(String name);
    Optional<List<Product>> findAllByProducerId(Long producerId);
    Optional<List<Product>> findAllByProductGroupId(Long productGroupId);
}
