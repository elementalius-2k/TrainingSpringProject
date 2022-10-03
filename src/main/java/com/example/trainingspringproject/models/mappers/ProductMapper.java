package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProductDto;
import com.example.trainingspringproject.models.entities.Product;
import com.example.trainingspringproject.repositories.ProducerRepository;
import com.example.trainingspringproject.repositories.ProductGroupRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private ProductGroupRepository groupRepository;

    public Product dtoToEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setQuantity(dto.getQuantity());
        product.setDescription(dto.getDescription());
        product.setIncomePrice(dto.getIncomePrice());
        product.setOutcomePrice(dto.getOutcomePrice());
        product.setProductGroup(groupRepository.findByName(dto.getProductGroupName())
                .orElseThrow(() -> new NothingFoundException("Product group", "name = " + dto.getProductGroupName())));
        product.setProducer(producerRepository.findByName(dto.getProducerName())
                .orElseThrow(() -> new NothingFoundException("Producer", "name = " + dto.getProducerName())));
        return product;
    }
    @Mapping(target = "productGroupName", expression = "java(entity.getProductGroup().getName())")
    @Mapping(target = "producerName", expression = "java(entity.getProducer().getName())")
    public abstract ProductDto entityToDto(Product entity);

    public abstract List<Product> dtoToEntity(Iterable<ProductDto> dto);
    public abstract List<ProductDto> entityToDto(Iterable<Product> entity);
}
