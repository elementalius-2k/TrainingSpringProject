package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.WorkerDto;
import com.example.trainingspringproject.models.entities.Worker;
import com.example.trainingspringproject.models.mappers.WorkerMapper;
import com.example.trainingspringproject.repositories.WorkerRepository;
import com.example.trainingspringproject.services.WorkerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkerServiceImplTest {
    private WorkerService service;
    private Validator validator;
    @Mock
    private WorkerRepository repositoryMock;
    @Mock
    private WorkerMapper mapperMock;

    @BeforeEach
    void init() {
        service = new WorkerServiceImpl(repositoryMock, mapperMock);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

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

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void create_whenWorkerHasNonUniqueName_thenThrowException() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        Assertions.assertThrows(AlreadyExistsException.class, () -> service.create(dto));
    }

    @Test
    void update_thenUpdateWorker() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.update(dto);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void update_whenWorkerNotExist_thenThrowException() {
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        Assertions.assertThrows(NothingFoundException.class, () -> service.update(dto));
    }

    @Test
    void update_whenWorkerHasNonUniqueName_thenThrowException() {
        Worker entity = new Worker();
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        Assertions.assertThrows(AlreadyExistsException.class, () -> service.update(dto));
    }

    @Test
    void create_update_whenWorkerHasInvalidParameters_thenValidationExceptionCreated() {
        WorkerDto dto = new WorkerDto();

        Assertions.assertFalse(validator.validate(dto).isEmpty());
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
        List<Worker> entities = Collections.emptyList();
        List<WorkerDto> dtos = Collections.singletonList(new WorkerDto());

        doReturn(entities).when(repositoryMock).findAll();
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAll(), dtos);
    }

    @Test
    void findAll_whenNoWorkersExist_thenThrowException() {
        List<Worker> entities = Collections.emptyList();

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
        List<Worker> entities = Collections.emptyList();
        List<WorkerDto> dtos = Collections.singletonList(new WorkerDto());

        doReturn(entities).when(repositoryMock).findAllByJob(JOB);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByJob(JOB), dtos);
    }

    @Test
    void findAllByJob_whenWorkersWithJobNotExist_thenThrowException() {
        List<Worker> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByJob(JOB);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByJob(JOB));
    }
}