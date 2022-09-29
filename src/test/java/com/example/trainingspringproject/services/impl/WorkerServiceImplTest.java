package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.WorkerDto;
import com.example.trainingspringproject.models.entities.Worker;
import com.example.trainingspringproject.models.mappers.WorkerMapper;
import com.example.trainingspringproject.repositories.WorkerRepository;
import com.example.trainingspringproject.services.WorkerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class WorkerServiceImplTest {
    @Autowired
    private WorkerService service;

    @MockBean
    private WorkerRepository repositoryMock;
    @MockBean
    private WorkerMapper mapperMock;

    private final Long ID = 1L;
    private final String NAME = "Ivanov I.I.";
    private final String JOB = "Trainee";

    @Test
    void create_thenSaveWorker() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.create(dto);

        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void create_whenWorkerHasNonUniqueName_thenThrowException() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(AlreadyExistsException.class, () -> service.create(dto));
    }

    @Test
    void create_whenWorkerHasInvalidParameters_thenThrowException() {
        WorkerDto dto = new WorkerDto();

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.create(dto));
    }

    @Test
    void update_thenUpdateWorker() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.update(dto);

        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void update_whenWorkerHasInvalidParameters_thenThrowException() {
        WorkerDto dto = new WorkerDto();

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.update(dto));
    }

    @Test
    void update_whenWorkerNotExist_thenThrowException() {
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.update(dto));
    }

    @Test
    void update_whenWorkerHasNonUniqueName_thenThrowException() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(AlreadyExistsException.class, () -> service.update(dto));
    }

    @Test
    void delete_whenWorkerWithIdExists_thenDeleteWorker() {
        Worker entity = new Worker();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.delete(ID);

        verify(repositoryMock, times(1)).delete(entity);
    }

    @Test
    void delete_whenWorkerWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.delete(ID));
    }

    @Test
    void findById_whenWorkerWithIdExists_thenReturnWorker() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findById(ID), dto);
    }

    @Test
    void findById_whenWorkerWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findById(ID));
    }

    @Test
    void findAll_whenWorkersExist_thenReturnAllWorkers() {
        List<Worker> entities = new ArrayList<>();
        List<WorkerDto> dtos = new ArrayList<>();
        dtos.add(new WorkerDto());

        doReturn(entities).when(repositoryMock).findAll();
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAll(), dtos);
    }

    @Test
    void findAll_whenNoWorkersExist_thenThrowException() {
        List<Worker> entities = new ArrayList<>();

        doReturn(entities).when(repositoryMock).findAll();

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAll());
    }

    @Test
    void findByName_whenWorkerWithNameExists_thenReturnWorker() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findByName(NAME), dto);
    }

    @Test
    void findByName_whenWorkerWithNameNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findByName(NAME));
    }

    @Test
    void findAllByJob_whenWorkersWithJobExist_thenReturnWorkers() {
        List<Worker> entities = new ArrayList<>();
        List<WorkerDto> dtos = new ArrayList<>();
        dtos.add(new WorkerDto());

        doReturn(entities).when(repositoryMock).findAllByJob(JOB);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByJob(JOB), dtos);
    }

    @Test
    void findAllByJob_whenWorkersWithJobNotExist_thenThrowException() {
        List<Worker> entities = new ArrayList<>();

        doReturn(entities).when(repositoryMock).findAllByJob(JOB);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByJob(JOB));
    }
}