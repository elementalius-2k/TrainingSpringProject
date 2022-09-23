package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.ProductGroupDto;

public interface ProductGroupService extends BaseService<ProductGroupDto> {
    ProductGroupDto findByName(String name);
}
