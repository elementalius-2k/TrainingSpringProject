package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.NotEnoughProductsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProductDto;
import com.example.trainingspringproject.models.entities.Product;
import com.example.trainingspringproject.models.mappers.ProductMapper;
import com.example.trainingspringproject.repositories.ProductRepository;
import com.example.trainingspringproject.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(@Valid ProductDto dto) {
        Product product = mapper.dtoToEntity(dto);
        product.setId(null);
        repository.save(product);
    }

    @Override
    @Transactional
    public void update(@Valid ProductDto dto) {
        Product oldData = repository.findById(dto.getId())
                .orElseThrow(() -> new NothingFoundException("Product", "id = " + dto.getId()));
        Product newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    @Override
    public void delete(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Product", "id = " + id));
        repository.delete(product);
    }

    @Override
    public ProductDto findById(Long id) {
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Product", "id = " + id)));
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> list = mapper.entityToDto(repository.findAll());
        if (list.isEmpty())
            throw new NothingFoundException("Product", "all");
        return list;
    }

    @Override
    public List<ProductDto> findAllByNameLike(String name) {
        List<ProductDto> list = mapper.entityToDto(repository.findAllByNameLike(name));
        if (list.isEmpty())
            throw new NothingFoundException("Product", "name = " + name);
        return list;
    }

    @Override
    public List<ProductDto> findAllByProducerId(Long producerId) {
        List<ProductDto> list = mapper.entityToDto(repository.findAllByProducerId(producerId));
        if (list.isEmpty())
            throw new NothingFoundException("Product", "producer id = " + producerId);
        return list;
    }

    @Override
    public List<ProductDto> findAllByProductGroupId(Long groupId) {
        List<ProductDto> list = mapper.entityToDto(repository.findAllByProductGroupId(groupId));
        if (list.isEmpty())
            throw new NothingFoundException("Product", "group id = " + groupId);
        return list;
    }

    @Override
    @Transactional
    public void income(Long productId, int quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new NothingFoundException("Product", "id = " + productId));
        product.setQuantity(product.getQuantity() + quantity);
        repository.save(product);
    }

    @Override
    @Transactional
    public void outcome(Long productId, int quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new NothingFoundException("Product", "id = " + productId));
        if (product.getQuantity() < quantity)
            throw new NotEnoughProductsException(quantity, product.getQuantity(), product.getName());
        product.setQuantity(product.getQuantity() - quantity);
        repository.save(product);
    }
}
