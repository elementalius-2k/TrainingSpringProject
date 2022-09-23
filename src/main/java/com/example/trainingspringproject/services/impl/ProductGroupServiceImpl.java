package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProductGroupDto;
import com.example.trainingspringproject.models.entities.ProductGroup;
import com.example.trainingspringproject.models.mappers.ProductGroupMapper;
import com.example.trainingspringproject.repositories.ProductGroupRepository;
import com.example.trainingspringproject.services.ProductGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class ProductGroupServiceImpl implements ProductGroupService {
    private final ProductGroupRepository repository;
    private final ProductGroupMapper mapper;

    public ProductGroupServiceImpl(ProductGroupRepository repository, ProductGroupMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void create(@Valid ProductGroupDto dto) {
        checkExisting(dto);
        ProductGroup productGroup = mapper.dtoToEntity(dto);
        repository.save(productGroup);
    }

    @Override
    @Transactional
    public void update(@Valid ProductGroupDto dto) {
        checkExisting(dto);
        ProductGroup oldData = repository.findById(dto.getId())
                .orElseThrow(() -> new NothingFoundException("Product group with id " + dto.getId()));
        ProductGroup newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    private void checkExisting(ProductGroupDto dto) {
        if (repository.findByName(dto.getName()).isPresent())
            throw new AlreadyExistsException("Product group with name " + dto.getName());
    }

    @Override
    public void delete(Long id) {
        ProductGroup productGroup = repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Product group with id " + id));
        repository.delete(productGroup);
    }

    @Override
    public ProductGroupDto findById(Long id) {
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Product group with id " + id)));
    }

    @Override
    public List<ProductGroupDto> findAll() {
        List<ProductGroupDto> list = mapper.entityToDto(repository.findAll());
        if (list.isEmpty())
            throw new NothingFoundException();
        return list;
    }

    @Override
    public ProductGroupDto findByName(String name) {
        return null;
    }
}
