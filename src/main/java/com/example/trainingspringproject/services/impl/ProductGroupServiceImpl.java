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
import org.springframework.util.CollectionUtils;
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
        //проверка уникальности name
        checkName(dto.getName());

        ProductGroup productGroup = mapper.dtoToEntity(dto);
        productGroup.setId(null);
        repository.save(productGroup);
    }

    @Override
    @Transactional
    public void update(@Valid ProductGroupDto dto) {
        //проверка существования записи с нужным id
        ProductGroup oldData = getByIdOrElseThrow(dto.getId());
        //если произошло изменение поля name, надо проверить, не нарушает ли новое значение уникальности в базе
        if (!dto.getName().equals(oldData.getName()))
            checkName(dto.getName());

        ProductGroup newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getByIdOrElseThrow(id));
    }

    @Override
    public ProductGroupDto findById(Long id) {
        return mapper.entityToDto(getByIdOrElseThrow(id));
    }

    @Override
    public List<ProductGroupDto> findAll() {
        List<ProductGroup> list = (List<ProductGroup>) repository.findAll();
        checkEmptyList(list, "all");
        return mapper.entityToDto(list);
    }

    @Override
    public ProductGroupDto findByName(String name) {
        return mapper.entityToDto(repository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Product group", "name = " + name)));
    }

    private void checkName(String name) {
        if (repository.findByName(name).isPresent())
            throw new AlreadyExistsException("Product group with name " + name);
    }

    private ProductGroup getByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Product group", "id = " + id));
    }

    private void checkEmptyList(List<ProductGroup> list, String exception) {
        if (CollectionUtils.isEmpty(list))
            throw new NothingFoundException("Product group", exception);
    }
}
