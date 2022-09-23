package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByNameLike(String name);
    List<Product> findAllByProducerId(Long producerId);
    List<Product> findAllByProductGroupId(Long productGroupId);
}
