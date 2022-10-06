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
import org.springframework.util.CollectionUtils;
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
        Product oldData = getByIdOrElseThrow(dto.getId());
        Product newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getByIdOrElseThrow(id));
    }

    @Override
    public ProductDto findById(Long id) {
        return mapper.entityToDto(getByIdOrElseThrow(id));
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> list = (List<Product>) repository.findAll();
        checkEmptyList(list, "all");
        return mapper.entityToDto(list);
    }

    @Override
    public List<ProductDto> findAllByNameLike(String name) {
        List<Product> list = repository.findAllByNameLike(name);
        checkEmptyList(list, "name = " + name);
        return mapper.entityToDto(list);
    }

    @Override
    public List<ProductDto> findAllByProducerId(Long producerId) {
        List<Product> list = repository.findAllByProducerId(producerId);
        checkEmptyList(list, "producer id = " + producerId);
        return mapper.entityToDto(list);
    }

    @Override
    public List<ProductDto> findAllByProductGroupId(Long groupId) {
        List<Product> list = repository.findAllByProductGroupId(groupId);
        checkEmptyList(list, "group id = " + groupId);
        return mapper.entityToDto(list);
    }

    @Override
    @Transactional
    public void income(Long productId, int quantity) {
        Product product = getByIdOrElseThrow(productId);
        product.setQuantity(product.getQuantity() + quantity);
        repository.save(product);
    }

    @Override
    @Transactional
    public void outcome(Long productId, int quantity) {
        Product product = getByIdOrElseThrow(productId);
        if (product.getQuantity() < quantity)
            throw new NotEnoughProductsException(quantity, product.getQuantity(), product.getName());
        product.setQuantity(product.getQuantity() - quantity);
        repository.save(product);
    }

    private Product getByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Product", "id = " + id));
    }

    private void checkEmptyList(List<Product> list, String exceptionMessage) {
        if (CollectionUtils.isEmpty(list))
            throw new NothingFoundException("Product", exceptionMessage);
    }
}
