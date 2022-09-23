package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.ProductDto;

import java.util.List;

public interface ProductService extends BaseService<ProductDto> {
    List<ProductDto> findAllByNameLike(String name);
    List<ProductDto> findAllByProducerId(Long producerId);
    List<ProductDto> findAllByProductGroupId(Long groupId);
}
