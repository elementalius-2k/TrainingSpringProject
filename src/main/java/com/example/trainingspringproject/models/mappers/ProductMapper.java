package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.models.dtos.ProductDto;
import com.example.trainingspringproject.models.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //@Mapping(target = "productGroup", )
    Product dtoToEntity(ProductDto dto);
    @Mapping(target = "productGroupName", expression = "java(entity.getProductGroup().getName())")
    @Mapping(target = "producerName", expression = "java(entity.getProducer().getName())")
    ProductDto entityToDto(Product entity);

    List<Product> dtoToEntity(Collection<ProductDto> dto);
    List<ProductDto> entityToDto(Collection<Product> entity);
}
