package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProducerDto;
import com.example.trainingspringproject.models.entities.Producer;
import com.example.trainingspringproject.models.mappers.ProducerMapper;
import com.example.trainingspringproject.repositories.ProducerRepository;
import com.example.trainingspringproject.services.ProducerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class ProducerServiceImpl implements ProducerService {
    private final ProducerRepository repository;
    private final ProducerMapper mapper;

    public ProducerServiceImpl(ProducerRepository repository, ProducerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void create(@Valid ProducerDto dto) {
        //проверка нарушения уникальности name
        if (repository.findByName(dto.getName()).isPresent())
            throw new AlreadyExistsException("Producer with name " + dto.getName());

        Producer producer = mapper.dtoToEntity(dto);
        producer.setId(null);
        repository.save(producer);
    }

    @Override
    @Transactional
    public void update(@Valid ProducerDto dto) {
        //проверка существования записи с нужным id
        Producer oldData = repository.findById(dto.getId())
                .orElseThrow(() -> new NothingFoundException("Producer", "id = " + dto.getId()));
        //если произошло изменение поля name, надо проверить, не нарушает ли новое значение уникальности в базе
        if (!dto.getName().equals(oldData.getName()) && repository.findByName(dto.getName()).isPresent())
            throw new AlreadyExistsException("Producer with name " + dto.getName());

        Producer newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    @Override
    public void delete(Long id) {
        Producer producer = repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Producer", "id = " + id));
        repository.delete(producer);
    }

    @Override
    public ProducerDto findById(Long id) {
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Producer", "id = " + id)));
    }

    @Override
    public List<ProducerDto> findAll() {
        List<ProducerDto> list = mapper.entityToDto(repository.findAll());
        if (CollectionUtils.isEmpty(list))
            throw new NothingFoundException("Producer", "all");
        return list;
    }

    @Override
    public ProducerDto findByName(String name) {
        return mapper.entityToDto(repository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Producer", "name = " + name)));
    }

    @Override
    public List<ProducerDto> findAllByAddressLike(String address) {
        List<ProducerDto> list = mapper.entityToDto(repository.findAllByAddressLike(address));
        if (CollectionUtils.isEmpty(list))
            throw new NothingFoundException("Producer", "address = " + address);
        return list;
    }
}
