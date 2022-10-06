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
import org.springframework.util.CollectionUtils;
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
        checkName(dto.getName());

        Worker worker = mapper.dtoToEntity(dto);
        worker.setId(null);
        repository.save(worker);
    }

    @Override
    @Transactional
    public void update(@Valid WorkerDto dto) {
        //проверка существования записи с нужным id
        Worker oldData = getByIdOrElseThrow(dto.getId());
        //если произошло изменение поля name, надо проверить, не нарушает ли новое значение уникальности в базе
        if (!dto.getName().equals(oldData.getName()))
            checkName(dto.getName());

        Worker newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getByIdOrElseThrow(id));
    }

    @Override
    public WorkerDto findById(Long id) {
        return mapper.entityToDto(getByIdOrElseThrow(id));
    }

    @Override
    public List<WorkerDto> findAll() {
        List<Worker> list = (List<Worker>) repository.findAll();
        checkEmptyList(list, "all");
        return mapper.entityToDto(list);
    }

    @Override
    public WorkerDto findByName(String name) {
        return mapper.entityToDto(repository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Worker", "name = " + name)));
    }

    @Override
    public List<WorkerDto> findAllByJob(String job) {
        List<Worker> list = repository.findAllByJob(job);
        checkEmptyList(list, "job = " + job);
        return mapper.entityToDto(list);
    }

    private void checkName(String name) {
        if (repository.findByName(name).isPresent())
            throw new AlreadyExistsException("Worker with name " + name);
    }

    private Worker getByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Worker", "id = " + id));
    }

    private void checkEmptyList(List<Worker> list, String exceptionMessage) {
        if (CollectionUtils.isEmpty(list))
            throw new NothingFoundException("Worker", exceptionMessage);
    }
}
