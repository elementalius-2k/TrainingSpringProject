package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.models.dtos.ProductDto;
import com.example.trainingspringproject.models.entities.Product;
import com.example.trainingspringproject.repositories.ProducerRepository;
import com.example.trainingspringproject.repositories.ProductGroupRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    @Autowired
    protected ProducerRepository producerRepository;
    @Autowired
    protected ProductGroupRepository groupRepository;

    @Mapping(target = "producer", expression = "java(producerRepository.findByName(dto.getProducerName()).get())")
    @Mapping(target = "productGroup", expression = "java(groupRepository.findByName(dto.getProductGroupName()).get())")
    public abstract Product dtoToEntity(ProductDto dto);
    @Mapping(target = "productGroupName", expression = "java(entity.getProductGroup().getName())")
    @Mapping(target = "producerName", expression = "java(entity.getProducer().getName())")
    public abstract ProductDto entityToDto(Product entity);

    public abstract List<Product> dtoToEntity(Collection<ProductDto> dto);
    public abstract List<ProductDto> entityToDto(Collection<Product> entity);
}
