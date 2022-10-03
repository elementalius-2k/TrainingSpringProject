package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.models.dtos.ProductGroupDto;
import com.example.trainingspringproject.models.entities.ProductGroup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductGroupMapper {
    ProductGroup dtoToEntity(ProductGroupDto dto);
    ProductGroupDto entityToDto(ProductGroup entity);

    List<ProductGroup> dtoToEntity(Iterable<ProductGroupDto> dto);
    List<ProductGroupDto> entityToDto(Iterable<ProductGroup> entity);
}
