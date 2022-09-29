package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.WorkerDto;
import com.example.trainingspringproject.models.entities.Worker;
import com.example.trainingspringproject.models.mappers.WorkerMapper;
import com.example.trainingspringproject.repositories.WorkerRepository;
import com.example.trainingspringproject.services.WorkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    public WorkerServiceImpl(WorkerRepository repository, WorkerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void create(@Valid WorkerDto dto) {
        //проверка нарушения уникальности name
        if (repository.findByName(dto.getName()).isPresent())
            throw new AlreadyExistsException("Worker with name " + dto.getName());

        Worker worker = mapper.dtoToEntity(dto);
        worker.setId(null);
        repository.save(worker);
    }

    @Override
    @Transactional
    public void update(@Valid WorkerDto dto) {
        //проверка существования записи с нужным id
        Worker oldData = repository.findById(dto.getId())
                .orElseThrow(() -> new NothingFoundException("Worker", "id = " + dto.getId()));
        //если произошло изменение поля name, надо проверить, не нарушает ли новое значение уникальности в базе
        if (!dto.getName().equals(oldData.getName()) && repository.findByName(dto.getName()).isPresent())
            throw new AlreadyExistsException("Worker with name " + dto.getName());

        Worker newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    @Override
    public void delete(Long id) {
        Worker worker = repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Worker", "id = " + id));
        repository.delete(worker);
    }

    @Override
    public WorkerDto findById(Long id) {
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Worker" , "id = " + id)));
    }

    @Override
    public List<WorkerDto> findAll() {
        List<WorkerDto> list = mapper.entityToDto(repository.findAll());
        if (list.isEmpty())
            throw new NothingFoundException("Worker", "all");
        return list;
    }

    @Override
    public WorkerDto findByName(String name) {
        return mapper.entityToDto(repository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Worker", "name = " + name)));
    }

    @Override
    public List<WorkerDto> findAllByJob(String job) {
        List<WorkerDto> list = mapper.entityToDto(repository.findAllByJob(job));
        if (list.isEmpty())
            throw new NothingFoundException("Worker", "job = " + job);
        return list;
    }
}
